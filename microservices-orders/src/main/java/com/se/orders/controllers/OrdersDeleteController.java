package com.se.orders.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.orders.helpers.Helpers;
import com.se.orders.models.ActiveOrdersSingleton;
import com.se.orders.models.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders Delete Controller", description = "Handles deleting orders.")
public class OrdersDeleteController {
    @DeleteMapping("/order/{id}")
    @Operation(summary = "Deletes an order with the given ID.")
    @Parameter(name = "id", description = "The ID of the order to delete", required = true)
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Order with the given ID not found")
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
