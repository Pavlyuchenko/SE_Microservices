package com.se.inventory.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.BookTracker;
import com.se.inventory.models.InventorySingleton;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Inventory Delete Controller", description = "Handles all DELETE requests for the inventory")
public class InventoryDeleteController {

    @DeleteMapping("/delete-record/{id}")
    @Operation(summary = "Deletes a book from the inventory by its id.")
    @Parameter(name = "id", description = "The id of the book to delete")
    @ApiResponse(responseCode = "200", description = "The book was successfully deleted")
    @ApiResponse(responseCode = "404", description = "The book was not found")
    public String deleteRecord(@PathVariable int id) {
        InventorySingleton is = InventorySingleton.getInstance();

        BookTracker book = Helpers.findBookByID(id, is.getBooks());

        if (book == null) {
            return HttpStatus.NOT_FOUND.toString();
        }

        is.getBooks().remove(book);

        return HttpStatus.OK.toString();
    }

}
