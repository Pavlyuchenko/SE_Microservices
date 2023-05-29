package com.se.inventory.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se.inventory.models.InventorySingleton;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Post Controller", description = "Handles all POST requests for the inventory")
public class InventoryPostController {
        @PostMapping(value = "/create-book/{id}/{quantity}", produces = "application/json")
        @Operation(summary = "Creates a new book with the given id of quantity 0.")
        @Parameter(name = "id", description = "The id of the book to create")
        @ApiResponse(responseCode = "200", description = "The book was successfully created", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "400", description = "The id is not a valid integer", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "409", description = "The book with given id already exists", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        public String createBook(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
                return InventorySingleton.getInstance().createBook(id, quantity);
        }
}
