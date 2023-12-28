package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Model.CompundOrder;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.SimpleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    private final AccountService accountService;
    private int numberOfOrders = 1;
    @Autowired
    public ShipmentService(AccountService accountService) {
        this.accountService = accountService;
    }


    public void shipSimpleOrder(int orderID) {
        Order order = OrderDB.getInstance(orderID);
        int customerID = OrderDB.getCustomer(order).getID();
        double shippingFees = calculateShippingFees();
        accountService.deductFromAccount(customerID, shippingFees);
    }

    public void shipCompoundOrder(int orderID) {
        Order compoundOrder = OrderDB.getInstance(orderID);
        numberOfOrders = ((CompundOrder) compoundOrder).getOrders().size();
        for (Order simpleOrder : ((CompundOrder) compoundOrder).getOrders()) {
            shipSimpleOrder(simpleOrder.getOrderID());
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
        numberOfOrders = ((CompundOrder) compoundOrder).getOrders().size();
        for (Order order : ((CompundOrder) compoundOrder).getOrders()) {
            cancelSimpleOrderShipment(order.getOrderID());
        }
    }

    private double calculateShippingFees() {
        double fees = 50;
        return fees / numberOfOrders;
    }
}