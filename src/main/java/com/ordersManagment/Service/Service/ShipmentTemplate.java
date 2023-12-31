package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.ShipmentTemplateDB;
import com.ordersManagment.Service.Model.Order;


/**
 * ShipmentTemplate class
 *
 * used to get template from shipmentTemplateDB
 *
 */
public class ShipmentTemplate extends Template{

    public ShipmentTemplate(Order o){
        super(o);
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get message after replace the placeholders
     *
     * @return message
     */

    public String getMessage() {
        String temp = ShipmentTemplateDB.getTemplate(order.getCustomer());

        return replacePlaceholders(temp);
    }


    //------------------------------------------------------------------------------------------------------------

}
