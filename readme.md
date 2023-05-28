# Book managment implemented using microservices architecture

## Architecture

### 1. Gateway (port 3000)

Provided by examiners, implemented in Python using Flask.

### 2. Book Catalog (port 3001)

Implemented in Python using Flask.

### 3. Book Inventory (port 3002)

Implemented in Java using Spring Boot.

#### Endpoints

GET / <br />
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

GET / <br />
GET /orders <br />
GET /order/{id} <br />
<br />
POST /order?bookIds={bookIds}?quantities={quantities} <br />
<br />
DELETE /order/{id} <br />

## Example of usage

1.

## Note:

All services have dummy data in memory, so you don't need to run any database.
