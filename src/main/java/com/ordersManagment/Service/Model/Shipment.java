package com.ordersManagment.Service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor

@AllArgsConstructor
public class Shipment {
    @JsonProperty("shipmentID")
    private int shipmentID;

    private static int shipmentIDCounter = 1;
    @JsonProperty("orderID")
    @NonNull
    int orderID;
    @JsonProperty("shipmentTime")
    @NonNull
    Time shipmentTime;
    @JsonProperty("shipmentAddress")
    @NonNull
    Map<Integer, Address> shipmentAddress;

    public Shipment() {
        this.shipmentID = getStaticShipmentID();
        incrementShipmentID();
    }

    private static void incrementShipmentID() {
        shipmentIDCounter++;
    }
    public static int getStaticShipmentID() {
        return shipmentIDCounter;
    }

}
