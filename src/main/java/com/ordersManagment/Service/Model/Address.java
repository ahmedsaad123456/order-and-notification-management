package com.ordersManagment.Service.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Address {

        @JsonProperty("city")
        @NonNull
        private String city;

        @JsonProperty("area")
        @NonNull
        private String area;

        @JsonProperty("street")
        @NonNull
        private String street;

        @JsonProperty("houseNumber")
        private String  houseNumber;

        private String ALlAddress;

        //------------------------------------------------------------------------------------------------------------

        /**
         * get all address as one string
         * parse it to city, area, street, houseNumber
         * @return String: all address
         */
        public void setALlAddress(String add) {

                System.out.println("add: " + add);

                this.ALlAddress = add;
                String[] temp = add.split("/");

                this.city = temp[0];
                this.area = temp[1];
                this.street = temp[2];
                this.houseNumber = temp[3];
        }

        //------------------------------------------------------------------------------------------------------------

}
