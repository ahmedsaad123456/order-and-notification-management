package com.ordersManagment.Service.Service;


import com.ordersManagment.Service.Database.ProductDB;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * ProductService class
 *
 * used to add product assuming that the same product has the same serial number
 * so if we add product with serial number is already exist in the database
 * the amount of this product will increase by 1
 * get product by serial number
 * get all products
 * get category
 *
 *
 * get help from ProductDB class
 *
 */
@Service
public class ProductService {


    /**
     * add product assuming that the same product has the same serial number
     * so if we add product with serial number is already exist in the database
     * first it should have the same name then
     * the amount of this product will increase by 1
     *
     * @param p product that we want to add it to the database
     * @return ProductResponse that contains the product object and the three massages
     * one is increased amount +1 when add product with serial number is already exist
     * two product is existed in system but in different name when add product with serial number is already exist
     * and in different name
     * three is Added Successfully when add product with serial number isn't exist
     */
    public ProductResponse addProduct(Product p){

        int amount = ProductDB.getProductAmountBySerialNumber(p.getSerialNumber());

        if(amount!=0){
            if(!p.getName().equals(ProductDB.getProductBySerialNumber(p.getSerialNumber()).getName())){
                return new ProductResponse(false , "Added failed" , "product is existed in system but in different name");
            }

            ProductDB.updateCategory(p);
            ProductDB.updateProductAmount(p , amount+1);
            p.setAmount(amount+1);
            return new ProductResponse(true , "increased amount +1" , p);

        }
        else{
            ProductDB.updateCategory(p);
            ProductDB.saveProduct(p);
            p.setAmount(1);
            return new ProductResponse(true , "Added Successfully" , p);

        }

    }

    //------------------------------------------------------------------------------------------------------------


    /**
     * get all products
     *
     * @return all products
     */
    public ArrayList<Product> getAllProduct(){
        return ProductDB.getProducts();
    }

    //------------------------------------------------------------------------------------------------------------


    /**
     * get product by serial number
     *
     * @param serialNumber of the product that we want to get it
     * @return ProductResponse true if the product is existed and false if isn't existed
     */
    public ProductResponse getProductBySerialNumber(String serialNumber){
        Product p = ProductDB.getProductBySerialNumber(serialNumber);
        if(p!= null){
            return new ProductResponse(true , "product is exist" , p);
        }

        return new ProductResponse(false ,"please enter serial number correctly", "product is not exist");
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get categories
     *
     * @return all categories
     */

    public HashMap<String , Integer> getAllCategories(){ return ProductDB.getCategory();}




}
