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

public class NotificationService {
    @NonNull
    private Template template;

    private Notification notification;



    private NotificationSender notificationSender;

    public void chooseChannelAndSend(Customer c ){
        NotificationSender s = null;
        if (c.getPreferredChannel().equals("All")) {
            s = new EmailNotificationSender();
            s = new SMSNotificationSender(s);
        } else if (c.getPreferredChannel().equals("SMS")) {
            s = new SMSNotificationSender();

        } else if (c.getPreferredChannel().equals("Email")) {
            s = new EmailNotificationSender();
        }

        assert s != null;
        notificationSender = s;
        sendNotification();

    }
    private void sendNotification(){
        createNotification();
        notificationSender.sendNotification(notification);
    }


    private void createNotification(){
        Instant currentInstant = Instant.now();
        Date currentDate =  Date.from(currentInstant);
        Time currentTime = new Time(System.currentTimeMillis());
        String message = template.getMessage();

        notification = new Notification(currentDate , currentTime , message);

    }

    public static Queue<Notification> getSMSNotifications(){
        return SMSNotificationDB.getAllNotifications();
    }

    public Queue<Notification> getEmailNotifications(){
        return EmailNotificationDB.getAllNotifications();
    }








}
