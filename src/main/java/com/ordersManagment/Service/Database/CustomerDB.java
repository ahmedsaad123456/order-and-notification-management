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



}
