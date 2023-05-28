package com.se.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.se.inventory.helpers.Helpers;
import com.se.inventory.models.BookTracker;
import com.se.inventory.models.InventorySingleton;

@SpringBootTest
class HelpersTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testFindBook() {
        ArrayList<BookTracker> books = new ArrayList<BookTracker>();
        books.add(new BookTracker(1, 0));
        books.add(new BookTracker(10, 0));
        books.add(new BookTracker(5, 0));
        books.add(new BookTracker(7, 0));

        assertEquals(null, Helpers.findBookByID(2, books));
        assertEquals(null, Helpers.findBookByID(-2, books));
        assertEquals(books.get(0), Helpers.findBookByID(1, books));
        assertEquals(books.get(1), Helpers.findBookByID(10, books));
        assertEquals(books.get(2), Helpers.findBookByID(5, books));
        assertEquals(books.get(3), Helpers.findBookByID(7, books));
    }

    void testResetInventory() {
        Helpers.resetInventory();
        assertEquals(0, InventorySingleton.getInstance().getBooks().size());
    }
}
