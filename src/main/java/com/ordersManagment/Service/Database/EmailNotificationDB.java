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
 * EmailNotificationDB class
 *
 * used to store the notifications that sent to emails
 * and to store the notified emails and the number of notification that sent to this email
 *
 */

public class EmailNotificationDB extends Database{
    private static final Queue<Notification> EmailNotification;

    private static final HashMap<String, Integer> notifiedEmails;

    static {
        EmailNotification = new LinkedList<>();

        notifiedEmails = new HashMap<>();

    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * add notification to the queue
     *
     * @param notification that will be added to the queue
     */
    public static void addNotification(Notification notification) {
        EmailNotification.add(notification);
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get all emails notification
     *
     * @return queue of email notification
     */
    public static Queue<Notification> getAllNotifications(){
        return EmailNotification;
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

        Notification frontNotification = EmailNotification.peek();

        // Check if there is a notification at the front of the queue
        if (frontNotification != null) {


            // Check if the notification is older than 120 seconds
            if (isNotificationExpired(frontNotification, currentTime)) {

                EmailNotification.poll();
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
     * add email as notified email and if this email is notified before
     * the counter will be increased by one
     *
     * @param email that be notified
     */
    public static void addEmail(String email){

        notifiedEmails.put(email, notifiedEmails.getOrDefault(email, 0) + 1);

    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * get all notified emails and the number of notification that has sent to it
     *
     * @return emails and the number of notification that has sent to it
     */

    public static HashMap<String , Integer>getAllNotifiedEmails(){
        return notifiedEmails;
    }


    //------------------------------------------------------------------------------------------------------------


    /**
     * get the most notified email
     *
     * @return  most notified email and null if the queue is empty
     */

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

    //------------------------------------------------------------------------------------------------------------

}
