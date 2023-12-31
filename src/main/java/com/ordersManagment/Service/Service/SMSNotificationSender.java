package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.SMSNotificationDB;
import com.ordersManagment.Service.Model.Notification;

/**
 *
 * SMSNotificationSender class
 *
 * used to send notification to mobile numbers
 */
public class SMSNotificationSender implements NotificationSender{

    private final NotificationSender notificationSender;

    public SMSNotificationSender(NotificationSender s){
        notificationSender = s;
    }

    //------------------------------------------------------------------------------------------------------------

    public SMSNotificationSender(){
        notificationSender = null;
    }

    //------------------------------------------------------------------------------------------------------------


    /**
     * send notification to mobile number and to other channels of notification sender if is not null
     *
     * @param notification that will be sent
     */
    @Override
    public void sendNotification(Notification notification) {
        SMSNotificationDB.addNotification(notification);
        if(notificationSender!=null){
            notificationSender.sendNotification(notification);
        }
    }

    //------------------------------------------------------------------------------------------------------------

}
