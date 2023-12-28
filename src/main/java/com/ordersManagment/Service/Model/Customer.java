package com.ordersManagment.Service.Model;

import com.ordersManagment.Service.Enums.Language;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

@AllArgsConstructor
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
    private Language language;

    private int orderID;


}
