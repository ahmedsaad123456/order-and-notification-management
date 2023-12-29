package com.ordersManagment.Service.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * Response class
 * used to return response to the user
 */

public abstract class Response {

    private boolean status;
    private String message;
    private String errorMessage;

    //------------------------------------------------------------------------------------------------------------

    /**
     * Response constructor for success response
     * @param b
     * @param s
     */

    public Response(boolean b, String s) {
        this.status = b;
        this.message = s;
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * Response constructor for error response
     * @param b
     * @param s
     * @param errorMessage
     */

    public Response(boolean b, String s, String errorMessage) {
        this.status = b;
        this.message = s;
        this.errorMessage = errorMessage;
    }
}
