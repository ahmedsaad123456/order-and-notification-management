package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Database.ProductDB;
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
        if (!(order instanceof SimpleOrder)) {
            return new ShipmentResponse(false, "Order not found", "Order with ID " + orderID + " not found");
        }
        Customer customer = OrderDB.getCustomer(order);
        int customerID = customer.getID();
        Map<Integer, Address> shipmentAddress = new HashMap<>();
        shipmentAddress.put(customerID, CustomerDB.getAddress(customer.getEmail()));

        double shippingFees = calculateShippingFees();
        if (accountService.deductFromAccount(customerID, shippingFees)) {
            ShipmentDB.setShipment(orderID, shipmentAddress);
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
        System.out.println(compoundOrder);
        if (!(compoundOrder instanceof CompoundOrder)) {
            return new ShipmentResponse(false, "Invalid order", "Compound order with ID " + orderID + " not found");
        }

        Map<Integer, Address> shipmentAddress = new HashMap<>();
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            Customer customer = OrderDB.getCustomer(simpleOrder);
            int customerID = customer.getID();
            shipmentAddress.put(customerID, CustomerDB.getAddress(customer.getEmail()));
        }


        int numberOfOrders = ((CompoundOrder) compoundOrder).getOrders().size();
        double shippingFees = (calculateShippingFees() + numberOfOrders * 5) / numberOfOrders;

        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(simpleOrder).getID();
            if (!accountService.deductFromAccount(customerID, shippingFees)) {
                return new ShipmentResponse(false, "Insufficient funds", "Customer does not have enough funds to cover shipping fees");
            }
        }
        ShipmentDB.setShipment(orderID, shipmentAddress);
        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            sendShipmentNotification(simpleOrder);
            simpleOrder.setStatus(OrderStatus.Pending);
        }
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

        Shipment shipment = ShipmentDB.getShipment(shipmentID);
        if (shipment == null) {
            return new ShipmentResponse(false, "Shipment not found", "Shipment with ID " + shipmentID + " not found");
        }

        if (!checkShipmentTime(shipment.getShipmentTime())) {
            return new ShipmentResponse(false, "Shipment cancellation failed", "Shipment cannot be cancelled after 3 minutes");
        }

        assert OrderDB.getInstance(shipment.getOrderID()) != null;
        int customerID = OrderDB.getCustomer(OrderDB.getInstance(shipment.getOrderID())).getID();
        double refundedFees = calculateShippingFees();
        accountService.addToAccount(customerID, refundedFees);

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

        Shipment shipment = ShipmentDB.getShipment(shipmentID);
        if (shipment == null) {
            return new ShipmentResponse(false, "Shipment not found", "Shipment with ID " + shipmentID + " not found");
        }

        if (!checkShipmentTime(shipment.getShipmentTime())) {
            return new ShipmentResponse(false, "Shipment cancellation failed", "Shipment cannot be cancelled after 3 minutes");
        }

        Order compoundOrder = OrderDB.getInstance(shipment.getOrderID());
        assert compoundOrder != null;
        int numberOfOrders = ((CompoundOrder) compoundOrder).getOrders().size();
        double refundedFees = (calculateShippingFees() + numberOfOrders * 5) / numberOfOrders;

        for (Order simpleOrder : ((CompoundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(simpleOrder).getID();
            accountService.addToAccount(customerID, refundedFees);
        }

        ShipmentDB.removeShipment(shipment);
        return new ShipmentResponse(true, "Shipment cancellation successful", shipment);
    }


    /**
     * Calculate the shipping fees for an order.
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
        long differenceInMillis = shipmentTime.getTime() - currentTime.getTime();
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
