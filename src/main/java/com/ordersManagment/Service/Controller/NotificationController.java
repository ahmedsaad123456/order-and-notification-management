package com.ordersManagment.Service.Controller;
import com.ordersManagment.Service.Model.Notification;
import com.ordersManagment.Service.Service.NotificationService;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.Queue;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@AllArgsConstructor
@RequestMapping(value = "/notification")

/**
 * NotificationController class
 *
 * used to get all notifications across the emails and SMS
 *
 * get help from NotificationService class
 */

public class NotificationController {

    private final NotificationService notificationService;



    //------------------------------------------------------------------------------------------------------------

    // get all sms notification
    @GetMapping(value = "/SMS")
    public Queue<Notification> getSMSNotification(){
        return NotificationService.getSMSNotifications();
    }


    //------------------------------------------------------------------------------------------------------------

    // get all emails notification
    @GetMapping (value = "/email")
    public Queue<Notification> getEmailNotification(){
        return notificationService.getEmailNotifications();
    }

}
