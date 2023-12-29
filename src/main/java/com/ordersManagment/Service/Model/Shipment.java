package com.ordersManagment.Service.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

@AllArgsConstructor
public class Shipment {
    int shipmentID;
    int orderID;
    String shipmentDate;
    String shipmentAddress;
}
