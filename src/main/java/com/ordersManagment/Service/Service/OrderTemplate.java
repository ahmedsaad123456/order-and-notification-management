package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.OrderTemplateDB;
import com.ordersManagment.Service.Model.Customer;


public class OrderTemplate extends Template{


    public OrderTemplate(Customer c){
        super(c);
    }

    @Override
    public String getMessage() {
        String temp = OrderTemplateDB.getTemplate();

        return replacePlaceholders(temp);
    }
}
