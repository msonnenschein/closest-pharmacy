# closest-pharmacy

Code challenge response for RxSS - candidate: Morgan Sonnenschein

## Setup
Clone the project: `git clone https://github.com/msonnenschein/closest-pharmacy.git`.

Be sure you have a Java 11+ JDK installed.

## Starting the server
There are two ways to run the project once you clone it:

1. Run `mvnw clean spring-boot:run` (on Windows) or `./mvnw clean spring-boot:run` (on Linux). This will start running a server at `localhost:8080` by default.
2. Run `java -jar closest-pharmacy-0.0.1-SNAPSHOT.jar`. This will also start running a server at `localhost:8080` by default.

## How to use
The REST endpoint is `/latitude/{latitude}/longitude/{longitude}/closest-pharmacy`, so to run it you can navigate to 
`http://localhost:8080/latitude/{latitude input}/longitude/{longitude input}/closest-pharmacy` in a browser or 
make an HTTP GET request with Postman (those are the two ways I tested).

Example:
`http://localhost:8080/latitude/38.884/longitude/-96.24/closest-pharmacy`

The `latitude` and `longitude` path parameters must both be numbers.