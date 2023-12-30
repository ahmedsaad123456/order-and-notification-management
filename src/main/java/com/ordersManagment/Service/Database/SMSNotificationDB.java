package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.sql.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

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
                Notification expiredNotification = SMSNotification.poll();
            }
        }
    }

    private boolean isNotificationExpired(Notification notification, long currentTime) {
        Date notificationDate = (Date) notification.getDate();
        Time notificationTime = notification.getTime();

        long notificationMillis = notificationDate.getTime() + notificationTime.getTime();
        return currentTime - notificationMillis > TimeUnit.SECONDS.toMillis(120);
    }
}
