package com.ordersManagment.Service.Model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor


public class Product {

    @NonNull
    private String serialNumber;

    @NonNull
    private String name;

    @NonNull
    private String vendor;

    @NonNull
    private String category;

    @NonNull
    private Double price;


    private int amount;


}
