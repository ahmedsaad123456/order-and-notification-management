package com.ordersManagment.Service.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CompoundOrder extends Order{

    private ArrayList<Order> orders;


    public void addOrder(Order order){
        orders.add(order);
    }


    public void removeOrder(int orderID){
        for(int i=0 ; i < orders.size() ; i++){
            if(orders.get(i).getOrderID() == orderID){
                orders.remove(i);
                return;
            }
        }
    }







}
