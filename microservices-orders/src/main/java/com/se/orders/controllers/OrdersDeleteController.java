package com.se.orders.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.orders.helpers.Helpers;
import com.se.orders.models.ActiveOrdersSingleton;
import com.se.orders.models.Order;

@RestController
public class OrdersDeleteController {
    @DeleteMapping("/order/{id}")
    public @ResponseBody String deleteOrder(@PathVariable("id") int id) {
        /* remove order with id from ActiveOrdersSingleton.getInstance().getOrders() */
        Order removedOrder = null;
        List<Order> orders = ActiveOrdersSingleton.getInstance().getOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == id) {
                removedOrder = orders.get(i);
                orders.remove(i);
                break;
            }
        }
        if (removedOrder == null) {
            return HttpStatus.NOT_FOUND.toString();
        }

        /* Call the Inventory Microservice */
        String bookIdsString = Helpers.listToString(removedOrder.getBookIds());
        String quantitiesString = Helpers.listToString(removedOrder.getQuantities());

        String params = "?bookIds=" + bookIdsString + "&quantities="
                + quantitiesString;
        final String url = "http://localhost:3002/return-books" + params;

        return Helpers.callUrl(url);
    }
}
