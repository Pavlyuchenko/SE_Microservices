package com.se.orders.helpers;

import com.se.orders.models.Order;

import java.util.List;

import org.springframework.web.client.RestTemplate;

public class Helpers {
    public static boolean idExists(int id, List<Order> orders) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static String listToString(List<Integer> list) {
        String listString = "";
        for (int i = 0; i < list.size(); i++) {
            listString += list.get(i);
            if (i != list.size() - 1) {
                listString += ",";
            }
        }
        return listString;
    }

    public static String callUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}
