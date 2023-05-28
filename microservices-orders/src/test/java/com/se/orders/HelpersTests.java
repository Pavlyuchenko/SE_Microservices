package com.se.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.se.orders.helpers.Helpers;
import com.se.orders.models.Order;

public class HelpersTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void idExistsTest() {
        assertEquals(false, Helpers.idExists(1, new ArrayList<>()));

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);

        ArrayList<Order> orders = new ArrayList<>();
        Order order = new Order(ids, quantities);
        orders.add(order);

        assertEquals(true, Helpers.idExists(order.getId(), orders));
    }
}
