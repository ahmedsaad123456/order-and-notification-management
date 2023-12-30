package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Model.Customer;
import org.springframework.stereotype.Service;


@Service
public class AccountService {
    public boolean deductFromAccount(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        assert customer != null;
        if (customer.getBalance() >= balance) {
            customer.setBalance(customer.getBalance() - balance);
            return true;
        }
        return false;
    }

    public void addToAccount(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        assert customer != null;
        customer.setBalance(customer.getBalance() + balance);
    }


    public boolean checkAccountBalance(int custID, double balance) {
        Customer customer = CustomerDB.getCustomerByID(custID);
        assert customer != null;
        return (customer.getBalance() >= balance);
    }
}
