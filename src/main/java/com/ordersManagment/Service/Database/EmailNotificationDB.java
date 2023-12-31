package com.ordersManagment.Service.Database;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
@Configuration
@EnableScheduling
public class EmailNotificationDB extends Database{
    private static final Queue<Notification> EmailNotification;

    private static final HashMap<String, Integer> notifiedEmails;

    static {
        EmailNotification = new LinkedList<>();

        notifiedEmails = new HashMap<>();

        notifiedEmails.put("ahmed" , 3);

        notifiedEmails.put("ali" , 5);

        notifiedEmails.put("mhmd" , 4);

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

                EmailNotification.poll();
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

    public static void addEmail(String email){

        notifiedEmails.put(email, notifiedEmails.getOrDefault(email, 0) + 1);

    }

    public static HashMap<String , Integer>getAllNotifiedEmails(){
        return notifiedEmails;
    }


    public static String getMostNotifiedEmail(){
        if (notifiedEmails.isEmpty()) {
            return null;
        }

        Map.Entry<String, Integer> maxEntry = Collections.max(
                notifiedEmails.entrySet(),
                Map.Entry.comparingByValue()
        );

        return maxEntry.getKey();

    }
}
