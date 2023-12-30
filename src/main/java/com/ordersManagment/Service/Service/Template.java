package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Template {
    private Customer customer;

    public abstract String getMessage();

    protected String replacePlaceholders(String template) {
        // Replace {customerName} with actual customer name
        template = template.replace("{customerName}", customer.getName());

        // Replace {orderID} with actual order ID
        template = template.replace("{orderID}", String.valueOf(customer.getOrderID()));

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
