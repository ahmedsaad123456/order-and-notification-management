package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Model.Notification;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

/**
 *
 * EmailNotificationSender class
 *
 * used to send notification to emails
 *
 */
public class EmailNotificationSender implements NotificationSender{

    private final NotificationSender notificationSender;

    EmailNotificationSender(){
        this.notificationSender = null;
    }


    //------------------------------------------------------------------------------------------------------------


    /**
     * send notification to email and to other channels of notification sender if is not null
     *
     * @param notification that will be sent
     */
    @Override
    public void sendNotification(Notification notification) {
        EmailNotificationDB.addNotification(notification);
        if(notificationSender!=null){
            notificationSender.sendNotification(notification);
        }
    }


    //------------------------------------------------------------------------------------------------------------

}
