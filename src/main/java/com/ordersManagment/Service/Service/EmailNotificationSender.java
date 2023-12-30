package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Model.Notification;

public class EmailNotificationSender implements NotificationSender{

    private NotificationSender notificationSender;

    public EmailNotificationSender(NotificationSender s){
        notificationSender = s;
    }

    public EmailNotificationSender(){
        notificationSender = null;
    }

    @Override
    public void sendNotification(Notification notification) {
        EmailNotificationDB.addNotification(notification);
        if(notificationSender!=null){
            notificationSender.sendNotification(notification);
        }
    }
}
