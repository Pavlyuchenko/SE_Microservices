from flask import Flask, request, Blueprint
from flasgger import Swagger
import requests
from models import Book

catalog_bp = Blueprint("catalog", __name__)
app = Flask(__name__)
swagger = Swagger(app)

INVENTORY_URL = "http://localhost:3000/inventory"

books = []
book_ids = []


@catalog_bp.route("/", methods=["GET"])
def home():
    return "Go to <a href='/apidocs'>/apidocs</a> to see the API documentation"


@catalog_bp.route("/books", methods=["POST"])
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
    # print the path parameters
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

    res = requests.post(INVENTORY_URL + "/create-book/" + str(new_book.id) + "/0")
    print(res.text)

    return new_book.toJson()


@catalog_bp.route("/books/<id>", methods=["GET"])
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


@catalog_bp.route("/books", methods=["GET"])
def get_books():
    """GET endpoint for getting all the books in the catalog
    Get all the books from the catalog
    ---
    responses:
      200:
        description: The list of books is returned
    """
    books_jSon = []
    for i in range(len(books)):
        books_jSon.append(books[i].toJson())
    return books_jSon


@catalog_bp.route("/books/<id>", methods=["PUT"])
def update_book(id):
    """PUT endpoint for updating the attributes of a book
    Update attributes of a book in the catalog
    ---
    parameters:
      - name: id
        in: path
        type: integer
        required: true
      - name: title
        in: query
        type: string
        required: false
      - name: author
        in: query
        type: string
        required: false
      - name: pub_year
        in: query
        type: integer
        required: false
    responses:
      200:
        description: The list of books is returned
    """
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


@catalog_bp.route("/books/<id>", methods=["DELETE"])
def delete_book(id):
    """DELETE endpoint for delting a book from the catalog
    Delete a book from the catalog
    ---
    parameters:
      - name: id
        in: path
        type: integer
        required: true
    responses:
      200:
        description: The list of books is returned
    """
    for i in range(len(books)):
        if books[i].id == int(id):
            books.remove(books[i])
            return "Book " + id + " was removed from the catalog"
    return "Book is not in catalog"


app.register_blueprint(catalog_bp, url_prefix="/catalog")

if __name__ == "__main__":
    app.run(host="127.0.0.1", port=3001)
