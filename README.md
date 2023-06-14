# Zara Back end - Similar Product Service

Spring Boot Reactive Rest API Service for getting similar products from a given one

## Trello dashboard

https://trello.com/b/StFhzaOa/similar-product-service

## Installation

To install this project:

Clone the repo

```bash
  git clone https://github.com/Guuri11/similar-product-service
```

Go the repo folder

```
cd similar-product-service
```

Generate the jar file

```
  ./mvnw clean package
```

Make sure that you have docker install

```
docker --version
```

You will find out that you also have product-mock-service, which is the service where we communicate
to get the similar products to send it to the client. In the root you have docker files:

- Dockerfile which contains the instructions to build the spring boot service
- docker-compose.yaml which contains the instructions to build all the infrastructure

Let's build it

```
docker compose up -d 
```

Access to http://localhost:5001/swagger-ui/webjars/swagger-ui/index.html to see Swagger Docs. There
you will find the
product controller ready to try

A curl example from the endpoint

````
curl -X 'GET' \
  'http://localhost:5001/api/v1/products/1/similar' \
  -H 'accept: application/json'
````

And it's response (order could be different due to non-blocking requests):

```
[
  {
    "id": "2",
    "name": "Dress",
    "price": 19.99,
    "availability": true
  },
  {
    "id": "4",
    "name": "Boots",
    "price": 39.99,
    "availability": true
  },
  {
    "id": "3",
    "name": "Blazer",
    "price": 29.99,
    "availability": false
  }
]
```

