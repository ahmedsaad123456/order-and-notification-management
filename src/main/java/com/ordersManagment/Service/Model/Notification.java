package com.ordersManagment.Service.Model;

import lombok.*;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.Delayed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Notification  {


    private Date date;

    private Time time;

    private String message;
}
