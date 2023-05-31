import json
import time
import unittest
from catalog import app

app.testing = True


class test(unittest.TestCase):
    def test_no_title(self):
        with app.test_client() as client:
            # send data as request.args to endpoint
            book1_data = {
                "id": 1,
                "title": "Harry Potter",
                "author": "JK Rowling",
                "pub_year": "1997",
            }
            book2_data = {
                "id": 2,
                "title": "The Lord of the Rings",
                "author": "JRR Tolkien",
                "pub_year": "1954",
            }
            # call the endpoint (query_string is the data that is sent as request.args)
            res1 = client.post("/books", query_string=book1_data)
            res2 = client.post("/books", query_string=book2_data)

            # check if result is 200 == the call to the method was successful
            self.assertEqual(res1.status_code, 200)
            self.assertEqual(res2.status_code, 200)

            # Get response from the GET books endpoint
            book1_api = client.get("/books/1").text
            book2_api = client.get("/books/2").text
            # Converts data from string to dict
            book1_api = json.loads(book1_api)
            book2_api = json.loads(book2_api)

            # Prints for explanation
            print("Data inside GET /books/1:")
            print(book1_api)
            print("Data inside GET /books/2:")
            print(book2_api)

            # Example of usage
            self.assertEqual(book1_api["title"], book1_data["title"])


if __name__ == "__main__":
    unittest.main()
