package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Model.CompundOrder;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ShipmentService {

    private final AccountService accountService;
    @Autowired
    public ShipmentService(AccountService accountService) {
        this.accountService = accountService;
    }

    private void setShipment(int orderID) {
        Shipment shipment = new Shipment();
        shipment.setOrderID(orderID);
        Date currentDateDeprecated = new Date();
        shipment.setShipmentDate(currentDateDeprecated.toString());
        shipment.setShipmentAddress(OrderDB.getInstance(orderID).getAddress());
    }


    public void shipSimpleOrder(int orderID) {
        setShipment(orderID);
        Order order = OrderDB.getInstance(orderID);
        int customerID = OrderDB.getCustomer(order).getID();
        double shippingFees = calculateShippingFees();
        accountService.deductFromAccount(customerID, shippingFees);
    }

    public void shipCompoundOrder(int orderID) {
        setShipment(orderID);
        Order compoundOrder = OrderDB.getInstance(orderID);
        int numberOfOrders = ((CompundOrder) compoundOrder).getOrders().size();
        double shippingFees = calculateShippingFees() / numberOfOrders;
        for (Order simpleOrder : ((CompundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(simpleOrder).getID();
            accountService.deductFromAccount(customerID, shippingFees);
        }
    }

    public void cancelSimpleOrderShipment(int orderID) {
        Order order = OrderDB.getInstance(orderID);
        int customerID = OrderDB.getCustomer(order).getID();
        double refundedFees = calculateShippingFees();
        accountService.addToAccount(customerID, refundedFees);
    }

    public void cancelCompoundOrderShipment(int orderID) {
        Order compoundOrder = OrderDB.getInstance(orderID);
        int numberOfOrders = ((CompundOrder) compoundOrder).getOrders().size();
        double refundedFees = calculateShippingFees() / numberOfOrders;
        for (Order order : ((CompundOrder) compoundOrder).getOrders()) {
            int customerID = OrderDB.getCustomer(order).getID();
            accountService.addToAccount(customerID, refundedFees);
        }
    }

    private double calculateShippingFees() {
        double fees = 50;
        return fees;
    }
}
