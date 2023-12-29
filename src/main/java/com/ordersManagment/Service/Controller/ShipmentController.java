package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Response.ShipmentResponse;
import com.ordersManagment.Service.Service.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/Shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    // Place Simple Order Shipment
    @PostMapping("/place/{orderID}")
    public ShipmentResponse placeSimpleOrderShipment(@PathVariable int orderID) {
        return shipmentService.shipSimpleOrder(orderID);
    }

    // Place Compound Order Shipment
    @PostMapping("/place/compound/{orderID}")
    public ShipmentResponse placeCompoundOrderShipment(@PathVariable int orderID) {
        return shipmentService.shipCompoundOrder(orderID);
    }

    // Cancel Simple Order Shipment
    @DeleteMapping ("/cancel/{shipmentID}")
    public ShipmentResponse cancelSimpleOrderShipment(@PathVariable int shipmentID) {
        return shipmentService.cancelSimpleOrderShipment(shipmentID);
    }

    // Cancel Compound Order Shipment
    @DeleteMapping("/cancel/compound/{shipmentID}")
    public ShipmentResponse cancelCompoundOrderShipment(@PathVariable int shipmentID) {
        return shipmentService.cancelCompoundOrderShipment(shipmentID);
    }
}
