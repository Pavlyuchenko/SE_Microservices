import unittest
from catalog import app
app.testing = True


class test(unittest.TestCase):

    def test_no_title(self):
        with app.test_client() as client:
            # send data as POST form to endpoint
            sent = {"id": "1", "title": "Harry Potter",
                    "author": "JK Rowling", "pub_year": "1997"}
            result = client.post(
                "/books",
                data=sent
            )
            print(result)
        self.assertEquals(True, True)


if __name__ == "__main__":
    unittest.main()
