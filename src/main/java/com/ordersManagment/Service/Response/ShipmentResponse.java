package com.ordersManagment.Service.Response;

import com.ordersManagment.Service.Model.Shipment;
import lombok.Getter;
import lombok.Setter;

/**
 * ShipmentResponse class
 * used to return response to the user for shipment operations
 */
@Getter
@Setter
public class ShipmentResponse extends Response {

    Shipment shipment;

    //------------------------------------------------------------------------------------------------------------

    public ShipmentResponse(boolean b, String s, Shipment shipment) {
        super(b, s);
        this.shipment = shipment;
    }


    //------------------------------------------------------------------------------------------------------------

    public ShipmentResponse(boolean b, String s, String errorMessage) {
        super(b, s, errorMessage);
    }

}
