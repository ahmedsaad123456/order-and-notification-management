package com.ordersManagment.Service.Response;
import com.ordersManagment.Service.Model.Customer;
import lombok.*;

@Getter
@Setter

/**
 * CustomerResponse class
 * used to return response to the user
 */

public class CustomerResponse extends Response {

    Customer customer;

    //------------------------------------------------------------------------------------------------------------

    public CustomerResponse(boolean b, String s, Customer customer) {
        super(b, s);
        this.customer = customer;
    }

    //------------------------------------------------------------------------------------------------------------

    public CustomerResponse(boolean b, String s, String errorMessage) {
        super(b, s, errorMessage);
    }


}
