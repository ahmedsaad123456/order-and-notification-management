package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.Database;
import com.ordersManagment.Service.Database.ProductDB;
import com.ordersManagment.Service.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {


    public String addProduct(Product p){


        ProductDB.getProducts().add(p);

        return "Added Successfully";
    }


    public ArrayList<Product> getAllProduct(){
        return ProductDB.getProducts();
    }

    public Product getProduct(String name){
        ArrayList<Product> products = ProductDB.getProducts();
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }

        return null;
    }
}
