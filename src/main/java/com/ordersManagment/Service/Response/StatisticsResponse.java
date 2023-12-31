package com.ordersManagment.Service.Response;



import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class StatisticsResponse extends Response {

    // store the most one
    private HashMap<String , String> mostData;

    // store all data
    private HashMap<String , Integer> allData;




    //------------------------------------------------------------------------------------------------------------

    public StatisticsResponse(boolean b, String s, HashMap<String, String> m , HashMap<String ,Integer> a){
        super(b,s);
        this.mostData = m;
        this.allData = a;
    }


    //------------------------------------------------------------------------------------------------------------


    public StatisticsResponse(boolean b, String s, String errorMessage){
        super(b,s,errorMessage);
    }


}
