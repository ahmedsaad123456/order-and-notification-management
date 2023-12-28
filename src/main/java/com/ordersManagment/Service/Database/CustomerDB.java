package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
public class CustomerDB extends Database{

    @Getter
    private static ArrayList<Customer> customers;

    static {
        customers = new ArrayList<>();
    }

    public static Customer getInstance(int ID){
        for (Customer customer : customers) {
            if (customer.getID() == ID) {
                return customer;
            }
        }
        return null;
    }

    public static Customer getCustomerByName(String name){
        for (Customer customer : customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }

    public static Customer getCustomerByEmail(String email){
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void createInstance(Object object) {
        customers.add((Customer) object);
    }

    public static void save(Object object){ // called from service
        CustomerDB customerDB = new CustomerDB();
        customerDB.createInstance(object);
    }
}