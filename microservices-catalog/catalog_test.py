import unittest
from catalog import app, books

app.testing = True


class CatalogTest(unittest.TestCase):
    example_book_data = {
        "id": 1,
        "title": "Harry Potter",
        "author": "JK Rowling",
        "pub_year": "1997",
    }

    def test_no_title(self):
        with app.test_client() as client:
            # call the endpoint (query_string is the data that is sent as request.args)
            client.post("/books", query_string=self.example_book_data)

            # Example of usage
            self.assertEqual(len(books), 1)
            self.assertEqual(books[0].title, "Harry Potter")


if __name__ == "__main__":
    unittest.main()
