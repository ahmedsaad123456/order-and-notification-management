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

public class StatisticsController {

    private StatisticsService statisticsService;



    @GetMapping(value = "/emails")

    public StatisticsResponse getAllNotifiedEmailsAndMostNotifiedOnes(){
        return statisticsService.getAllNotifiedEmailsAndMostNotifiedOnes();
    }



    @GetMapping (value = "/mobiles-numbers")

    public StatisticsResponse getAllNotifiedMobileAndMostNotifiedOnes(){
        return statisticsService.getAllNotifiedMobileAndMostNotifiedOnes();
    }

    @GetMapping (value = "/templates")

    public StatisticsResponse getAllSentTemplateAndMostSentOnes(){
        return statisticsService.getAllSentTemplateAndMostSentOnes();
    }





}
