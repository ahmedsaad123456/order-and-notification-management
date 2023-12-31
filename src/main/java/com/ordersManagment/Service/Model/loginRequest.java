package com.ordersManagment.Service.Model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class loginRequest {
    private String email;
    private String password;

    loginRequest(){

    }


}
