package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.MostNotificationTemplateDB;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service

/**
 *
 * Template class
 *
 *
 * used to get message and replace placeholders with the customer attributes
 *
 *
 */


public abstract class Template {


    protected Order order;


    /**
     * abstract method to get message
     *
     * @return message
     */
    public abstract String getMessage();

    //------------------------------------------------------------------------------------------------------------

    /**
     * replace placeholders in the template with the customer attributes
     *
     * @param template of the message
     * @return message after replace the placeholders
     */
    protected String replacePlaceholders(String template) {

        MostNotificationTemplateDB.addTemplate(template);

        Customer customer = CustomerDB.getCustomerByID(order.getCustomer().getID());
        // Replace {customerName} with actual customer name
        assert customer != null;
        template = template.replace("{customerName}", customer.getName());

        // Replace {orderID} with actual order ID
        template = template.replace("{orderID}", String.valueOf(order.getOrderID()));

        // Replace {customerBalance} with actual customer balance
        template = template.replace("{customerBalance}", String.valueOf(customer.getBalance()));

        // Replace {customerMobileNumber} with actual customer mobile number
        template = template.replace("{customerMobileNumber}", customer.getMobileNumber());

        // Replace {customerEmail} with actual customer email
        template = template.replace("{customerEmail}", customer.getEmail());

        // Replace {customerAddress} with actual customer address
        template = template.replace("{customerAddress}", customer.getAddress());

        return template;
    }


    //------------------------------------------------------------------------------------------------------------

}
