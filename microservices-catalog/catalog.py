from flask import Flask, request
import json
from flasgger import Swagger

app = Flask(__name__)
swagger = Swagger(app)


class Book:
    def __init__(self, id, title, author, pub_year):
        self.id = int(id)
        self.title = title
        self.author = author
        self.pub_year = pub_year

    def __str__(self) -> str:
        return self.title

    def toJson(self):
        return json.dumps(self, default=lambda o: o.__dict__)


books = []
book_ids = []


@app.route("/books", methods=["POST"])
def add_book():
    """POST endpoint for adding a new book to the catalog
    Add a new book to the catalog
    ---
    parameters:
      - name: id
        in: query
        type: integer
        required: true
      - name: title
        in: query
        type: string
        required: true
      - name: author
        in: query
        type: string
        required: true
      - name: pub_year
        in: query
        type: integer
        required: true
    definitions:
        Book:
            type: object
            properties:
                id:
                    type: integer
                    description: The book's ID
                title:
                    type: string
                    description: The book's title
                author:
                    type: string
                    description: The book's author
                pub_year:
                    type: integer
                    description: The book's publication year
    responses:
      200:
        description: The created book is returned
    """
    new_book = Book(
        request.args["id"],
        request.args["title"],
        request.args["author"],
        request.args["pub_year"],
    )
    if book_ids.__contains__(new_book.id):
        return "ID has already been used"
    books.append(new_book)
    book_ids.append(new_book.id)
    print(len(books))
    return new_book.toJson()


@app.route("/books/<id>", methods=["GET"])
def get_book(id):
    """GET endpoint for retrieving a book from the catalog
    Retrieve a book from the catalog
    ---
    parameters:
      - name: id
        in: path
        type: integer
        required: true
    responses:
      200:
        description: Returns the book with the given ID
    """
    for i in range(len(books)):
        if books[i].id == int(id):
            return books[i].toJson()
    return "Book is not in catalog"


@app.route("/books", methods=["GET"])
def get_books():
    books_jSon = []
    for i in range(len(books)):
        books_jSon.append(books[i].toJson())
    return books_jSon


@app.route("/books/<id>", methods=["PUT"])
def update_book(id):
    params = request.args

    for i in range(len(books)):
        if books[i].id == int(id):
            params = request.args
            if "title" in params:
                books[i].title = params.get("title")
            if "author" in params:
                books[i].author = params.get("author")
            if "pub_year" in params:
                books[i].pub_year = params.get("pub_year")
            return books[i].toJson()
    return "Book is not in catalog"


@app.route("/books/<id>", methods=["DELETE"])
def delete_book(id):
    for i in range(len(books)):
        if books[i].id == int(id):
            books.remove(books[i])
            return "Book " + id + " was removed from the catalog"
    return "Book is not in catalog"


if __name__ == "__main__":
    app.run()
