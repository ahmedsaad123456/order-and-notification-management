package com.ordersManagment.Service.Response;
import com.ordersManagment.Service.Model.Customer;
import lombok.*;

@Getter
@Setter

/**
 * CustomerResponse class
 * used to return response to the user
 */

public class CustomerResponse {
    private boolean status;
    private String message;
    private String errorMessage;
    Customer customer;

    public CustomerResponse(boolean b, String s, Customer customer) {
        this.status = b;
        this.message = s;
        this.customer = customer;
    }

    public CustomerResponse(boolean b, String s) {
        this.status = b;
        this.message = s;
    }

    public CustomerResponse(boolean b, String s, String errorMessage) {
        this.status = b;
        this.message = s;
        this.errorMessage = errorMessage;
    }


}
