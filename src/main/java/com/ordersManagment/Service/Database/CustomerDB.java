package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter

/**
 * CustomerDB class
 * used to store customers data
 */

public class CustomerDB extends Database{

    @Getter
    private static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get customer by ID
     * @param ID
     * @return Customer: return customer if found, null if not found
     */

    public static Customer getCustomerByID(int ID){

        for (Customer customer : customers) {

            if (customer.getID() == ID) {
                return customer;
            }

        }
        return null;
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get customer by name
     * @param name
     * @return Customer: return customer if found, null if not found
     */

    public static Customer getCustomerByName(String name){

        for (Customer customer : customers) {

            if (customer.getName().equals(name)) {
                return customer;
            }

        }
        return null;
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get customer by email
     * @param email
     * @return Customer: return customer if found, null if not found
     */

    public static Customer getCustomerByEmail(String email){

        for (Customer customer : customers) {

            if (customer.getEmail().equals(email)) {
                return customer;
            }

        }
        return null;
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * save customer in database
     * @param object
     */

    public static void saveCustomer(Customer object) {

        if(customers.isEmpty()){

            (object).setID(1);

        } else{

            int ID=customers.get(customers.size()-1).getID()+1; // get last ID and increment it
            (object).setID(ID);
        }

        customers.add(object);
    }

}