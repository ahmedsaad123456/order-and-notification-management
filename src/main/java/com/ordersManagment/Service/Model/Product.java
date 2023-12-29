package com.ordersManagment.Service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor


public class Product {

    @JsonProperty("serialNumber")
    @NonNull
    private String serialNumber;

    @JsonProperty("name")
    @NonNull
    private String name;

    @JsonProperty("vendor")
    @NonNull
    private String vendor;

    @JsonProperty("category")
    @NonNull
    private String category;

    @JsonProperty("price")
    @NonNull
    private Double price;

    @JsonProperty("amount")
    private int amount;


}
