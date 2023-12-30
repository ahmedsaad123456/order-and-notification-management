package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Database.ProductDB;
import com.ordersManagment.Service.Enums.OrderStatus;
import com.ordersManagment.Service.Model.CompoundOrder;
import com.ordersManagment.Service.Model.Order;
import com.ordersManagment.Service.Model.Product;
import com.ordersManagment.Service.Model.SimpleOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Queue;

@Service
public class OrderService {
    AccountService accountService;

    public boolean checkSimpleOrderAvailability(ArrayList<Product> orderList) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (int i = 0; i < orderList.size(); i++) {
            for (int j = 0; j < availableProducts.size(); j++) {
                if (orderList.get(i).getSerialNumber().equals(availableProducts.get(j).getSerialNumber())) {
                    if (orderList.get(i).getAmount() > availableProducts.get(j).getAmount()) {
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }

    public boolean checkCompoundOrderAvailability(ArrayList<Order> orderList) {
        ArrayList<Product> requestedProducts = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            ArrayList<Product> currentOrder = orderList.get(i).getProducts();
            for (int j = 0; j < currentOrder.size(); j++) {
                boolean found = false;
                for (int k = 0; k < requestedProducts.size(); k++) {
                    if (currentOrder.get(j).getSerialNumber().equals(requestedProducts.get(k).getSerialNumber())) {
                        requestedProducts.get(k).setAmount(requestedProducts.get(k).getAmount() + currentOrder.get(j).getAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    requestedProducts.add(currentOrder.get(j));
                }
            }
        }
        return checkSimpleOrderAvailability(requestedProducts);
    }

    public void addSimpleOrder(ArrayList<Product> orderList, int customerID) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (int i = 0; i < orderList.size(); i++) {
            for (int j = 0; j < availableProducts.size(); j++) {
                if (orderList.get(i).getSerialNumber().equals(availableProducts.get(j).getSerialNumber())) {
                    ProductDB.reduceProductAmount(orderList.get(i).getSerialNumber(), orderList.get(i).getAmount());
                }
            }
        }
        SimpleOrder order = new SimpleOrder(orderList, CustomerDB.getCustomerByID(customerID), OrderDB.getNextID(), OrderStatus.Placed , CustomerDB.getCustomerByID(customerID).getAddress());
        OrderDB.addOrder(order);
    }

    public boolean addCompoundOrder(ArrayList<Order> orderList, int customerID) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (int i = 0; i < orderList.size(); i++) {
            ArrayList<Product> currentOrder = orderList.get(i).getProducts();
            for (int j = 0; j < currentOrder.size(); j++) {
                for (int k = 0; k < availableProducts.size(); k++) {
                    if (currentOrder.get(j).getSerialNumber().equals(availableProducts.get(k).getSerialNumber())) {
                        ProductDB.reduceProductAmount(currentOrder.get(i).getSerialNumber(), currentOrder.get(i).getAmount());
                    }
                }
            }
        }
        CompoundOrder order = new CompoundOrder();
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getCustomer().getID() == customerID) {
                order.setProducts(orderList.get(i).getProducts());
                order.setOrderID(OrderDB.getNextID());
                order.setStatus(OrderStatus.Placed);
                order.setCustomer(orderList.get(i).getCustomer());
            } else {
                Order newOrder = orderList.get(i);
                newOrder.setOrderID(OrderDB.getNextID());
                newOrder.setStatus(OrderStatus.Placed);
                order.addOrder(newOrder);
            }
        }
        OrderDB.addOrder(order);
        return true;
    }

    public float calcutateOrder(ArrayList<Product> orderList) {
        float orderSum = 0;
        for (int i = 0; i < orderList.size(); i++) {
            orderSum += (float) (orderList.get(i).getAmount() * orderList.get(i).getPrice());
        }
        return orderSum;
    }

    public void cancelSimpleOrder(int orderID) {
        SimpleOrder order = (SimpleOrder) OrderDB.getInstance(orderID);
        ArrayList<Product> orderProducts = order.getProducts();
        for (int i = 0; i < orderProducts.size(); i++) {
            ProductDB.increaseProductAmount(orderProducts.get(i).getSerialNumber(), orderProducts.get(i).getAmount());
        }
        accountService.addToAccount(order.getCustomer().getID(), calcutateOrder(orderProducts));
        OrderDB.deleteOrder(orderID);
    }

    public void cancelCompoundOrder(int orderID) {
        CompoundOrder order = (CompoundOrder) OrderDB.getInstance(orderID);
        ArrayList<CompoundOrder> orderQueue = new ArrayList<>();
        orderQueue.add(order);
        while (!orderQueue.isEmpty()) {
            ArrayList<Order> orders = orderQueue.get(0).getOrders();
            for (int i = 0; i < orders.size(); i++) {
                orderQueue.add((CompoundOrder) orders.get(i));
            }
            ArrayList<Product> orderProducts = orderQueue.get(0).getProducts();
            for (int i = 0; i < orderProducts.size(); i++) {
                ProductDB.increaseProductAmount(orderProducts.get(i).getSerialNumber(), orderProducts.get(i).getAmount());
            }
            accountService.addToAccount(orderQueue.get(0).getCustomer().getID(), calcutateOrder(orderProducts));
            orderQueue.removeFirst();
        }
        OrderDB.deleteOrder(orderID);
    }
}
