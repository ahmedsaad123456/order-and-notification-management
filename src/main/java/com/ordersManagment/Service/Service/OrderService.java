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

@Service
public class OrderService {
    AccountService accountService;

    public boolean checkSimpleOrderAvailability(ArrayList<Product> orderList) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (Product product : orderList) {
            for (Product availableProduct : availableProducts) {
                if (product.getSerialNumber().equals(availableProduct.getSerialNumber())) {
                    if (product.getAmount() > availableProduct.getAmount()) {
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
        SimpleOrder order = new SimpleOrder(orderList, CustomerDB.getCustomerByID(customerID), OrderDB.getNextID(), OrderStatus.Placed , CustomerDB.getCustomerByID(customerID).getAddress());
        OrderDB.addOrder(order);
        return order;
    }

    public Order addCompoundOrder(ArrayList<Order> orderList, int customerID) {
        ArrayList<Product> availableProducts = ProductDB.getProducts();
        for (int i = 0; i < orderList.size(); i++) {
            ArrayList<Product> currentOrder = orderList.get(i).getProducts();
            for (Product product : currentOrder) {
                for (Product availableProduct : availableProducts) {
                    if (product.getSerialNumber().equals(availableProduct.getSerialNumber())) {
                        ProductDB.reduceProductAmount(currentOrder.get(i).getSerialNumber(), currentOrder.get(i).getAmount());
                    }
                }
            }
        }
        CompoundOrder order = new CompoundOrder();
        for (Order value : orderList) {
            if (value.getCustomer().getID() == customerID) {
                order.setProducts(value.getProducts());
                order.setOrderID(OrderDB.getNextID());
                order.setStatus(OrderStatus.Placed);
                order.setCustomer(value.getCustomer());
            } else {
                value.setOrderID(OrderDB.getNextID());
                value.setStatus(OrderStatus.Placed);
                order.addOrder(value);
            }
        }
        OrderDB.addOrder(order);
        return order;
    }

    public float calcutateOrder(ArrayList<Product> orderList) {
        float orderSum = 0;
        for (Product product : orderList) {
            orderSum += (float) (product.getAmount() * product.getPrice());
        }
        return orderSum;
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
        CompoundOrder order = (CompoundOrder) OrderDB.getInstance(orderID);
        ArrayList<CompoundOrder> orderQueue = new ArrayList<>();
        orderQueue.add(order);
        while (!orderQueue.isEmpty()) {
            ArrayList<Order> orders = orderQueue.get(0).getOrders();
            for (Order value : orders) {
                orderQueue.add((CompoundOrder) value);
            }
            ArrayList<Product> orderProducts = orderQueue.get(0).getProducts();
            for (Product orderProduct : orderProducts) {
                ProductDB.increaseProductAmount(orderProduct.getSerialNumber(), orderProduct.getAmount());
            }
            accountService.addToAccount(orderQueue.get(0).getCustomer().getID(), calcutateOrder(orderProducts));
            orderQueue.removeFirst();
        }
        OrderDB.deleteOrder(orderID);
    }
}
