package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.se.inventory.controllers.InventoryGetController;
import com.se.inventory.helpers.Helpers;

@SpringBootTest
class InventoryGetControllerGetInventoryTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testGetInventory() {
        Helpers.resetInventory();

        InventoryGetController controller = new InventoryGetController();

        String inventory = controller.getInventory();
        assertEquals("[]", inventory);
    }
}
