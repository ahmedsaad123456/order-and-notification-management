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
public class SMSNotificationDB extends Database {
    private static final Queue<Notification> SMSNotification;

    private static final HashMap<String , Integer> notifiedMobileNumber;

    static {
        SMSNotification = new LinkedList<>();

        notifiedMobileNumber = new HashMap<>();
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


    public static void addMobileNumber(String mobileNumber){
        notifiedMobileNumber.put(mobileNumber, notifiedMobileNumber.getOrDefault(mobileNumber, 0) + 1);
    }

    public static HashMap<String , Integer>getAllNotifiedMobileNumber(){
        return notifiedMobileNumber;
    }


    public static String getMostNotifiedMobileNumber(){
        if (notifiedMobileNumber.isEmpty()) {
            return null;
        }

        Map.Entry<String, Integer> maxEntry = Collections.max(
                notifiedMobileNumber.entrySet(),
                Map.Entry.comparingByValue()
        );

        return maxEntry.getKey();

    }
}
