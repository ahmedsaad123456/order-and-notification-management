package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.stereotype.Service;

/**
 * NotificationSender interface
 *
 * used to send Notification
 * using decorator pattern
 *
 *
 */
@Service
public interface NotificationSender {
    void sendNotification(Notification notification);

}
