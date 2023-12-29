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

public class ProductResponse {
    private boolean status;
    private String message;
    private String errorMessage;
    private Product product;

    //------------------------------------------------------------------------------------------------------------

    public ProductResponse(boolean b, String s, Product p){
        this.status = b;
        this.message = s;
        this.product = p;
    }

    //------------------------------------------------------------------------------------------------------------

    public ProductResponse(boolean b, String s) {
        this.status = b;
        this.message = s;
    }

    //------------------------------------------------------------------------------------------------------------


    public ProductResponse(boolean b, String s, String errorMessage){
        this.status = b;
        this.message = s;
        this.errorMessage = errorMessage;
    }

}
