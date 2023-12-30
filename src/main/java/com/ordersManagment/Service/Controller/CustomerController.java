package com.ordersManagment.Service.Controller;
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
@RequestMapping(value = "/api/Auth")

/**
 * CustomerController class
 * used to sign up, login, get all customers and get address
 * get help from CustomerService class
 */

public class CustomerController {

    private final CustomerService CustomerService;

    //------------------------------------------------------------------------------------------------------------

    // sign up
    @PostMapping(value = "/customer/signUp")
    public CustomerResponse signUp(@RequestBody Customer customer){
        return CustomerService.addCustomer(customer);
    }

    //------------------------------------------------------------------------------------------------------------

    // login
    @GetMapping(value = "/customer/login/{email}/{password}")
    public CustomerResponse login(@PathVariable("email") String email, @PathVariable("password") String password){

        return CustomerService.login(email, password);
    }

    //------------------------------------------------------------------------------------------------------------

    // get all customers
    @GetMapping(value = "/customer/AllCustomers")
    public ArrayList<Customer> getAllCustomers(){
        return CustomerService.getAllCustomers();
    }

    //------------------------------------------------------------------------------------------------------------



}
