package com.ordersManagment.Service.Response;

import com.ordersManagment.Service.Model.Order;
import lombok.*;

@Getter
@Setter


public class OrderResponse extends Response{

    Order order;

    public OrderResponse(boolean b, String s, Order order) {
        super(b, s);
        this.order = order;
    }

    public OrderResponse(boolean b, String s, String errorMessage) {
        super(b, s, errorMessage);
    }

    public OrderResponse(boolean b, String s) {
        super(b, s);
    }



}
