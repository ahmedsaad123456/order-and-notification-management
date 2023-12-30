package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Database.SMSNotificationDB;
import com.ordersManagment.Service.Model.Notification;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.util.Queue;

@Getter
@Setter
@RequiredArgsConstructor

public class NotificationService {
    @NonNull
    private Template template;

    private Notification notification;


    @NonNull
    private NotificationSender notificationSender;

    public void sendNotification(){
        createNotification();
        notificationSender.sendNotification(notification);
    };


    private void createNotification(){
        Instant currentInstant = Instant.now();
        Date currentDate = (Date) Date.from(currentInstant);
        Time currentTime = new Time(System.currentTimeMillis());
        String message = template.getMessage();

        notification = new Notification(currentDate , currentTime , message);

    }

    public Queue<Notification> getSMSNotifications(){
        return SMSNotificationDB.getAllNotifications();
    };

    public Queue<Notification> getEmailNotifications(){
        return EmailNotificationDB.getAllNotifications();
    };








}
