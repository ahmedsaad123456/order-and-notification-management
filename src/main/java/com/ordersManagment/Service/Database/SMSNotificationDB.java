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

/**
 * SMSNotificationDB class
 *
 * used to store the notifications that sent to mobile numbers
 * and to store the notified mobile numbers and the number of notification that sent to this mobile number
 *
 */
public class SMSNotificationDB extends Database {
    private static final Queue<Notification> SMSNotification;

    private static final HashMap<String , Integer> notifiedMobileNumber;

    static {
        SMSNotification = new LinkedList<>();

        notifiedMobileNumber = new HashMap<>();
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * add notification to the queue
     *
     * @param notification that will be added to the queue
     */
    public static void addNotification(Notification notification) {
        SMSNotification.add(notification);
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get all mobile numbers notification
     *
     * @return queue of mobile numbers notification
     */
    public static Queue<Notification> getAllNotifications(){
        return SMSNotification;
    }



    //------------------------------------------------------------------------------------------------------------


    /**
     * method that execute every one second
     * and check if the front of the queue set in the queue for 120 second
     * if that true it will be removed form the queue
     *
     */
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



    //------------------------------------------------------------------------------------------------------------

    /**
     * check if the notification set in the queue for 120 second or not
     *
     * @param notification that we want to check it
     * @param currentTime the time
     * @return true if it set in the queue for 120 second otherwise return false
     *
     */

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



    //------------------------------------------------------------------------------------------------------------

    /**
     * add mobile Number as notified mobile Number and if this mobile Number is notified before
     * the counter will be increased by one
     *
     * @param mobileNumber that be notified
     */
    public static void addMobileNumber(String mobileNumber){
        notifiedMobileNumber.put(mobileNumber, notifiedMobileNumber.getOrDefault(mobileNumber, 0) + 1);
    }


    //------------------------------------------------------------------------------------------------------------

    /**
     * get all notified mobile numbers and the number of notification that has sent to it
     *
     * @return mobile numbers and the number of notification that has sent to it
     */
    public static HashMap<String , Integer>getAllNotifiedMobileNumber(){
        return notifiedMobileNumber;
    }



    //------------------------------------------------------------------------------------------------------------

    /**
     * get the most notified mobile number
     *
     * @return  most notified mobile number and null if the queue is empty
     */
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


    //------------------------------------------------------------------------------------------------------------

}
