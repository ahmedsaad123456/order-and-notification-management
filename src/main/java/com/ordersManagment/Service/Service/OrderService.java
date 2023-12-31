package com.ordersManagment.Service.Service;

import com.ordersManagment.Service.Database.CustomerDB;
import com.ordersManagment.Service.Database.OrderDB;
import com.ordersManagment.Service.Database.ProductDB;
import com.ordersManagment.Service.Enums.OrderStatus;
import com.ordersManagment.Service.Model.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

@Service
public class OrderService {
    AccountService accountService = new AccountService();

    public boolean checkSimpleOrderAvailability(ArrayList<Product> orderList) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (Product product : orderList) {
            boolean flag = false;
            for (Product availableProduct : availableProducts) {
                if (product.getSerialNumber().equals(availableProduct.getSerialNumber())) {
                    if (product.getAmount() > availableProduct.getAmount()) {
                        return false;
                    }
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return false;
        }
        return true;
    }

    public boolean checkCompoundOrderAvailability(ArrayList<Order> orderList) {
        ArrayList<Product> requestedProducts = new ArrayList<>();
        for (Order order : orderList) {
            ArrayList<Product> currentOrder = order.getProducts();
            for (Product product : currentOrder) {
                boolean found = false;
                for (Product requestedProduct : requestedProducts) {
                    if (product.getSerialNumber().equals(requestedProduct.getSerialNumber())) {
                        requestedProduct.setAmount(requestedProduct.getAmount() + product.getAmount());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    requestedProducts.add(product);
                }
            }
        }
        return checkSimpleOrderAvailability(requestedProducts);
    }

    public Order addSimpleOrder(ArrayList<Product> orderList, int customerID) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (Product product : orderList) {
            for (Product availableProduct : availableProducts) {
                if (product.getSerialNumber().equals(availableProduct.getSerialNumber())) {
                    ProductDB.reduceProductAmount(product.getSerialNumber(), product.getAmount());
                }
            }
        }
        assert CustomerDB.getCustomerByID(customerID) != null;
        SimpleOrder order = new SimpleOrder(orderList, CustomerDB.getCustomerByID(customerID), OrderDB.getNextID(), OrderStatus.Placed, new Time(new Date().getTime()));
        OrderDB.addOrder(order);
        Customer c = order.getCustomer();
        NotificationService notificationService = new NotificationService(new OrderTemplate(order));
        notificationService.chooseChannelAndSend(c);


        return order;
    }

    public Order addCompoundOrder(ArrayList<Order> orderList, int customerID) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (int i = 0; i < orderList.size(); i++) {
            orderList.get(i).setCustomer(CustomerDB.getCustomerByID(orderList.get(i).getCustomer().getID()));
            ArrayList<Product> currentOrder = orderList.get(i).getProducts();
            for (Product product : currentOrder) {
                for (Product availableProduct : availableProducts) {
                    if (product.getSerialNumber().equals(availableProduct.getSerialNumber())) {
                        ProductDB.reduceProductAmount(product.getSerialNumber(), product.getAmount());
                    }
                }
            }
        }
        Time time = new Time(new Date().getTime());
        CompoundOrder order = new CompoundOrder();
        for (Order value : orderList) {
            if (value.getCustomer().getID() == customerID) {
                order.setProducts(value.getProducts());
                order.setOrderID(OrderDB.getNextID());
                order.setStatus(OrderStatus.Placed);
                order.setCustomer(value.getCustomer());
                order.setOrderTime(time);
            } else {
                value.setOrderID(OrderDB.getNextID());
                value.setStatus(OrderStatus.Placed);
                value.setOrderTime(time);
                order.addOrder(value);
            }
        }
        OrderDB.addOrder(order);
        for (Order value : orderList) {
            Customer c = value.getCustomer();
            NotificationService notificationService = new NotificationService(new OrderTemplate(value));
            notificationService.chooseChannelAndSend(c);
        }
        return order;
    }

    public float calcutateOrder(ArrayList<Product> orderList) {
        float orderSum = 0;
        for (Product product : orderList) {
            orderSum += (float) (product.getAmount() * ProductDB.getProductBySerialNumber(product.getSerialNumber()).getPrice());
        }
        return orderSum;
    }

    public boolean checkAddress(ArrayList<Order> orderList) {
        String[] address = CustomerDB.getCustomerByID(orderList.get(0).getCustomer().getID()).getAddress().split("/");
        for (Order value : orderList) {
            String[] newAddress = CustomerDB.getCustomerByID(value.getCustomer().getID()).getAddress().split("/");
            if (!address[0].equals(newAddress[0]) || !address[1].equals(newAddress[1])) {
                return false;
            }
        }
        return true;
    }

    public void cancelSimpleOrder(int orderID) {
        SimpleOrder order = (SimpleOrder) OrderDB.getInstance(orderID);
        assert order != null;
        ArrayList<Product> orderProducts = order.getProducts();
        for (Product orderProduct : orderProducts) {
            ProductDB.increaseProductAmount(orderProduct.getSerialNumber(), orderProduct.getAmount());
        }
        accountService.addToAccount(order.getCustomer().getID(), calcutateOrder(orderProducts));
        OrderDB.deleteOrder(orderID);
    }

    public void cancelCompoundOrder(int orderID) {
        Order order = OrderDB.getInstance(orderID);
        ArrayList<Order> orderQueue = new ArrayList<>();
        orderQueue.add(order);
        while (!orderQueue.isEmpty()) {
            if(orderQueue.get(0) == null){
                orderQueue.remove(0);
                continue;
            }
            ArrayList<Order> orders = ((CompoundOrder)orderQueue.get(0)).getOrders();
            for (Order value : orders) {
                orderQueue.add(value);
            }
            ArrayList<Product> orderProducts = orderQueue.get(0).getProducts();
            for (Product orderProduct : orderProducts) {
                ProductDB.increaseProductAmount(orderProduct.getSerialNumber(), orderProduct.getAmount());
            }
            accountService.addToAccount(orderQueue.get(0).getCustomer().getID(), calcutateOrder(orderProducts));
            orderQueue.remove(0);
        }
        OrderDB.deleteOrder(orderID);
    }

    public ArrayList<Order> getOrders() {
        return OrderDB.getOrders();
    }

    public boolean checkOrderTime(int orderID) {
        Time currentTime = new Time(new Date().getTime()), orderTime = OrderDB.getInstance(orderID).getOrderTime();
        long differenceInMillis = currentTime.getTime() - orderTime.getTime();
        long differenceInMinutes = differenceInMillis / (60 * 1000);
        return differenceInMinutes < 3;
    }

    public OrderStatus checkOrderStatus(int orderID) {
        return OrderDB.getInstance(orderID).getStatus();
    }
}
