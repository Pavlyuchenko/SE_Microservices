package com.se.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.se.orders.controllers.OrdersGetController;
import com.se.orders.models.ActiveOrdersSingleton;
import com.se.orders.models.Order;

public class GetTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetOrders() {
        ActiveOrdersSingleton aos = ActiveOrdersSingleton.getInstance();
        OrdersGetController ogc = new OrdersGetController();

        assertEquals("[]", ogc.getOrders());

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);

        new Order(ids, quantities);

        assertEquals(1, aos.getOrders().size());
        assertNotEquals("[]", ogc.getOrders());
    }

    @Test
    public void testGetOrder() {
        OrdersGetController ogc = new OrdersGetController();

        assertEquals(HttpStatus.NOT_FOUND.toString(), ogc.getOrder(1));

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);

        Order order = new Order(ids, quantities);

        assertNotEquals(HttpStatus.NOT_FOUND.toString(), ogc.getOrder(order.getId()));
    }
}