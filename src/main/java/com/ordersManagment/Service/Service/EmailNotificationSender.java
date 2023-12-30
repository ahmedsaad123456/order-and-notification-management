package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Model.Notification;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class EmailNotificationSender implements NotificationSender{

    private final NotificationSender notificationSender;

    EmailNotificationSender(){
        this.notificationSender = null;
    }

    @Override
    public void sendNotification(Notification notification) {
        EmailNotificationDB.addNotification(notification);
        if(notificationSender!=null){
            notificationSender.sendNotification(notification);
        }
    }
}
