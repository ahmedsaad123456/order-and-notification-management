package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Vector;

@Setter
public class OrderDB extends Database{
    @Getter
    private static Vector<Order> orders;

    @Getter
    private static int nextID;

    static {
        orders = new Vector<>();
        nextID = 0;
    }


    @Override
    public void createInstance(Object object) {
        orders.add((Order) object);
    }

    public static Order getInstance(int orderID){
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }

    public static void addOrder(Order order){
        orders.add(order);
        nextID++;
    }

    public static void deleteOrder(int orderID){
        orders.remove(getInstance(orderID));
    }

    public static Customer getCustomer(Order order){
        return order.getCustomer();
    }

}