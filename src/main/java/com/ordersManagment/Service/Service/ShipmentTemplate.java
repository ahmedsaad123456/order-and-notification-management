package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.ShipmentTemplateDB;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;


public class ShipmentTemplate extends Template{

    public ShipmentTemplate(Order o){
        super(o);
    }

    public String getMessage() {
        String temp = ShipmentTemplateDB.getTemplate();

        return replacePlaceholders(temp);
    }
}
