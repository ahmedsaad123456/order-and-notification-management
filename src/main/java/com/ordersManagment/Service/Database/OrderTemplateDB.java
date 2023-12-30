package com.ordersManagment.Service.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class OrderTemplateDB extends Database{

    private static ArrayList<String> orderTemplate;

    static {
        orderTemplate = new ArrayList<>(Arrays.asList(
                "Dear {customerName}, Thank you for placing your order with us. Your order (ID: {orderID}) has been received and is being processed" ,
                "Hello {customerName}, Thank you, for choosing us! Your order ({orderID}) is now confirmed. Your account balance is {customerBalance}."
            )
        );
    }

    public static String getTemplate(){
        Random random = new Random();
        int randomIndex = random.nextInt(orderTemplate.size());

        return orderTemplate.get(randomIndex);
    }
}
