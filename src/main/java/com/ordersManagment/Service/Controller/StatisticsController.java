package com.ordersManagment.Service.Controller;


import com.ordersManagment.Service.Response.StatisticsResponse;
import com.ordersManagment.Service.Service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Getter
@Setter
@RequestMapping(value = "/stat")


/**
 * StatisticsController class
 *
 * used to get all notified email-address/phone-number and sent notification template.
 * and used to get :
 * The most notified email-address/phone-number.
 * The most sent notification template.
 *
 * get help from StatisticsService class
 */

public class StatisticsController {

    private StatisticsService statisticsService;



    //------------------------------------------------------------------------------------------------------------

    // get All Notified Emails And Most Notified Ones
    @GetMapping(value = "/emails")
    public StatisticsResponse getAllNotifiedEmailsAndMostNotifiedOnes(){
        return statisticsService.getAllNotifiedEmailsAndMostNotifiedOnes();
    }


    //------------------------------------------------------------------------------------------------------------


    // get All Notified Mobile number And Most Notified Ones
    @GetMapping (value = "/mobile-numbers")
    public StatisticsResponse getAllNotifiedMobileAndMostNotifiedOnes(){
        return statisticsService.getAllNotifiedMobileAndMostNotifiedOnes();
    }

    //------------------------------------------------------------------------------------------------------------


    // get All Sent notification Template And Most Sent Ones
    @GetMapping (value = "/templates")
    public StatisticsResponse getAllSentTemplateAndMostSentOnes(){
        return statisticsService.getAllSentTemplateAndMostSentOnes();
    }





}
