package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Model.Customer;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Service.*;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    OrderStatusService orderStatusService;
    AccountService accountService;

    NotificationService notificationService;

    @PostMapping("/add-simple-order")
    public Response addSimpleOrder(@RequestBody ArrayList<Product> orderList, int customerID) {
        Response response = new Response();
        if (!orderService.checkSimpleOrderAvailability(orderList)) {
//            response.setStatus(false);
//            response.setMessage("Not added");
            return response;
        }
        if (!accountService.checkAccountBalance(customerID, orderService.calcutateOrder(orderList))) {
//            response.setStatus(false);
//            response.setMessage("Not added");
            return response;
        }
        orderService.addSimpleOrder(orderList, customerID);
        accountService.deductFromAccount(customerID, orderService.calcutateOrder(orderList));


//        Customer c = CustomerDB.getCustomerByID(customerID);
//        NotificationSender s = null;
//        if(c.getPreferredChannel().equals("All")){
//            s = new EmailNotificationSender();
//            s = new SMSNotificationSender(s);
//        } else if (c.getPreferredChannel().equals("Email")){
//            s = new SMSNotificationSender();
//
//        } else if(c.getPreferredChannel().equals("SMS")){
//            s = new EmailNotificationSender();
//        }
//
//        notificationService = new NotificationService(new OrderTemplate(c) , s);
//        notificationService.sendNotification();


//        response.setStatus(true);
//        response.setMessage("Added");
        return response;
    }

    @PostMapping("/add-compound-order")
    public Response addComplexOrder(@RequestBody ArrayList<Order> orderList, int customerID) {
        Response response = new Response();
        if (!orderService.checkCompoundOrderAvailability(orderList)) {
//            response.setStatus(false);
//            response.setMessage("Not added");
            return response;
        }
        for (int i = 0; i < orderList.size(); i++) {
            if (!accountService.checkAccountBalance(orderList.get(i).getCustomer().getID(), orderService.calcutateOrder(orderList.get(i).getProducts()))) {
//            response.setStatus(false);
//            response.setMessage("Not added");
                return response;
            }
        }

        orderService.addCompoundOrder(orderList, customerID);
        for (int i = 0; i < orderList.size(); i++) {
            accountService.deductFromAccount(orderList.get(i).getCustomer().getID(), orderService.calcutateOrder(orderList.get(i).getProducts()));
        }
//        response.setStatus(true);
//        response.setMessage("Added");
        return response;
    }

    @DeleteMapping("/delete-simple-order")
    public Response deleteSimpleOrder(@RequestHeader int orderID) {
        Response response = new Response();
        orderService.cancelSimpleOrder(orderID);
//        response.setStatus(true);
//        response.setMessage("Canceled");
        return response;
    }

    @DeleteMapping("/delete-compound-order")
    public Response deleteCompoundOrder(@RequestHeader int orderID) {
        Response response = new Response();
        orderService.cancelCompoundOrder(orderID);
//        response.setStatus(true);
//        response.setMessage("Canceled");
        return response;
    }
}
