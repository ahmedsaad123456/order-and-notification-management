package com.ordersManagment.Service.Database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MostNotificationTemplateDB {

    private static final HashMap<String , Integer> sentTemplate;

    static {
        sentTemplate = new HashMap<>();
    }


    public static void addTemplate(String temp){
        sentTemplate.put(temp , sentTemplate.getOrDefault(temp , 0) +1);
    }

    public static HashMap<String , Integer>getAllSentTemplates(){
        return sentTemplate;
    }


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
}
