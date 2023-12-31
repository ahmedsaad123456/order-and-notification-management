package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * ShipmentTemplateDB class
 *
 * used to store shipment templates in different language and different number of placeholders according to Customer attributes
 * this is not violate OCP because if this app will be real app we allow admin to add new template in any language
 * by create endpoint for that
 *
 *
 *
 */
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

    //------------------------------------------------------------------------------------------------------------


    /**
     * get random template from list according to customer language
     *
     * @param customer to know the language
     * @return random template
     */
    public static String getTemplate(Customer customer) {
        Random random = new Random();
        List<String> templates = shipmentTemplates.get(customer.getLanguage());


        int randomIndex = random.nextInt(templates.size());


        return templates.get(randomIndex);
    }

    //------------------------------------------------------------------------------------------------------------

}
