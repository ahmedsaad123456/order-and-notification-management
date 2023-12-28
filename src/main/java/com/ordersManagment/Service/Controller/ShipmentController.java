package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/place/{orderID}")
    public void placeSimpleOrderShipment(@PathVariable int orderID) {
        shipmentService.shipSimpleOrder(orderID);
    }

    @PostMapping("/place/{orderID}")
    public void placeCompoundOrderShipment(@PathVariable int orderID) {
        shipmentService.shipCompoundOrder(orderID);
    }

    @PostMapping("/cancel/{orderID}")
    public void cancelSimpleOrderShipment(@PathVariable int orderID) {
        shipmentService.cancelSimpleOrderShipment(orderID);
    }

    @PostMapping("/cancel/{orderID}")
    public void cancelCompoundOrderShipment(@PathVariable int orderID) {
        shipmentService.cancelCompoundOrderShipment(orderID);
    }

}