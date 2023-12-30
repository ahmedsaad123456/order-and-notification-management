package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.ShipmentTemplateDB;
import com.ordersManagment.Service.Model.Customer;


public class ShipmentTemplate extends Template{

    public ShipmentTemplate(Customer c){
        super(c);
    }

    public String getMessage() {
        String temp = ShipmentTemplateDB.getTemplate();

        return replacePlaceholders(temp);
    }
}
