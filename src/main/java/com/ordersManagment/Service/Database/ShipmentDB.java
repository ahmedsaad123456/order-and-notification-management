package com.ordersManagment.Service.Database;
import com.ordersManagment.Service.Model.Address;
import com.ordersManagment.Service.Model.Shipment;
import lombok.Getter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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

    public static boolean checkAddress( Map<Integer, String> shipmentAddress) {
        for (Map.Entry<Integer, String> entry : shipmentAddress.entrySet()) {
            if (entry.getValue().equals("")) {
                return false;
            }
        }
        return true;

    }

    public static void setShipment(int orderID, Map<Integer, Address> shipmentAddress) {
        Shipment shipment = new Shipment();
        shipment.setOrderID(orderID);
        Time time = new Time(new Date().getTime());
        shipment.setShipmentTime(time);
        shipment.setShipmentAddress(shipmentAddress);
        ShipmentDB.saveShipment(shipment);
    }


   public static void saveShipment(Shipment shipment){
        shipments.add(shipment);
    }

    public static void removeShipment(Shipment shipment){
        shipments.remove(shipment);
    }


}
