package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.OrderTemplateDB;
import com.ordersManagment.Service.Model.Order;


/**
 * OrderTemplate class
 *
 * used to get template from OrderTemplateDB
 *
 */
public class OrderTemplate extends Template{


    public OrderTemplate(Order o){
        super(o);
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get message after replace the placeholders
     *
     * @return message
     */
    @Override
    public String getMessage() {
        String temp = OrderTemplateDB.getTemplate(order.getCustomer());

        return replacePlaceholders(temp);
    }


    //------------------------------------------------------------------------------------------------------------

}
