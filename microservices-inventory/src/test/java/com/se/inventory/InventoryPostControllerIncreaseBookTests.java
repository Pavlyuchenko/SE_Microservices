package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.se.inventory.controllers.InventoryPostController;
import com.se.inventory.controllers.InventoryPutController;
import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.InventorySingleton;

@SpringBootTest
class InventoryPostControllerIncreaseBookTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testIncreaseBookQuantityNotFound() {
        Helpers.resetInventory();

        InventoryPutController controller = new InventoryPutController();

        String status = controller.increaseBookQuantity(1, 1);

        /*
         * Check that the status is 404 when trying to increase a book that doesn't
         * exist
         */
        assertEquals(HttpStatus.NOT_FOUND.toString(), status);
    }

    @Test
    void testIncreaseBookQuantitySuccess() {
        Helpers.resetInventory();

        InventoryPostController postController = new InventoryPostController();
        postController.createBook(1, 0);

        InventoryPutController controller = new InventoryPutController();

        String status = controller.increaseBookQuantity(1, 10);

        /* Check that the status is 200 when increasing a book that exists */

        assertEquals(HttpStatus.OK.toString(), status);

        /* Check that the quantity is 10 */
        assertEquals(10, Helpers.findBookByID(1, InventorySingleton.getInstance().getBooks()).getQuantity());
    }
}
