package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.se.inventory.controllers.InventoryGetController;
import com.se.inventory.helpers.Helpers;

@SpringBootTest
class InventoryGetControllerGetBookTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGetBookNotFound() {
        Helpers.resetInventory();

        InventoryGetController controller = new InventoryGetController();

        String status = controller.getBookJSON(1);

        assertEquals(HttpStatus.NOT_FOUND.toString(), status);
    }
}
