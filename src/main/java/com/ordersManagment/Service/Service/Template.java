package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service



public abstract class Template {


    private Order order;





    public abstract String getMessage();

    protected String replacePlaceholders(String template) {
        Customer customer = CustomerDB.getCustomerByID(order.getCustomer().getID());
        // Replace {customerName} with actual customer name
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
}
