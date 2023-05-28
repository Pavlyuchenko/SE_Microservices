package com.se.orders.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.se.orders.models.ActiveOrdersSingleton;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Orders Get Controller", description = "Handles all GET requests for the orders")
public class OrdersGetController {

    @GetMapping("/")
    @Operation(summary = "Returns the introduction page for the Orders API.")
    public String index() {
        /* Multiline HTML return */
        return "<html>" + "<head>" + "<title>Orders API</title>" + "</head>"
                + "<body style='display:flex; flex-direction: column; align-items: center; text-align: center;'>"
                + "<h1>Orders API</h1>"
                + "<p>This is the second microservice, Book Orders, for the Software Engineering class at Maastircht University.<br />Made by Group 29.</p>"
                + "<a href='http://localhost:3003/swagger-ui/index.html#/' style='font-size: 24px;'>Use the Swagger UI to view the endpoints and test the API.</a>"
                + "</body>" + "</html>";
    }

    @GetMapping("/orders")
    @Operation(summary = "Returns all orders in JSON format.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public @ResponseBody String getOrders() {
        return ActiveOrdersSingleton.getInstance().getOrdersJSON();
    }

    @GetMapping("/order/{id}")
    @Operation(summary = "Returns the order with the given ID in JSON format.")
    @Parameter(name = "id", description = "The ID of the order to return", required = true)
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public @ResponseBody String getOrder(@PathVariable("id") int id) {
        String order = ActiveOrdersSingleton.getInstance().getOrderJSON(id);

        if (order == null) {
            return HttpStatus.NOT_FOUND.toString();
        }

        return order;
    }
}
