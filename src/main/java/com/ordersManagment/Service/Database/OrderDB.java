package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.CompundOrder;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
public class OrderDB extends Database{
    @Getter
    private static ArrayList<Order> orders;

    static {
        orders = new ArrayList<>();
    }


    public static Order getInstance(int orderID){
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order;
            }
        }
        return null;
    }

    public static Customer getCustomer(Order order){
        return order.getCustomer();
    }

}
