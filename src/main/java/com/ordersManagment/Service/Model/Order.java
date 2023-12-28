package com.ordersManagment.Service.Model;

import com.ordersManagment.Service.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    protected ArrayList<Product> products;
    protected Customer customer;
    protected int OrderID;
    protected OrderStatus orderStatus;


}