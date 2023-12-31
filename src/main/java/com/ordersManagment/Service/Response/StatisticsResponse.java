package com.ordersManagment.Service.Response;



import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class StatisticsResponse extends Response {

    private HashMap<String , String> MostData;
    private HashMap<String , Integer> AllData;





    public StatisticsResponse(boolean b, String s, HashMap<String, String> m , HashMap<String ,Integer> a){
        super(b,s);
        this.MostData = m;
        this.AllData = a;
    }


    //------------------------------------------------------------------------------------------------------------


    public StatisticsResponse(boolean b, String s, String errorMessage){
        super(b,s,errorMessage);
    }


}
