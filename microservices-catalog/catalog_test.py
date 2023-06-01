import unittest
from catalog import app, books, Book
import json

app.testing = True


class CatalogTest(unittest.TestCase):
    example_book_data1 = {
        "id": 1,
        "title": "Harry Potter",
        "author": "JK Rowling",
        "pub_year": "1997",
    }

    example_book_data2 = {
        "id": 2,
        "title": "Lord of the Rings",
        "author": "JRR Tolkien",
        "pub_year": "1954",
    }

    example_book_data3 = {
        "id": 1,
        "title": "Lord of the Rings",
        "author": "JRR Tolkien",
        "pub_year": "1954",
    }

    def test_add_book(self):
        books.clear()
        with app.test_client() as client:
            client.post("/books", query_string=self.example_book_data1)
            self.assertEqual(len(books), 1)
            self.assertEqual(books[0].title, "Harry Potter")
            self.assertEqual(books[0].author, "JK Rowling")
            self.assertEqual(books[0].pub_year, "1997")
            self.assertEqual(books[0].id, 1)
            response = client.post(
                "/books", query_string=self.example_book_data3).text
            self.assertTrue(response == "ID has already been used")

    def test_get_book(self):
        books.clear()
        with app.test_client() as client:
            book1 = Book(1, "Harry Potter", "JK Rowling", "1997")
            books.append(book1)
            result = json.loads(client.get("/books/1").text)
            self.assertEqual(book1.title, result["title"])
            self.assertEqual(book1.author, result["author"])
            self.assertEqual(book1.pub_year, result["pub_year"])
            self.assertEqual(book1.id, result["id"])
            response = client.get("/books/2").text
            self.assertTrue(response == "Book is not in catalog")

    def test_get_books(self):
        books.clear()
        with app.test_client() as client:
            book1 = Book(1, "Harry Potter", "JK Rowling", "1997")
            book2 = Book(2, "Lord of the Rings", "JRR Tolkien", "1954")
            books.append(book1)
            books.append(book2)
            result = []
            result.append(json.loads(client.get("/books").json[0]))
            result.append(json.loads(client.get("/books").json[1]))
            self.assertEqual(self.example_book_data1, result[0])
            self.assertEqual(self.example_book_data2, result[1])

    def test_update_book(self):
        books.clear()
        with app.test_client() as client:
            client.post("/books", query_string=self.example_book_data3)
            print(books[0])

    def test_delete_book(self):
        books.clear()
        with app.test_client() as client:
            book = Book(1, "Harry Potter", "JK Rowling", "1997")
            books.append(book)
            prev_len = len(books)
            response = client.delete("/books/1").text
            self.assertTrue(len(books) == prev_len - 1)
            self.assertTrue(response == "Book " + str(book.id) +
                            " was removed from the catalog")


if __name__ == "__main__":
    unittest.main()
