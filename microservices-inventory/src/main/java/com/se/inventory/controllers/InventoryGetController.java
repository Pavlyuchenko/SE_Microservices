package com.se.inventory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.inventory.models.InventorySingleton;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Get Controller", description = "Handles all GET requests for the inventory")
public class InventoryGetController {

    @GetMapping("/")
    @Operation(summary = "Returns the introduction page for the Inventory API.")
    public String index() {
        /* Multiline HTML return */
        return "<html>" + "<head>" + "<title>Inventory API</title>" + "</head>"
                + "<body style='display:flex; flex-direction: column; align-items: center; text-align: center;'>"
                + "<h1>Inventory API</h1>"
                + "<p>This is the second microservice, Book Inventory, for the Software Engineering class at Maastircht University.<br />Made by Group 29.</p>"
                + "<a href='http://localhost:3002/swagger-ui/index.html#/' style='font-size: 24px;'>Use the Swagger UI to view the endpoints and test the API.</a>"
                + "</body>" + "</html>";
    }

    @GetMapping("/get-inventory")
    @Operation(summary = "Gets all books in the inventory along with their quantities in JSON format.")
    @Parameter(name = "id", description = "The id of the book to get")
    @ApiResponse(responseCode = "200", description = "The inventory was successfully retrieved", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
    public String getInventory() {
        return InventorySingleton.getInstance().getBooksJSON();
    }

    @GetMapping(value = "/get-book/{id}", produces = "application/json")
    @Operation(summary = "Gets a book from the inventory by its id in JSON format.")
    @Parameter(name = "id", description = "The id of the book to get")
    @ApiResponse(responseCode = "200", description = "The book was successfully retrieved", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
    @ApiResponse(responseCode = "400", description = "The id is not a valid integer", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
    @ApiResponse(responseCode = "404", description = "The book was not found", content = {
            @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
    public String getBookJSON(@PathVariable("id") Integer id) {
        String bookData = InventorySingleton.getInstance().getBookJSON(id);

        if (bookData == null) {
            return HttpStatus.NOT_FOUND.toString();
        }

        return bookData;
    }

}
