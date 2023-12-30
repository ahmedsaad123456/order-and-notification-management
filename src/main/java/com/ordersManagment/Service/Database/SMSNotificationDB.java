package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class SMSNotificationDB extends Database {
    private static final Queue<Notification> SMSNotification;

    static {
        SMSNotification = new LinkedList<>();
    }

    public static void addNotification(Notification notification) {
        SMSNotification.add(notification);
    }

    public static Queue<Notification> getAllNotifications(){
        return SMSNotification;
    }

    @Scheduled(fixedRate = 1000)
    public void checkAndRemoveExpiredNotifications() {

        long currentTime = System.currentTimeMillis();

        Notification frontNotification = SMSNotification.peek();

        // Check if there is a notification at the front of the queue
        if (frontNotification != null) {

            // Check if the notification is older than 120 seconds
            if (isNotificationExpired(frontNotification, currentTime)) {
                SMSNotification.poll();
            }
        }
    }

    private boolean isNotificationExpired(Notification notification, long currentTime) {
        Date notificationDate = notification.getDate();
        Time notificationTime = notification.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(notificationDate);
        calendar.set(Calendar.HOUR_OF_DAY, notificationTime.getHours());
        calendar.set(Calendar.MINUTE, notificationTime.getMinutes());
        calendar.set(Calendar.SECOND, notificationTime.getSeconds());

        long notificationMillis = calendar.getTimeInMillis();

        return currentTime - notificationMillis > TimeUnit.SECONDS.toMillis(120);
    }
}
