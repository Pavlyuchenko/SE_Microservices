# Book managment implemented using microservices architecture

## Architecture

### 1. Gateway (port 3000)

Provided by examiners, implemented in Python using Flask.

### 2. Book Catalog (port 3001)

Implemented in Python using Flask.

### 3. Book Inventory (port 3002)

Implemented in Java using Spring Boot.

#### Endpoints

GET /
GET /get-inventory
GET /get-book/{id}
GET /return-books?bookIds={bookIds}&quantities={quantities}
GET /check-order-availability?bookIds={bookIds}&quantities={quantities}

PUT /increase-book-quantity/{id}/{quantity}
PUT /decrease-book-quantity/{id}/{quantity}

POST /create-book/{id}/{quantity}

### 4. Book Orders (port 3003)

Implemented in Java using Spring Boot.

#### Endpoints

GET /
GET /orders
GET /order/{id}

POST /order?bookIds={bookIds}?quantities={quantities}

DELETE /order/{id}

## Example of usage

1.

## Note:

All services have dummy data in memory, so you don't need to run any database.
