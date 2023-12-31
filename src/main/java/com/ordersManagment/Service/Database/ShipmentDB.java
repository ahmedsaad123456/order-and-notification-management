package com.ordersManagment.Service.Database;
import com.ordersManagment.Service.Model.Address;
import com.ordersManagment.Service.Model.Shipment;
import lombok.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Getter
public class ShipmentDB extends Database{
    private static final ArrayList<Shipment> shipments;

    static {
        shipments = new ArrayList<>();
    }

    /**
     * get shipment by shipment id
     * @param shipmentID shipment id that we want to get shipment by it
     * @return shipment that has the same shipment id
     */
    public static Shipment getShipmentByShipmentID(int shipmentID){
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentID() == shipmentID) {
                return shipment;
            }
        }
        return null;
    }

    /**
     * get shipment by order id
     * @param orderID order id that we want to get shipment by it
     * @return shipment that has the same order id
     */

    public static Shipment getShipmentByOrderID(int orderID){
        for (Shipment shipment : shipments) {
            if (shipment.getOrderID() == orderID) {
                return shipment;
            }
        }
        return null;
    }

    /**
     * set shipment for order and save it in database
     * @param orderID order id that we want to set shipment for it
     * @param shipmentAddress shipment address that we want to set for order to be shipped to it
     */
    public static void setShipment(int orderID, Map<Integer, Address> shipmentAddress) {
        Shipment shipment = new Shipment();
        shipment.setOrderID(orderID);
        Time time = new Time(new Date().getTime());
        shipment.setShipmentTime(time);
        shipment.setShipmentAddress(shipmentAddress);
        ShipmentDB.saveShipment(shipment);
    }


    /**
     * save shipment in database
     * @param shipment shipment that we want to save it
     */
   public static void saveShipment(Shipment shipment){
        shipments.add(shipment);
    }

    /**
     * remove shipment from database
     * @param shipment shipment that we want to remove it
     */
    public static void removeShipment(Shipment shipment){
        shipments.remove(shipment);
    }


}
