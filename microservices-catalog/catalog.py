from flask import Flask, request
import json

app = Flask(__name__)


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


@app.route("/books", methods=["POST"])
def add_book():
    new_book = Book(
        request.args["id"],
        request.args["title"],
        request.args["author"],
        request.args["pub_year"],
    )
    books.append(new_book)
    print(len(books))
    return new_book.toJson()


@app.route("/books/<id>", methods=["GET"])
def get_book(id):
    for i in range(len(books)):
        if books[i].id == int(id):
            return books[i].toJson()
    return "Book is not in catalog"


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
