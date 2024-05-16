Real-time Transaction Challenge
===============================
## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host. 

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.



# Candidate README

## Prerequisites

Make sure you have the following installed:
- Java Development Kit (JDK) version 17
- Maven
- Intellij IDEA (optional, but recommended)
- Postman for API and integration testing
- MySQL WorkBench (optional)


This service accepts the following requests:
1. Ping: A GET request to return the server run time.
2. Load: A PUT request which loads money to respective user records, if there is no user exists in the database, new record will be created for the user.
3. Authorization: A PUT request which conditionally debits amount from the user. A successful response is given, even when the user does not have enough balance, with a "DECLINED" response code.

## Bootstrap instructions
*To run this server locally, do the following:*
1. **Clone the repository:**
   ```sh
   git clone https://github.com/codescreen/CodeScreen_xpz8pzqh.git
   
2. **Navigate to the project directory in terminal:**
    ```sh
   cd codescreen/CodeScreen_xpz8pzqh

3. **Clean the project:**
    ```sh
   mvn clean

4. **Compile the project:**
    ```sh
   mvn compile
   
5. **Package the project:**
    ```sh
   mvn package

6. **Run the application:** (If you are using any IDE, you can simply run the Main.class file and test it locally.)
    ```sh
   java -jar target/CodeScreen_xpz8pzqh-1.0.0-jar-with-dependencies.jar

Once the server is started, http://localhost:4567/ping

(The above url returns local server time)

## Design considerations

- This project uses Spark to handle the requests listening on 0.0.0.0:4567.
- To store all the transactions for load and authorize, I have used MySQL database. It has two tables; users and messages. Users table has the amount and currency whereas the messages table record all the transactions from time to time.
- I have used Postman tool to run and test the services.
- All the functions and classes are successfully tested and documented in the test folder.
- The Integration tests are being performed using Postman and saved all the scenarios in a json collection which is located in the resources folder.


Deployment considerations

To deploy this application, we can manually upload the jar file to the cloud servers or we can use docker to create an image and build it.

1. **Build an image**
   ```sh
   docker build -t my-image .
2. **Run the build image file**
   ```sh
   docker run -p 4567:4567 my-image
