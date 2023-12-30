package com.ordersManagment.Service.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ShipmentTemplateDB extends Database{
    private static final ArrayList<String> shipmentTemplate;

    static {
        shipmentTemplate = new ArrayList<>(Arrays.asList(
                "Dear {customerName},Great news! Your order (ID: {orderID}) has been shipped",
                "Hello {customerName}, Exciting news! Your order (ID: {orderID}) is on its way! We've just shipped it. your shipping address is {customerAddress}"
        )
        );
    }

    public static String getTemplate(){
        Random random = new Random();
        int randomIndex = random.nextInt(shipmentTemplate.size());

        return shipmentTemplate.get(randomIndex);
    }
}
