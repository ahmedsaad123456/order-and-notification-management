package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.EmailNotificationDB;
import com.ordersManagment.Service.Database.MostNotificationTemplateDB;
import com.ordersManagment.Service.Database.SMSNotificationDB;
import com.ordersManagment.Service.Response.StatisticsResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StatisticsService {



    public StatisticsResponse getAllNotifiedEmailsAndMostNotifiedOnes(){
        String mostNotifiedEmail = EmailNotificationDB.getMostNotifiedEmail();
        if(mostNotifiedEmail!=null){
            HashMap<String , String> m = new HashMap<>();
            m.put("most notified email" , mostNotifiedEmail);
            System.out.println(mostNotifiedEmail);
            return new StatisticsResponse(true , "Notified Emails Statistics" , m , EmailNotificationDB.getAllNotifiedEmails());
        }

        return new StatisticsResponse(false , "Emails not found" , "There are no emails notified yet");

    }


    public StatisticsResponse getAllNotifiedMobileAndMostNotifiedOnes(){
        String mostNotifiedMobile = SMSNotificationDB.getMostNotifiedMobileNumber();
        if(mostNotifiedMobile!=null){
            HashMap<String , String> m = new HashMap<>();
            m.put("most notified mobile number" , mostNotifiedMobile);
            return new StatisticsResponse(true , "Notified mobile numbers Statistics" , m , SMSNotificationDB.getAllNotifiedMobileNumber());
        }

        return new StatisticsResponse(false , "mobile numbers not found" , "There are no mobile numbers notified yet");

    }


    public StatisticsResponse getAllSentTemplateAndMostSentOnes(){
        String mostSentTemplate = MostNotificationTemplateDB.getMostSentTemplate();
        if(mostSentTemplate!=null){
            HashMap<String , String> m = new HashMap<>();
            m.put("most sent template" , mostSentTemplate);
            return new StatisticsResponse(true , "Sent template Statistics" , m ,MostNotificationTemplateDB.getAllSentTemplates());
        }

        return new StatisticsResponse(false , "Templates not found" , "There are no template sent yet");

    }



}
