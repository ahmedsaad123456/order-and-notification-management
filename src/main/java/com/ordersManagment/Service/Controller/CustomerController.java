package com.ordersManagment.Service.Controller;
import com.ordersManagment.Service.Model.loginRequest;
import com.ordersManagment.Service.Response.CustomerResponse;
import com.ordersManagment.Service.Service.CustomerService;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import com.ordersManagment.Service.Model.Customer;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@Getter
@Setter
@RequestMapping(value = "/customer")

/**
 * CustomerController class
 * used to sign up, login, get all customers and get address
 * get help from CustomerService class
 */

public class CustomerController {

    private final CustomerService CustomerService;

    //------------------------------------------------------------------------------------------------------------

    // sign up
    @PostMapping(value = "/signUp")
    public CustomerResponse signUp(@RequestBody Customer customer){
        return CustomerService.addCustomer(customer);
    }

    //------------------------------------------------------------------------------------------------------------

    // login
    @PostMapping(value = "/login")
    public CustomerResponse login(@RequestBody loginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        return CustomerService.login(email, password);
    }

    //------------------------------------------------------------------------------------------------------------

    // get all customers
    @GetMapping(value = "/AllCustomers")
    public ArrayList<Customer> getAllCustomers(){
        return CustomerService.getAllCustomers();
    }

    //------------------------------------------------------------------------------------------------------------



}
