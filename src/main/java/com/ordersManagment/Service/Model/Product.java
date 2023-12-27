package com.ordersManagment.Service.Model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class Product {

    private String serialNumber;

    private String name;

    private String vendor;

    private String category;

    private Double price;


}
