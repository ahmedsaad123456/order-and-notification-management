package com.ordersManagment.Service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordersManagment.Service.Enums.Language;
import com.ordersManagment.Service.Enums.notificationChannel;
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

    @JsonProperty("address")
    @NonNull
    private String address;

    @JsonProperty("balance")
    @NonNull
    private Double balance;

    @JsonProperty("password")
    @NonNull
    private String password;

    @JsonProperty("language")
    @NonNull
    private Language language;

    @JsonProperty("preferredChannel")
    @NonNull
    private notificationChannel preferredChannel;



    public String getLanguage() {
        return language.toString();
    }

    public void setLanguage(String language) {
        this.language = Language.valueOf(language);
    }

    public String getPreferredChannel() { return preferredChannel.toString(); }

    public void setPreferredChannel(String Channel){this.preferredChannel = notificationChannel.valueOf(Channel);}


}
