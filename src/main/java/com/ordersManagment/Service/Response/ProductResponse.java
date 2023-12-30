package com.ordersManagment.Service.Response;

import com.ordersManagment.Service.Model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * ProductResponse class
 *
 * used to return response to the user when dealing with the products
 */

public class ProductResponse extends Response {

    private Product product;

    //------------------------------------------------------------------------------------------------------------

    public ProductResponse(boolean b, String s, Product p){
        super(b,s);
        this.product = p;
    }

    //------------------------------------------------------------------------------------------------------------


    public ProductResponse(boolean b, String s, String errorMessage){
        super(b,s,errorMessage);
    }

}
