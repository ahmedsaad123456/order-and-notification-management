package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class EmailNotificationDB extends Database{
    private static Queue<Notification> EmailNotification;

    static {
        EmailNotification = new LinkedList<>();
    }

    public static void addNotification(Notification notification) {
        EmailNotification.add(notification);
    }

    public static Queue<Notification> getAllNotifications(){
        return EmailNotification;
    }

    @Scheduled(fixedRate = 1000)
    public void checkAndRemoveExpiredNotifications() {
        long currentTime = System.currentTimeMillis();

        Notification frontNotification = EmailNotification.peek();

        // Check if there is a notification at the front of the queue
        if (frontNotification != null) {

            // Check if the notification is older than 120 seconds
            if (isNotificationExpired(frontNotification, currentTime)) {
                Notification expiredNotification = EmailNotification.poll();
            }
        }
    }

    private boolean isNotificationExpired(Notification notification, long currentTime) {
        Date notificationDate = notification.getDate();
        Time notificationTime = notification.getTime();

        long notificationMillis = notificationDate.getTime() + notificationTime.getTime();
        return currentTime - notificationMillis > TimeUnit.SECONDS.toMillis(120);
    }
}
