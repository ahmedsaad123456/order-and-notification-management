package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.OrderTemplateDB;
import com.ordersManagment.Service.Model.Order;


public class OrderTemplate extends Template{


    public OrderTemplate(Order o){
        super(o);
    }

    @Override
    public String getMessage() {
        String temp = OrderTemplateDB.getTemplate();

        return replacePlaceholders(temp);
    }
}
