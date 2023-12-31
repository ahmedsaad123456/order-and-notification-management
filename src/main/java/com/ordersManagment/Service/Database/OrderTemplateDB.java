package com.ordersManagment.Service.Database;



import com.ordersManagment.Service.Model.Customer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * OrderTemplateDB class
 *
 * used to store order templates in different language and different number of placeholders according to Customer attributes
 * this is not violate OCP because if this app will be real app we allow admin to add new template in any language
 * by create endpoint for that
 *
 *
 *
 */

public class OrderTemplateDB extends Database {

    private static final HashMap<String, List<String>> orderTemplates;

    static {
        orderTemplates = new HashMap<>();

        // English templates
        orderTemplates.put("English", Arrays.asList(
                "Dear {customerName}, Thank you for placing your order with us. Your order (ID: {orderID}) has been received and is being processed",
                "Hello {customerName}, Thank you for choosing us! Your order (ID: {orderID}) is now confirmed. Your account balance is {customerBalance}."
        ));

        // Arabic templates
        orderTemplates.put("Arabic", Arrays.asList(
                "عزيزي {customerName}، شكرًا لك على تقديم طلبك لدينا. تم استلام طلبك (الرقم: {orderID}) ويتم معالجته الآن",
                "مرحبًا {customerName}، شكرًا لاختيارك لنا! تم تأكيد طلبك (الرقم: {orderID}) الآن. رصيد حسابك هو {customerBalance}."
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
        List<String> templates = orderTemplates.get(customer.getLanguage());



        int randomIndex = random.nextInt(templates.size());


        return templates.get(randomIndex);
    }


    //------------------------------------------------------------------------------------------------------------

}
