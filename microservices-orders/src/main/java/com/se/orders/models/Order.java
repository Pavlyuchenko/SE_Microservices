package com.se.orders.models;

import java.util.Date;
import java.util.List;

import com.se.orders.helpers.Helpers;

public class Order {
    private int id;
    private List<Integer> bookIds;
    private List<Integer> quantities;
    private Date date;

    public Order(List<Integer> bookIds, List<Integer> quantities) {
        if (bookIds.size() != quantities.size()) {
            throw new IllegalArgumentException("bookIds and quantities must be the same size");
        }

        /* random ID between 100000 and 1000000 */
        this.id = (int) (Math.random() * 900000) + 100000;

        while (Helpers.idExists(this.id, ActiveOrdersSingleton.getInstance().getOrders())) {
            this.id = (int) (Math.random() * 900000) + 100000;
        }

        this.bookIds = bookIds;
        this.quantities = quantities;
        this.date = new Date();

        /* Automatically add this order to active orders */
        ActiveOrdersSingleton.getInstance().addOrder(this);
    }

    @Override
    public String toString() {
        String order = "Order #" + id + ": \n";
        for (int i = 0; i < bookIds.size(); i++) {
            order += "\t(Book ID: " + bookIds.get(i) + ", Quantity: " + quantities.get(i) + ")\n";
        }
        order += "\tDate: " + date;
        return order;
    }

    public String getJSON() {
        String result = "{";
        result += "\"id\":" + id + ",";
        result += "\"bookIds\":" + bookIds + ",";
        result += "\"quantities\":" + quantities + ",";
        result += "\"date\":\"" + date + "\"";
        result += "}";
        return result;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getBookIds() {
        return bookIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
}
