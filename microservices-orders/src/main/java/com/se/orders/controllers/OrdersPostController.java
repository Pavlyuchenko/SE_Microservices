package com.se.orders.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.orders.helpers.Helpers;
import com.se.orders.models.Order;

@RestController
public class OrdersPostController {
    @PostMapping("/order")
    public @ResponseBody String createOrder(@RequestParam(required = true, name = "bookIds") List<Integer> bookIds,
            @RequestParam(required = true, name = "quantities") List<Integer> quantities) {
        /* Check for invalid values */
        if (bookIds.size() != quantities.size()) {
            return HttpStatus.BAD_REQUEST.toString();
        }
        for (int i = 0; i < bookIds.size(); i++) {
            if (bookIds.get(i) < 0 || quantities.get(i) <= 0) {
                return HttpStatus.BAD_REQUEST.toString();
            }
        }

        String bookIdsString = Helpers.listToString(bookIds);
        String quantitiesString = Helpers.listToString(quantities);

        /* Call the Inventory Microservice */
        String params = "?bookIds=" + bookIdsString + "&quantities=" + quantitiesString;
        final String url = "http://localhost:3002/check-order-availability" + params;

        String result = Helpers.callUrl(url);

        /* If the Inventory Microservice returns OK, create the order */
        if (result.equals(HttpStatus.OK.toString())) {
            new Order(bookIds, quantities);
        }

        return result;
    }
}
