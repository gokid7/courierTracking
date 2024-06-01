# Courier Location Tracking Application

This is a RESTful web application built with Java 17 that tracks the courierLocations of couriers and provides functionalities to log their entries within a 100-meter radius of specified Migros stores. It also calculates the total distance traveled by a courier.

## Features
- Log courier courierLocations.
- Detect and log entries within a 100-meter radius of Migros stores.
- Calculate the total distance traveled by a courier.
- Implemented Singleton,Observer and Builder design patterns.

## Prerequisites
- Java 17
- Maven
- MongoDB
- Docker
- Docker Compose (optional)

## Build on Docker commands(on Mac terminal)
- Clone the repository:
    ```sh
    git clone https://github.com/gokid7/courier-tracking.git
    cd courier-tracking
    ```
- sudo docker build -t tracking .
- sudo docker run -p 8080:8080 --name tracking-container tracking

## Running the Application on Local

1. Clone the repository:
    ```sh
    git clone https://github.com/gokid7/courier-tracking.git
    cd courier-tracking
    ```

2. Build the application using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

4. The application will start on `http://localhost:8080`.

## API Endpoints

- POST /api/couriers/location: Kurye konumunu loglamak için.
- GET /api/couriers/distance/{courierId}: Bir kuryenin toplam seyahat mesafesini sorgulamak için.
- POST /api/couriers/create: Yeni bir kurye oluşturmak için.
- PUT /api/couriers/{id}: Var olan bir kuryeyi güncellemek için.

### Create Courier
- **URL:** `/api/couriers/create`
  - **Method:** `POST`
      - **Request Body:**
          ```json
          {
            "name": "Courier Name",
            "totalDistance" : "0.0"
          }
          ```

### Log Location
- **URL:** `/api/couriers/location`
- **Method:** `POST`
  - **Request Body:**
      ```json
      {
      "courierId": "66561deabebbd34d358d6552",
      "timestamp": "2023-05-20T15:30:00",
      "latitude": 40.986106,
      "longitude": 29.1161293
      }
      ```

### Get Total Travel Distance
- **URL:** `/api/couriers/distance/{courierId}`
- **Method:** `GET`

## Testing

1. To run the tests, use the following command:
    ```sh
    mvn test
    ```

## Design Patterns Used

### Singleton Pattern
- Used in `MongoConfig` to ensure a single instance for Mongo client with nosql DB.
- Used in `StoreLoaderUtil` to ensure a single instance for stores initiate.

### Builder Pattern
- Used in `ResponseUtil` to builder design pattern.

### Observer Pattern
- Implemented implicitly by the logging mechanism where the `StoreObserver` acts as an observer to log entries when couriers courierLocations are updated.

