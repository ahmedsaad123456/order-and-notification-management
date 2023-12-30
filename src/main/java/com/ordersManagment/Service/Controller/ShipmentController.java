package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Response.ShipmentResponse;
import com.ordersManagment.Service.Service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
@RequestMapping("/Shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;
    // Place Simple Order Shipment
    @PostMapping(value = "/place/simple/{orderID}")
    public ShipmentResponse placeSimpleOrderShipment(@PathVariable("orderID") int orderID) {
        return shipmentService.shipSimpleOrder(orderID);
    }

    // Place Compound Order Shipment
    @PostMapping(value = "/place/compound/{orderID}")
    public ShipmentResponse placeCompoundOrderShipment(@PathVariable("orderID") int orderID) {
        return shipmentService.shipCompoundOrder(orderID);
    }

    // Cancel Simple Order Shipment
    @DeleteMapping (value = "/cancel/simple/{shipmentID}")
    public ShipmentResponse cancelSimpleOrderShipment(@PathVariable("shipmentID") int shipmentID) {
        return shipmentService.cancelSimpleOrderShipment(shipmentID);
    }

    // Cancel Compound Order Shipment
    @DeleteMapping(value ="/cancel/compound/{shipmentID}")
    public ShipmentResponse cancelCompoundOrderShipment(@PathVariable("shipmentID") int shipmentID) {
        return shipmentService.cancelCompoundOrderShipment(shipmentID);
    }


}
