package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.SMSNotificationDB;
import com.ordersManagment.Service.Model.Notification;

public class SMSNotificationSender implements NotificationSender{

    private final NotificationSender notificationSender;

    public SMSNotificationSender(NotificationSender s){
        notificationSender = s;
    }

    public SMSNotificationSender(){
        notificationSender = null;
    }
    @Override
    public void sendNotification(Notification notification) {
        SMSNotificationDB.addNotification(notification);
        if(notificationSender!=null){
            notificationSender.sendNotification(notification);
        }    }
}
