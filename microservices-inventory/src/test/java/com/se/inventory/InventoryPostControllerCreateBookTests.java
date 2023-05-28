package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.se.inventory.controllers.InventoryPostController;
import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.InventorySingleton;

@SpringBootTest
class InventoryPostControllerCreateBookTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testCreateBookSuccess() {
        Helpers.resetInventory();

        InventoryPostController controller = new InventoryPostController();

        String status = controller.createBook(1, 10);

        assertEquals(HttpStatus.CREATED.toString(), status);

        /* Check that there is only one book in the inventory */
        assertEquals(1, InventorySingleton.getInstance().getBooks().size());
        assertEquals(10, InventorySingleton.getInstance().getBook(1).getQuantity());

        /*
         * Check that creating a book with existing ID does not increase the stock size
         */
        controller.createBook(1, 10);

        assertEquals(1, InventorySingleton.getInstance().getBooks().size());
    }
}
