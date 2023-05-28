package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.se.inventory.controllers.InventoryPostController;
import com.se.inventory.controllers.InventoryPutController;
import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.InventorySingleton;

@SpringBootTest
class InventoryPutControllerDecreaseBookTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testDecreaseBookQuantityNonExistent() {
		Helpers.resetInventory();

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book that doesn't exist */
		String status = controller.decreaseBookQuantity(1, 1);

		assertEquals(HttpStatus.NOT_FOUND.toString(), status);
	}

	@Test
	void testDecreaseBookQuantityMoreThanInStock() {
		Helpers.resetInventory();

		InventoryPostController postController = new InventoryPostController();
		postController.createBook(1, 0);

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book more than is in stock */
		controller.increaseBookQuantity(1, 10);

		assertEquals(HttpStatus.CONFLICT.toString(), controller.decreaseBookQuantity(1, 11));
	}

	@Test
	void testDecreaseBookQuantity() {
		Helpers.resetInventory();

		InventoryPostController postController = new InventoryPostController();
		postController.createBook(1, 0);

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book more than is in stock */
		controller.increaseBookQuantity(1, 10);

		controller.decreaseBookQuantity(1, 10);

		assertEquals(Helpers.findBookByID(1, InventorySingleton.getInstance().getBooks()).getQuantity(), 0);
	}

	@Test
	void testCheckOrderAvailabilityNonExistent() {
		Helpers.resetInventory();

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book that doesn't exist */
		String status = controller.checkOrderAvailability(new ArrayList<Integer>(Arrays.asList(1)),
				new ArrayList<Integer>(Arrays.asList(1)));

		assertEquals(HttpStatus.NOT_FOUND.toString() + " ID 1", status);
	}

	@Test
	void testCheckOrderAvailabilityNotEnoughStock() {
		Helpers.resetInventory();

		InventoryPostController postController = new InventoryPostController();
		postController.createBook(1, 1);

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book that doesn't exist */
		String status = controller.checkOrderAvailability(new ArrayList<Integer>(Arrays.asList(1)),
				new ArrayList<Integer>(Arrays.asList(2)));

		assertEquals(HttpStatus.NOT_ACCEPTABLE.toString() + " ID 1 Quantity 1", status);
	}

	@Test
	void testCheckOrderAvailabilityNotMatching() {
		Helpers.resetInventory();

		InventoryPostController postController = new InventoryPostController();
		postController.createBook(1, 1);
		postController.createBook(2, 1);

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book that doesn't exist */
		String status = controller.checkOrderAvailability(new ArrayList<Integer>(Arrays.asList(1, 2)),
				new ArrayList<Integer>(Arrays.asList(1)));

		assertEquals(HttpStatus.BAD_REQUEST.toString(), status);
	}

	@Test
	void testCheckOrderAvailabilityValid() {
		Helpers.resetInventory();

		InventoryPostController postController = new InventoryPostController();
		postController.createBook(1, 1);
		postController.createBook(2, 11);
		postController.createBook(3, 2);
		postController.createBook(201, 5);

		InventoryPutController controller = new InventoryPutController();

		/* Try to decrease book that doesn't exist */
		String status = controller.checkOrderAvailability(new ArrayList<Integer>(Arrays.asList(1, 2, 201)),
				new ArrayList<Integer>(Arrays.asList(1, 10, 2)));

		assertEquals(HttpStatus.OK.toString(), status);

		assertEquals(0, Helpers.findBookByID(1, InventorySingleton.getInstance().getBooks()).getQuantity());
		assertEquals(1, Helpers.findBookByID(2, InventorySingleton.getInstance().getBooks()).getQuantity());
		assertEquals(2, Helpers.findBookByID(3, InventorySingleton.getInstance().getBooks()).getQuantity());
		assertEquals(3, Helpers.findBookByID(201, InventorySingleton.getInstance().getBooks()).getQuantity());
	}
}
