package com.se.inventory.models;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

import com.se.inventory.helpers.Helpers;

public class InventorySingleton {
    private static InventorySingleton instance;

    private ArrayList<BookTracker> books = new ArrayList<BookTracker>();

    private InventorySingleton() {
    }

    /* The Singleton Design Pattern that maintains only one instance of Inventory */
    public static InventorySingleton getInstance() {
        if (instance == null) {
            instance = new InventorySingleton();
        }
        return instance;
    }

    public ArrayList<BookTracker> getBooks() {
        return books;
    }

    /* Calls jsonify() for each book in this.books */
    public String getBooksJSON() {
        String json = "";
        for (BookTracker book : books) {
            json += book.jsonify() + ", ";
        }
        json = "[" + json + "]";

        return json;
    }

    /* Returns the book with the given id as a JSON string */
    public String getBookJSON(int id) {
        BookTracker book = Helpers.findBookByID(id, this.books);
        if (book == null) {
            return null;
        }

        return book.jsonify();
    }

    /* Returns book object */
    public BookTracker getBook(int id) {
        BookTracker book = Helpers.findBookByID(id, this.books);
        if (book == null) {
            return null;
        }

        return book;
    }

    /*
     * Creates a new book with the given id of quantity 0.
     * Returns 201 if successful, 409 if a book with the given ID already exists.
     */
    public String createBook(int id, int quantity) {
        System.out.println("Creating book with id " + id + " and quantity " + quantity);
        if (Helpers.findBookByID(id, this.books) != null) {
            /* Already exists */
            return HttpStatus.CONFLICT.toString();
        }
        if (quantity < 0) {
            /* Invalid quantity */
            return HttpStatus.BAD_REQUEST.toString();
        }

        BookTracker book = new BookTracker(id, quantity);
        books.add(book);

        return HttpStatus.CREATED.toString();
    }

    /*
     * Updates the quantity of the book in stock by the given quantity.
     * Returns 200 if successful, 404 if the book doesn't exist.
     */
    public String updateBookQuantity(int id, int quantity) {
        BookTracker book = Helpers.findBookByID(id, this.books);
        if (book == null) {
            return HttpStatus.NOT_FOUND.toString();
        }

        book.updateQuantity(quantity);
        return HttpStatus.OK.toString();
    }

    public void removeBook(BookTracker book) {
        books.remove(book);
    }
}
