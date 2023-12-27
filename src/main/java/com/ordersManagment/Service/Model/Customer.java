package com.ordersManagment.Service.Model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor


public class Customer {

    private int ID;

    @NonNull
    private String name;

    @NonNull
    private String MobileNumber;

    @NonNull
    private String email;

    @NonNull
    private String location;

    @NonNull
    private Double balance;

    @NonNull
    private String password;

    @NonNull
    private String language;

    private int orderID;


}
