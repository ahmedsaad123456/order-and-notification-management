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

public class NotificationController {

    private final NotificationService notificationService;


    @GetMapping(value = "/SMS")
    public Queue<Notification> getSMSNotification(){
        return NotificationService.getSMSNotifications();
    }

    @GetMapping (value = "/email")
    public Queue<Notification> getEmailNotification(){
        return notificationService.getEmailNotifications();
    }

}
