package com.ordersManagment.Service.Model;

import com.ordersManagment.Service.Enums.OrderStatus;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SimpleOrder extends Order {
    public SimpleOrder(ArrayList<Product> products, Customer customer, int orderID, OrderStatus orderStatus , String address) {

        super(products , customer , orderID , orderStatus , address);
    }
}
