# Book managment implemented using microservices architecture

## Architecture

### 1. Gateway (port 3000)

Provided by examiners, implemented in Python using Flask.

### 2. Book Catalog (port 3001)

Implemented in Python using Flask.

#### Endpoints

Explained in http://localhost:3001/apidocs

### 3. Book Inventory (port 3002)

Implemented in Java using Spring Boot.

#### Endpoints

GET / -> Explanation <br />
GET /get-inventory <br />
GET /get-book/{id} <br />
GET /return-books?bookIds={bookIds}&quantities={quantities} <br />
GET /check-order-availability?bookIds={bookIds}&quantities={quantities} <br />
<br />
PUT /increase-book-quantity/{id}/{quantity} <br />
PUT /decrease-book-quantity/{id}/{quantity} <br />
<br />
POST /create-book/{id}/{quantity} <br />

### 4. Book Orders (port 3003)

Implemented in Java using Spring Boot.

#### Endpoints

GET / -> Explanation <br />
GET /orders <br />
GET /order/{id} <br />
<br />
POST /order?bookIds={bookIds}?quantities={quantities} <br />
<br />
DELETE /order/{id} <br />

## Example of usage

1. Run all microservices and gateway (Java microservices require Maven, Python microservices require Python and Flask)
2. Create a book in catalog - POST localhost:3000/catalog/books?id=420&title=Three Body Problem&author=Liu Cixin&pub_year=2012
3. Check that it is created in catalog - GET localhost:3000/catalog/books
4. Check that it is created in inventory - GET localhost:3000/inventory/get-inventory
5. Add some instances of the book to inventory - PUT localhost:3000/inventory/increase-book-quantity/420/10
6. Check that it is added to inventory - GET localhost:3000/inventory/get-inventory
7. Create an order - POST localhost:3000/orders/order?bookIds=420&quantities=5
8. Check that it is created in orders - GET localhost:3000/orders/orders

## Note:

All services have dummy data in memory, so you don't need to run any database.
