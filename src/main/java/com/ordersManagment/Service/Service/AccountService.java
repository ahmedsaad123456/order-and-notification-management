package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Model.Customer;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    public boolean deductFromAccount(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        if (customer.getBalance() >= balance) {
            customer.setBalance(customer.getBalance() - balance);
            return true;
        }
        return false;
    }

    public boolean addToAccount(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        customer.setBalance(customer.getBalance() + balance);
        return true;
    }


    public boolean checkAccountBalance(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        return (customer.getBalance() >= balance);
    }
}
