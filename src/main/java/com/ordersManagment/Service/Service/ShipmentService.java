package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Database.ShipmentDB;
import com.ordersManagment.Service.Enums.OrderStatus;
import com.ordersManagment.Service.Model.*;
import com.ordersManagment.Service.Response.ShipmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;
@Service
public class ShipmentService {

    private final AccountService accountService;
    @Autowired
    public ShipmentService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Ship a simple order and deduct shipping fees from the customer's account.
     *
     * @param orderID ID of the order to be shipped
     * @return ShipmentResponse indicating the success or failure of the shipment
     */
    public ShipmentResponse shipSimpleOrder(int orderID) {
        Order order = OrderDB.getInstance(orderID);
        // check if order is simple
        if (!(order instanceof SimpleOrder)) {
            return new ShipmentResponse(false, "Order not found", "Order with ID " + orderID + " not found");
        }
        // check if order is already shipped
        if (order.getStatus() == OrderStatus.Pending || order.getStatus() == OrderStatus.Shipped) {
            return new ShipmentResponse(false, "Order has shippedOrder with ID " + orderID + " is already shipped", ShipmentDB.getShipmentByOrderID(orderID));
        }

        Customer customer = OrderDB.getCustomer(order);
        int customerID = customer.getID();
        Map<Integer, Address> shipmentAddress = new HashMap<>();
        // get customer address
        shipmentAddress.put(customerID, CustomerDB.getAddress(customer.getEmail()));

        // calculate shipping fees and deduct from customer account if possible
        double shippingFees = calculateShippingFees();
        if (accountService.deductFromAccount(customerID, shippingFees)) {
            ShipmentDB.setShipment(orderID, shipmentAddress);
            // send notification to customer and update order status
            sendShipmentNotification(order);
            order.setStatus(OrderStatus.Pending);
            return new ShipmentResponse(true, "Shipment successful", ShipmentDB.getShipmentByOrderID(orderID));
        }
        else {
            return new ShipmentResponse(false, "Insufficient funds", "Customer does not have enough funds to cover shipping fees");
        }
    }

    /**
     * Ship a compound order and deduct shipping fees from each customer's account.
     *
     * @param orderID ID of the compound order to be shipped
     * @return ShipmentResponse indicating the success or failure of the shipment
     */
    public ShipmentResponse shipCompoundOrder(int orderID) {
        Order compoundOrder = OrderDB.getInstance(orderID);
        // check if order is compound
        if (!(compoundOrder instanceof CompoundOrder)) {
            return new ShipmentResponse(false, "Invalid order", "Compound order with ID " + orderID + " not found");
        }
        // check if order is already shipped
        if (compoundOrder.getStatus() == OrderStatus.Pending || compoundOrder.getStatus() == OrderStatus.Shipped) {
            return new ShipmentResponse(false, "Order has shippedOrder with ID " + orderID + " is already shipped", ShipmentDB.getShipmentByOrderID(orderID));
        }

        Map<Integer, Address> shipmentAddress = new HashMap<>();
        // get customer address for each order
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            Customer customer = OrderDB.getCustomer(simpleOrder);
            int customerID = customer.getID();
            shipmentAddress.put(customerID, CustomerDB.getAddress(customer.getEmail()));
        }
        int custID = OrderDB.getCustomer(compoundOrder).getID();
        shipmentAddress.put(custID, CustomerDB.getAddress(OrderDB.getCustomer(compoundOrder).getEmail()));

        // calculate shipping fees that participate in compound order and deduct from each customer account if possible
        int numberOfOrders = ((CompoundOrder) compoundOrder).getOrders().size();
        double shippingFees = (calculateShippingFees() + numberOfOrders * 5) / numberOfOrders;

        // check if each customer has enough funds to cover shipping fees and deduct from each customer account
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(simpleOrder).getID();
            if (!accountService.deductFromAccount(customerID, shippingFees)) {
                return new ShipmentResponse(false, "Insufficient funds", "Customer does not have enough funds to cover shipping fees");
            }
        }
        if (!accountService.deductFromAccount(custID, shippingFees)) {
            return new ShipmentResponse(false, "Insufficient funds", "Customer does not have enough funds to cover shipping fees");
        }
        ShipmentDB.setShipment(orderID, shipmentAddress);

        // send notification to each customer and update order status
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            sendShipmentNotification(simpleOrder);
            simpleOrder.setStatus(OrderStatus.Pending);
        }
        sendShipmentNotification(compoundOrder);
        compoundOrder.setStatus(OrderStatus.Pending);
        return new ShipmentResponse(true, "Shipment successful", ShipmentDB.getShipmentByOrderID(orderID));
    }

    /**
     * Cancel the shipment for a simple order, refund shipping fees, and remove the shipment record.
     *
     * @param shipmentID ID of the shipment for which shipment is to be cancelled
     * @return ShipmentResponse indicating the success or failure of the cancellation
     */
    public ShipmentResponse cancelSimpleOrderShipment(int shipmentID) {

        Shipment shipment = ShipmentDB.getShipmentByShipmentID(shipmentID);
        // check if shipment exists
        if (shipment == null) {
            return new ShipmentResponse(false, "Shipment not found", "Shipment with ID " + shipmentID + " not found");
        }

        // check if shipment can be cancelled assuming cancel occur in (less than 3 minutes) from shipment time
        if (!checkShipmentTime(shipment.getShipmentTime())) {
            return new ShipmentResponse(false, "Shipment cancellation failed", "Shipment cannot be cancelled after 3 minutes");
        }

        // refund shipping fees and remove shipment record
        assert OrderDB.getInstance(shipment.getOrderID()) != null;
        int customerID = OrderDB.getCustomer(OrderDB.getInstance(shipment.getOrderID())).getID();
        double refundedFees = calculateShippingFees();
        accountService.addToAccount(customerID, refundedFees);
        // update order status
        OrderDB.getInstance(shipment.getOrderID()).setStatus(OrderStatus.Placed);
        ShipmentDB.removeShipment(shipment);
        return new ShipmentResponse(true, "Shipment cancellation successful", shipment);
    }

    /**
     * Cancel the shipment for a compound order, refund shipping fees for each order, and remove the shipment record.
     *
     * @param shipmentID ID of the shipment for which shipment is to be cancelled
     * @return ShipmentResponse indicating the success or failure of the cancellation
     */
    public ShipmentResponse cancelCompoundOrderShipment(int shipmentID) {

        Shipment shipment = ShipmentDB.getShipmentByShipmentID(shipmentID);
        // check if shipment exists
        if (shipment == null) {
            return new ShipmentResponse(false, "Shipment not found", "Shipment with ID " + shipmentID + " not found");
        }

        // check if shipment can be cancelled assuming cancel occur in (less than 3 minutes) from shipment time
        if (!checkShipmentTime(shipment.getShipmentTime())) {
            return new ShipmentResponse(false, "Shipment cancellation failed", "Shipment cannot be cancelled after 3 minutes");
        }

        // refund shipping fees for each order and remove shipment record
        Order compoundOrder = OrderDB.getInstance(shipment.getOrderID());
        assert compoundOrder != null;
        int numberOfOrders = ((CompoundOrder) compoundOrder).getOrders().size();
        double refundedFees = (calculateShippingFees() + numberOfOrders * 5) / numberOfOrders;

        accountService.addToAccount(OrderDB.getCustomer(compoundOrder).getID(), refundedFees);
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(simpleOrder).getID();
            accountService.addToAccount(customerID, refundedFees);
            simpleOrder.setStatus(OrderStatus.Placed);
        }
        // update order status
        compoundOrder.setStatus(OrderStatus.Placed);
        ShipmentDB.removeShipment(shipment);
        return new ShipmentResponse(true, "Shipment cancellation successful", shipment);
    }


    /**
     * Calculate the shipping fees for an order this still assumption
     *
     * @return The shipping fees
     */
    private double calculateShippingFees() {
        return 50.0;
    }

    /**
     * Check if the time difference between the current time and shipment time is less than 3 minutes.
     *
     * @param shipmentTime The time the shipment was created
     * @return True if the shipment can be cancelled, false otherwise
     */
    private boolean checkShipmentTime(Time shipmentTime) {
        Time currentTime = new Time(new Date().getTime());
        long differenceInMillis = currentTime.getTime() - shipmentTime.getTime();
        long differenceInMinutes = differenceInMillis / (60 * 1000);
        return differenceInMinutes < 3;
    }

    /**
     * Send notification to customer
     *
     * @param order The order to be shipped
     */
    private void sendShipmentNotification(Order order){
        Customer customer = OrderDB.getCustomer(order);
        NotificationService notificationService = new NotificationService(new ShipmentTemplate(order));
        notificationService.chooseChannelAndSend(customer);

    }

}