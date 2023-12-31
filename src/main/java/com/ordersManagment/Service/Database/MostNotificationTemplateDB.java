package com.ordersManagment.Service.Database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * MostNotificationTemplateDB class
 *
 * used to store the notification templates that sent to Customer
 *
 */

public class MostNotificationTemplateDB extends Database {

    private static final HashMap<String , Integer> sentTemplate;

    static {
        sentTemplate = new HashMap<>();
    }

    //------------------------------------------------------------------------------------------------------------


    /**
     * add template and if this template is used before
     * the counter will be increased by one
     *
     * @param temp the used template
     */
    public static void addTemplate(String temp){
        sentTemplate.put(temp , sentTemplate.getOrDefault(temp , 0) +1);
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get all sent notification templates
     *
     * @return all sent notification templates
     */
    public static HashMap<String , Integer>getAllSentTemplates(){
        return sentTemplate;
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get The most sent notification template.
     *
     * @return The most sent notification template.
     */
    public static String getMostSentTemplate(){
        if (sentTemplate.isEmpty()) {
            return null;
        }

        Map.Entry<String, Integer> maxEntry = Collections.max(
                sentTemplate.entrySet(),
                Map.Entry.comparingByValue()
        );

        return maxEntry.getKey();

    }

    //------------------------------------------------------------------------------------------------------------

}
