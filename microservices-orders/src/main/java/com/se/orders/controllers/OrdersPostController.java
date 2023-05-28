package com.se.orders.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.orders.helpers.Helpers;
import com.se.orders.models.Order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Orders Post Controller", description = "Handles creating orders.")
public class OrdersPostController {
    @PostMapping("/order")
    @Operation(summary = "Creates a new order with the given book IDs and quantities.")
    @Parameter(name = "bookIds", description = "The IDs of the books to order as comma separated array", required = true)
    @Parameter(name = "quantities", description = "The quantities of the books to order as comma separated array", required = true)
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "Invalid values or mismatched number of book IDs and quantities")
    public @ResponseBody String createOrder(@RequestParam(required = true, name = "bookIds") List<Integer> bookIds,
            @RequestParam(required = true, name = "quantities") List<Integer> quantities) {
        /* Check for invalid values */
        if (bookIds.size() != quantities.size()) {
            return HttpStatus.BAD_REQUEST.toString();
        }
        /* Check if any of the values are less than 0 */
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
