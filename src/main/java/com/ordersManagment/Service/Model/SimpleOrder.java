package com.ordersManagment.Service.Model;

import com.ordersManagment.Service.Enums.OrderStatus;
import lombok.*;

import java.sql.Time;
import java.util.ArrayList;

@Getter
@Setter


public class SimpleOrder extends Order {
    public SimpleOrder(ArrayList<Product> products, Customer customer, int orderID, OrderStatus orderStatus, String address, Time time) {
        super(products, customer, orderID, orderStatus, address, time);
    }
}
