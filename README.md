# currency-converter

This application converts money amounts of some source currency to the target currency using external converters:
https://api.exchangeratesapi.io/latest and https://api.exchangerate-api.com/v4/latest.
An aaplication randomly chooses convertion provider and send a request to that provider. If any problem occures withh first provider - the request to the anothher provider is sent.

## Starting an application

In your favorite IDE find and run class com.example.converterservice.ConverterServiceApplication.
By default application runs on port 8080.

## API

An application supports following end-point

POST http://localhost:8080/currency/convert
Content-Type: application/json

Request Body:
{code}
{
  "from": string,
  "to": string,
  "amount": number
}
{code}

Response Body:
{code}
{
  "from": string,
  "to": string,
  "amount": number,
  "converted": number
}
{code}

## Testing

The project contains file rest-api.http in the root folder. Use it if you use Intellij Idea for testing - just open and run thhe file. Make sure that application is up and running.
