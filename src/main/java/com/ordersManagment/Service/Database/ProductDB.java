package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter

public class ProductDB extends Database{

    @Getter
    private static ArrayList<Product> products;

    static {
        products = new ArrayList<>();
    }

}
