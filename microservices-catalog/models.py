import json


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
