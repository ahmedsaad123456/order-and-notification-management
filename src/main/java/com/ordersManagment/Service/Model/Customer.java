package com.ordersManagment.Service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordersManagment.Service.Enums.Language;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor

@AllArgsConstructor
public class Customer {

    @JsonProperty("id")
    private int ID;

    @JsonProperty("name")
    @NonNull
    private String name;

    @JsonProperty("mobileNumber")
    @NonNull
    private String MobileNumber;

    @JsonProperty("email")
    @NonNull
    private String email;

    @JsonProperty("location")
    @NonNull
    private String location;

    @JsonProperty("balance")
    @NonNull
    private Double balance;

    @JsonProperty("password")
    @NonNull
    private String password;

    @JsonProperty("language")
    @NonNull
    private Language language;

    @JsonProperty("orderID")
    private int orderID;

    public String getLanguage() {
        return language.toString();
    }

    public void setLanguage(String language) {
        this.language = Language.valueOf(language);
    }


}
