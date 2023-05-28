package com.se.inventory.models;

/* An object that tracks the number of books in the inventory.
Also a wrapper for a Book class in the other microservice to which it's connected by id */

public class BookTracker {
    private int id;
    private int quantity;

    public BookTracker(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public BookTracker() {
        this.id = 0;
        this.quantity = 0;
    }

    public String jsonify() {
        return "{\"id\": " + this.getId() + ", \"quantity\": " + this.getQuantity() + "}";
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        this.quantity = newQuantity;
    }

    public void updateQuantity(int change) {
        this.quantity += change;
    }
}
