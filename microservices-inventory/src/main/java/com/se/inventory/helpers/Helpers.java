package com.se.inventory.helpers;

import java.util.ArrayList;

import com.se.inventory.models.BookTracker;
import com.se.inventory.models.InventorySingleton;

public class Helpers {
    public static BookTracker findBookByID(int id, ArrayList<BookTracker> books) {
        for (BookTracker book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static void resetInventory() {
        InventorySingleton.getInstance().getBooks().clear();
    }
}
