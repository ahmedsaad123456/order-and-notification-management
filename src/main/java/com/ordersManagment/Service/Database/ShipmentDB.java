package com.ordersManagment.Service.Database;
import com.ordersManagment.Service.Model.Shipment;
import lombok.Getter;

import java.util.ArrayList;

public class ShipmentDB extends Database{
    @Getter
    private static ArrayList<Shipment> shipments;

    static {
        shipments = new ArrayList<>();
    }

    public static Shipment getShipment(int shipmentID){
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentID() == shipmentID) {
                return shipment;
            }
        }
        return null;
    }

    public static Shipment getShipmentByOrderID(int orderID){
        for (Shipment shipment : shipments) {
            if (shipment.getOrderID() == orderID) {
                return shipment;
            }
        }
        return null;
    }


   public static void saveShipment(Shipment shipment){
        shipments.add(shipment);
    }

    public static void removeShipment(Shipment shipment){
        shipments.remove(shipment);
    }


}
