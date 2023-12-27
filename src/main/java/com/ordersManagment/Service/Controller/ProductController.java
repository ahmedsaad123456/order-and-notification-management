package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@AllArgsConstructor


public class ProductController {


    private final ProductService pService;



    @PostMapping(value = "/product")
    public String addProduct(@RequestBody Product p){
        return pService.addProduct(p);

    }


    @GetMapping (value = "/product")
    public ArrayList<Product> getAllProducts(){
        return pService.getAllProduct();
    }

    @GetMapping(value = "/product/{name}")
    public Product getProduct(@PathVariable("name") String name){
        return pService.getProduct(name);
    }



}
