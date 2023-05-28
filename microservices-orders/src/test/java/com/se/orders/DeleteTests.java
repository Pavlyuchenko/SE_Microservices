package com.se.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.se.orders.controllers.OrdersDeleteController;
import com.se.orders.models.ActiveOrdersSingleton;
import com.se.orders.models.Order;

public class DeleteTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void testDeleteOrder() {
        OrdersDeleteController odc = new OrdersDeleteController();

        assertEquals(HttpStatus.NOT_FOUND.toString(), odc.deleteOrder(1));

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ArrayList<Integer> quantities = new ArrayList<>();
        quantities.add(1);
        quantities.add(2);

        Order order = new Order(ids, quantities);

        assertEquals(1, ActiveOrdersSingleton.getInstance().getOrders().size());

        assertNotEquals(HttpStatus.OK.toString(), odc.deleteOrder(order.getId()));

        assertEquals(0, ActiveOrdersSingleton.getInstance().getOrders().size());
    }
}