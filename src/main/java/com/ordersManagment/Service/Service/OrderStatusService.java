package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Enums.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusService {
    public boolean changeStatus(int orderID, OrderStatus orderStatus){
        OrderDB.getInstance(orderID).setOrderStatus(orderStatus);
        return true;
    }
}
