package com.ordersManagment.Service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordersManagment.Service.Enums.OrderStatus;
import lombok.*;

import java.sql.Time;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Order {

    @JsonProperty("products")
    @NonNull
    private ArrayList<Product> products;

    @JsonProperty("customer")
    private Customer customer;

    private int OrderID;

    private OrderStatus status;

    Time orderTime;
    //-------------------------------------------------------------


}