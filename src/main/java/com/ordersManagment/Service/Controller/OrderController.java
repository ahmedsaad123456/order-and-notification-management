package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Service.AccountService;
import com.ordersManagment.Service.Service.OrderService;
import com.ordersManagment.Service.Service.OrderStatusService;
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
//        response.setStatus(true);
//        response.setMessage("Added");
        return response;
    }

    @PostMapping("/add-complex-order")
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

    @DeleteMapping("/delete-order")
    public Response deleteOrder(@RequestHeader int OrderID) {
        Response response = new Response();

        return response;
    }
}
