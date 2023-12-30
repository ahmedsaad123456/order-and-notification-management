package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Response.ProductResponse;
import com.ordersManagment.Service.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/product")


/**
 * ProductController class
 * used to add product assuming that the same product has the same serial number
 * so if we add product with serial number is already exist in the database
 * the amount of this product will increase by 1
 * get product by serial number
 * get all products
 * get category
 *
 *
 *  get help from ProductService class
 *
 */
public class ProductController {

    private final ProductService pService;

    //------------------------------------------------------------------------------------------------------------

    // add product
    @PostMapping(value = "/add")
    public ProductResponse addProduct(@RequestBody Product p){
        return pService.addProduct(p);

    }

    //------------------------------------------------------------------------------------------------------------

    // get all products
    @GetMapping (value = "/AllProducts")
    public ArrayList<Product> getAllProducts(){
        return pService.getAllProduct();
    }

    //------------------------------------------------------------------------------------------------------------

    // get product by serial number
    @GetMapping(value = "/show/{serialNumber}")
    public ProductResponse getProductBySerialNumber(@PathVariable("serialNumber") String serialNumber){
        return pService.getProductBySerialNumber(serialNumber);
    }

    //------------------------------------------------------------------------------------------------------------

    // get category
    @GetMapping(value = "/category")
    public HashMap<String ,Integer> getAllCategories(){
        return pService.getAllCategories();
    }

    //------------------------------------------------------------------------------------------------------------




}
