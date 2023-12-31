package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class ShipmentTemplateDB extends Database {
    private static final HashMap<String, List<String>> shipmentTemplates;

    static {
        shipmentTemplates = new HashMap<>();

        // English templates
        shipmentTemplates.put("English", Arrays.asList(
                "Dear {customerName}, Great news! Your order (ID: {orderID}) has been shipped",
                "Hello {customerName}, Exciting news! Your order (ID: {orderID}) is on its way! We've just shipped it. your shipping address is {customerAddress}"
        ));

        // Arabic templates
        shipmentTemplates.put("Arabic", Arrays.asList(
                "عزيزي {customerName}، أخبار رائعة! تم شحن طلبك (الرقم: {orderID})",
                "مرحبًا {customerName}، أخبار مثيرة! طلبك (الرقم: {orderID}) . عنوان الشحن الخاص بك هو {customerAddress}"
        ));
    }

    public static String getTemplate(Customer customer) {
        Random random = new Random();
        List<String> templates = shipmentTemplates.get(customer.getLanguage());


        int randomIndex = random.nextInt(templates.size());


        return templates.get(randomIndex);
    }
}
