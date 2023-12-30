package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Model.Notification;
import org.springframework.stereotype.Service;

@Service
public interface NotificationSender {
    void sendNotification(Notification notification);

}
