package com.ordersManagment.Service.Service;
import com.ordersManagment.Service.Response.CustomerResponse;
import org.springframework.stereotype.Service;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Database.CustomerDB;
import java.util.ArrayList;
import java.util.Objects;

@Service

/**
 * CustomerService class
 * used to add customer, login, get all customers
 * get help from CustomerDB class to access database
 */

public class CustomerService {

    /**
     * add customer / sign up
     * @param customer
     * @return CustomerResponse: return true and customer if added successfully, false if not added and error message
     */
    public CustomerResponse addCustomer (Customer customer){

        if(IsExistByEmail(customer.getEmail())){ // email already exist

            System.out.println("Email already exist");
            return new CustomerResponse(false, "Customer not added","Email already exist");


        } else if(customer.getName().length() < 3){ // name must be at least 3 characters

            System.out.println("Name must be at least 3 characters");
            return new CustomerResponse(false, "Customer not added","Name must be at least 3 characters");


        } else if(customer.getMobileNumber().length() != 11){ // mobile number must be 11 digits

            System.out.println("Invalid mobile number");
            return new CustomerResponse(false, "Customer not added","Invalid mobile number");


        } else if(customer.getEmail().length() < 5){ // email must be at least 5 characters

            System.out.println("Invalid email");
            return new CustomerResponse(false, "Customer not added","Invalid email");


        } else if(customer.getAddress().length() < 3){ // location must be at least 3 characters

            System.out.println("Invalid Address");
            return new CustomerResponse(false, "Customer not added","Invalid Address");


        } else if(customer.getPassword().length() < 8){ // password must be at least 8 characters

            System.out.println("Password must be at least 8 characters");
            return new CustomerResponse(false, "Customer not added","Password must be at least 8 characters");


        } else if(customer.getBalance() < 0){ // balance must be positive

            System.out.println("Invalid balance");
            return new CustomerResponse(false, "Customer not added","Invalid balance");


        } else if(!Objects.equals(customer.getLanguage(), "English") && !Objects.equals(customer.getLanguage(), "Arabic")){// language must be English or Arabic

            System.out.println("Invalid language");
            return new CustomerResponse(false, "Customer not added","Invalid language");


        }else if (IsExistByName(customer.getName())) { // name already exist

            System.out.println("Name already exist");
            return new CustomerResponse(false, "Customer not added", "Name already exist");

        } else { // all data is valid: return true and customer

            Customer c = new Customer();

            // set customer data :
            // name, mobile number, email, address, balance, password, language, preferred channel
            // remaining data : ID, orderID will be set in other classes

            c.setName(customer.getName());
            c.setMobileNumber(customer.getMobileNumber());
            c.setEmail(customer.getEmail());
            c.setBalance(customer.getBalance());
            c.setPassword(customer.getPassword());
            c.setLanguage(customer.getLanguage());
            c.setAddress(customer.getAddress());
            c.setPreferredChannel(customer.getPreferredChannel());

            CustomerDB.saveCustomer(c); // add customer to database

            System.out.println("Customer added successfully");
            return new CustomerResponse(true, "Customer added successfully", c);
        }
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * login customer
     * @param email
     * @param password
     * @return CustomerResponse: return true and customer if login successfully, false if login failed
     */
    public CustomerResponse login(String email, String password){

        if(IsExistByEmail(email)){ // email exists

            Customer customer = CustomerDB.getCustomerByEmail(email);
            assert customer != null;

            if(customer.getPassword().equals(password)){ // password is correct

                System.out.println("Login successfully");
                return new CustomerResponse(true, "Login successfully", customer);

            } else{ //password is wrong

                System.out.println("Login failed");
                return new CustomerResponse(false, "Login failed","Wrong password");

            }
        } else{ // email not exist

            System.out.println("Login failed");
            return new CustomerResponse(false, "Login failed","Email not exist");

        }
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * check if email exist in database
     * @param email
     * @return true if email exist, false if not exist
     */
    public Boolean IsExistByEmail(String email){
        return CustomerDB.getCustomerByEmail(email) != null;
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get all customers
     * @return ArrayList<Customer>
     */
    public ArrayList<Customer> getAllCustomers(){
        return CustomerDB.getCustomers();
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * check if name exist in database
     * @param name
     * @return
     */
    public Boolean IsExistByName(String name){return CustomerDB.getCustomerByName(name) != null;}

}
