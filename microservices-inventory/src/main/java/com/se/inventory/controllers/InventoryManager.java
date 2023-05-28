package com.se.inventory.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.BookTracker;
import com.se.inventory.models.InventorySingleton;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Inventory Manager", description = "Handles book additions and deletions")
public class InventoryManager {
        @PutMapping(value = "/increase-book-quantity/{id}/{quantity}", produces = "application/json")
        @Operation(summary = "Increases the quantity of the book in stock by the given quantity.")
        @Parameter(name = "id", description = "The id of the book to update")
        @Parameter(name = "quantity", description = "How many new books to add to the inventory")
        @ApiResponse(responseCode = "200", description = "The book was successfully updated", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "400", description = "The id or quantity is not a valid integer", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "404", description = "The book with given id doesn't exist", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        public String increaseBookQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
                return InventorySingleton.getInstance().updateBookQuantity(id, quantity);
        }

        /*
         * @param id: The id of the book to reduce
         * 
         * @param quantity: The amount to reduce the book by
         * 
         * @effects: Decreases the quantity of the book in stock by the given @quantity
         * 
         * @return:
         * - 200 if successful,
         * - 404 the book doesn't exist,
         * - 409 if the quantity is too large
         */
        @PutMapping(value = "/decrease-book-quantity/{id}/{quantity}", produces = "application/json")
        @Operation(summary = "Decreases the quantity of the book in stock by the given quantity.")
        @Parameter(name = "id", description = "The id of the book to update")
        @Parameter(name = "quantity", description = "How many books to remove from the inventory")
        @ApiResponse(responseCode = "200", description = "The book was successfully updated", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "400", description = "The id or quantity is not a valid integer", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "404", description = "The book with given id doesn't exist", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "409", description = "The quantity given is greater than the number of books remaining in the inventory", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        public String decreaseBookQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity) {
                BookTracker book = Helpers.findBookByID(id,
                                InventorySingleton.getInstance().getBooks());

                if (book == null) {
                        return HttpStatus.NOT_FOUND.toString();
                }

                if (book.getQuantity() < quantity) {
                        return HttpStatus.CONFLICT.toString();
                }
                book.updateQuantity(-quantity);

                /* If changing the line below, need to change Order's createOrder method too */
                return HttpStatus.OK.toString();
        }

        @GetMapping(value = "/check-order-availability", produces = "application/json")
        @Operation(summary = "Checks if the books in the order are available in the inventory.")
        @Parameter(name = "bookIds", description = "The ids of the books to check as a comma separated list")
        @Parameter(name = "quantities", description = "The quantities of the books to check as a comma separated list")
        @ApiResponse(responseCode = "200", description = "The books are available", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "400", description = "The bookIds and quantities are not the same size", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "404", description = "One of the books doesn't exist", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "406", description = "One of the books doesn't have enough quantity", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        public String checkOrderAvailability(@RequestParam(required = true, name = "bookIds") List<Integer> bookIds,
                        @RequestParam(required = true, name = "quantities") List<Integer> quantities) {
                /* bookIds and quantities must be the same size */
                if (bookIds.size() != quantities.size()) {
                        return HttpStatus.BAD_REQUEST.toString();
                }

                /* Check if the books exist and if there are enough of them */
                for (int i = 0; i < bookIds.size(); i++) {
                        int ID = bookIds.get(i);

                        BookTracker book = InventorySingleton.getInstance().getBook(ID);

                        if (book == null) {
                                return HttpStatus.NOT_FOUND.toString() + " ID " + ID;
                        } else if (book.getQuantity() < quantities.get(i)) {
                                return HttpStatus.NOT_ACCEPTABLE.toString() + " ID " + ID + " Quantity "
                                                + book.getQuantity();
                        }
                }

                /* Decrease the quantity of the books from the order */
                for (int i = 0; i < bookIds.size(); i++) {
                        int ID = bookIds.get(i);

                        BookTracker book = InventorySingleton.getInstance().getBook(ID);
                        book.updateQuantity(-quantities.get(i));
                }

                return HttpStatus.OK.toString();
        }

        @GetMapping(value = "/return-books", produces = "application/json")
        @Operation(summary = "Returns the books from the order to the inventory.")
        @Parameter(name = "bookIds", description = "The ids of the books to return as a comma separated list")
        @Parameter(name = "quantities", description = "The quantities of the books to return as a comma separated list")
        @ApiResponse(responseCode = "200", description = "The books were successfully returned", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "400", description = "The bookIds and quantities are not the same size", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        @ApiResponse(responseCode = "404", description = "One of the books doesn't exist", content = {
                        @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json") })
        public String returnBooks(@RequestParam(required = true, name = "bookIds") List<Integer> bookIds,
                        @RequestParam(required = true, name = "quantities") List<Integer> quantities) {
                /* bookIds and quantities must be the same size */
                if (bookIds.size() != quantities.size()) {
                        return HttpStatus.BAD_REQUEST.toString();
                }

                /* Check if the books exist */
                for (int i = 0; i < bookIds.size(); i++) {
                        int ID = bookIds.get(i);

                        BookTracker book = InventorySingleton.getInstance().getBook(ID);

                        if (book == null) {
                                return HttpStatus.NOT_FOUND.toString() + " ID " + ID;
                        }
                }

                /* Increase the quantity of the books from the order */
                for (int i = 0; i < bookIds.size(); i++) {
                        int ID = bookIds.get(i);

                        BookTracker book = InventorySingleton.getInstance().getBook(ID);
                        book.updateQuantity(quantities.get(i));
                }

                return HttpStatus.OK.toString();
        }
}
