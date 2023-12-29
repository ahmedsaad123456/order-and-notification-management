package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter

public class ProductDB extends Database {

    @Getter
    private static ArrayList<Product> products;

    static {
        products = new ArrayList<>();
    }

    @Override
    public void createInstance(Object object) {

    }

    public static void reduceProductAmount(String serialNumber, int amount) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getSerialNumber().equals(serialNumber)) {
                products.get(i).setAmount(products.get(i).getAmount() - amount);
                break;
            }
        }
    }

    public static void increaseProductAmount(String serialNumber, int amount) {
        reduceProductAmount(serialNumber, -amount);
    }
}