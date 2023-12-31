package com.ordersManagment.Service.Controller;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Enums.OrderStatus;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Response.OrderResponse;
import com.ordersManagment.Service.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@RestController
@RequestMapping("/order")

public class OrderController {
    @Autowired
    OrderService orderService;
    AccountService accountService;

    @PostMapping("/add-simple-order")
    public OrderResponse addSimpleOrder(@RequestBody ArrayList<Product> orderList, @RequestParam int customerID) {
        if (CustomerDB.getCustomerByID(customerID) == null) {
            return new OrderResponse(false, "Order is Not added", "Invalid customer ID");
        }
        if (!orderService.checkSimpleOrderAvailability(orderList)) {
            return new OrderResponse(false, "Order is Not added", "Not enough products");
        }
        if (!accountService.checkAccountBalance(customerID, orderService.calcutateOrder(orderList))) {

            return new OrderResponse(false, "Order is Not added", "Not enough balance");
        }

        Order order = orderService.addSimpleOrder(orderList, customerID);
        accountService.deductFromAccount(customerID, orderService.calcutateOrder(orderList));
        return new OrderResponse(true, "Order is added", order);
    }

    @PostMapping("/add-compound-order")
    public OrderResponse addComplexOrder(@RequestBody ArrayList<Order> orderList, @RequestParam int customerID) {
        if (CustomerDB.getCustomerByID(customerID) == null) {
            return new OrderResponse(false, "Order is Not added", "Invalid customer ID");
        }
        if (!orderService.checkCompoundOrderAvailability(orderList)) {
            return new OrderResponse(false, "Order is Not added", "Not enough products");
        }
        for (Order order : orderList) {
            if (!accountService.checkAccountBalance(order.getCustomer().getID(), orderService.calcutateOrder(order.getProducts()))) {
                return new OrderResponse(false, "Order is Not added", "Not enough balance");
            }
        }
        if(!orderService.checkAddress(orderList)){
            return new OrderResponse(false, "Order is Not added", "Address is far from each other");
        }
        Order ord = orderService.addCompoundOrder(orderList, customerID);
        for (Order order : orderList) {
            accountService.deductFromAccount(order.getCustomer().getID(), orderService.calcutateOrder(order.getProducts()));
        }

        return new OrderResponse(true, "Order is added", ord);
    }

    @DeleteMapping("/delete-simple-order")
    public OrderResponse deleteSimpleOrder(@RequestParam int orderID) {
        if(OrderDB.getInstance(orderID) == null){
            return new OrderResponse(false, "Order is not canceled", "Order ID is incorrect");
        }
        if(orderService.checkOrderStatus(orderID) != OrderStatus.Placed){
            return new OrderResponse(false, "Order is not canceled", "Order has been shipped or pending shipment (please cancel shipment first)");
        }
        if(!orderService.checkOrderTime(orderID)){
            return new OrderResponse(false, "Order is not canceled", "Time is too late");
        }
        orderService.cancelSimpleOrder(orderID);
        return new OrderResponse(true, null, "Order is canceled");
    }

    @DeleteMapping("/delete-compound-order")
    public OrderResponse deleteCompoundOrder(@RequestParam int orderID) {
        if(OrderDB.getInstance(orderID) == null){
            return new OrderResponse(false, "Order is not canceled", "Order ID is incorrect");
        }
        if(orderService.checkOrderStatus(orderID) != OrderStatus.Placed){
            return new OrderResponse(false, "Order is not canceled", "Order has been shipped or pending shipment (please cancel shipment first)");
        }
        if(!orderService.checkOrderTime(orderID)){
            return new OrderResponse(false, "Order is not canceled", "Time is too late");
        }
        orderService.cancelCompoundOrder(orderID);
        return new OrderResponse(true, null, "Order is canceled");
    }

    @GetMapping("/get-orders")
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }
}
