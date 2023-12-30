package com.ordersManagment.Service.Controller;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Response.OrderResponse;
import com.ordersManagment.Service.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    AccountService accountService;

    @PostMapping("/add-simple-order")
    public OrderResponse addSimpleOrder(@RequestBody ArrayList<Product> orderList, int customerID) {

        if (!orderService.checkSimpleOrderAvailability(orderList)) {

            return new OrderResponse(false, "Order is Not added", "Not enough products");
        }
        if (!accountService.checkAccountBalance(customerID, orderService.calcutateOrder(orderList))) {

            return new OrderResponse(false, "Order is Not added", "Not enough balance");
        }

        Order order=orderService.addSimpleOrder(orderList, customerID);
        accountService.deductFromAccount(customerID, orderService.calcutateOrder(orderList));
        return new OrderResponse(true, "Order is added", order);
    }

    @PostMapping("/add-compound-order")
    public OrderResponse addComplexOrder(@RequestBody ArrayList<Order> orderList, int customerID) {
        if (!orderService.checkCompoundOrderAvailability(orderList)) {
            return new OrderResponse(false, null, "Order is Not added");
        }
        for (Order order : orderList) {
            if (!accountService.checkAccountBalance(order.getCustomer().getID(), orderService.calcutateOrder(order.getProducts()))) {
                return new OrderResponse(false, null, "Order is Not added");
            }
        }

        Order ord=orderService.addCompoundOrder(orderList, customerID);
        for (Order order : orderList) {
            accountService.deductFromAccount(order.getCustomer().getID(), orderService.calcutateOrder(order.getProducts()));
        }

        return new OrderResponse(true, "Order is added", ord);
    }

    @DeleteMapping("/delete-simple-order")
    public OrderResponse deleteSimpleOrder(@RequestHeader int orderID) {

        orderService.cancelSimpleOrder(orderID);
        return new OrderResponse(true, null,"Order is canceled");
    }

    @DeleteMapping("/delete-compound-order")
    public OrderResponse deleteCompoundOrder(@RequestHeader int orderID) {

        orderService.cancelCompoundOrder(orderID);
        return new OrderResponse(true, null,"Order is canceled");
    }
}
