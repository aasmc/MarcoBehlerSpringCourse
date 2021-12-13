# PdfInvoices educational project

This project is based on the course of Marco Behler The Confident Spring Professional.
https://www.marcobehler.com/courses/spring-professional

### Part 1. Java Webapps without Spring
This part is aimed at building a simple server endpoint that accepts POST and GET
requests. 

A user can post a pdf invoice on the server by providing a user_id and amount parameters to 
the URL:
```text
POST http://localhost:8080/invoices?user_id=freddieFox&amount=50
```

A user cat retrieve all invoices from the server by using the following GET endpoint
``` text
GET http://localhost:8080/invoices
```

The response will contain a JSON object of the following contents (example):
```json
[
    {
      "id": 1,
      "user_id": "freddieFox",
      "amount": 50,
      "pdf_url": "https://cdn.myfancypdfinvoices.com/invoice-freddieFox-1.pdf"
    },
    {
      "id": 2,
      "user_id": "americanAirlines",
      "amount": 1000,
      "pdf_url": "https://cdn.myfancypdfinvoices.com/invoice-americanAirlines-1.pdf"
    }
]
```