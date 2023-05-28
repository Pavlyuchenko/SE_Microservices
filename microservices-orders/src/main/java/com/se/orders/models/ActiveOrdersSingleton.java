package com.se.orders.models;

import java.util.ArrayList;
import java.util.List;

public class ActiveOrdersSingleton {
    private static ActiveOrdersSingleton instance = null;

    private List<Order> orders;

    private ActiveOrdersSingleton() {
        orders = new ArrayList<Order>();
    }

    public static ActiveOrdersSingleton getInstance() {
        if (instance == null) {
            instance = new ActiveOrdersSingleton();
        }
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getOrdersJSON() {
        String result = "[";
        for (Order order : orders) {
            result += order.getJSON() + ",";
        }
        if (result.length() > 1) {
            result = result.substring(0, result.length() - 1);
        }
        result += "]";
        return result;
    }

    public String getOrderJSON(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order.getJSON();
            }
        }
        return null;
    }
}
