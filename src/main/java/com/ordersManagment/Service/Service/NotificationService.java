package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Database.SMSNotificationDB;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Notification;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.Queue;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Service

/**
 * NotificationService class
 *
 * used to choose the channel to sent to it
 * and send notification
 * and get all sms notification and email notification
 *
 */

public class NotificationService {
    @NonNull
    private Template template;

    private Notification notification;


    private NotificationSender notificationSender;


    //------------------------------------------------------------------------------------------------------------

    /**
     * choose channel according to the preferred channel of the Customer
     * using the decorator pattern
     * and then send the notification
     *
     *
     * @param c customer to get the preferred channel
     */
    public void chooseChannelAndSend(Customer c ){
        NotificationSender s = null;
        if (c.getPreferredChannel().equals("All")) {
            s = new EmailNotificationSender();
            s = new SMSNotificationSender(s);
            SMSNotificationDB.addMobileNumber(c.getMobileNumber());
            EmailNotificationDB.addEmail(c.getEmail());
        } else if (c.getPreferredChannel().equals("SMS")) {
            s = new SMSNotificationSender();
            SMSNotificationDB.addMobileNumber(c.getMobileNumber());


        } else if (c.getPreferredChannel().equals("Email")) {
            s = new EmailNotificationSender();
            EmailNotificationDB.addEmail(c.getEmail());

        }

        assert s != null;
        notificationSender = s;
        sendNotification();

    }

    //------------------------------------------------------------------------------------------------------------

    /**
     *
     * send notification using notification sender
     */
    private void sendNotification(){
        createNotification();
        notificationSender.sendNotification(notification);
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * create notification using the template and the current date and time
     *
     */

    private void createNotification(){
        Instant currentInstant = Instant.now();
        Date currentDate =  Date.from(currentInstant);
        Time currentTime = new Time(System.currentTimeMillis());
        String message = template.getMessage();

        notification = new Notification(currentDate , currentTime , message);

    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get all sms notification
     *
     * @return all sms notification
     */
    public static Queue<Notification> getSMSNotifications(){
        return SMSNotificationDB.getAllNotifications();
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get all email notification
     *
     * @return all email notification
     */
    public Queue<Notification> getEmailNotifications(){
        return EmailNotificationDB.getAllNotifications();
    }


    //------------------------------------------------------------------------------------------------------------






}
