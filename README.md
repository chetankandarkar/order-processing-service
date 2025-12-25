# order-processing-service
Order Processing Service

Setup & Run Instructions

## Prerequisites

* Java JDK 17 installed and configured
* Maven 3.8 or higher
* ActiveMQ Classic (5.x)
* Git
* Postman
* IDE (Eclipse / IntelliJ – optional)

---
## Clone the Repository

* Clone the Git repository to your local system
* Navigate to the project root directory

---
## Database Setup (H2 – File Based)

* Application uses **H2 file-based database**
* No manual DB creation required
* Database file is created automatically on application startup
* H2 Console is enabled for verification
* Tables are auto-created using JPA (`ddl-auto=update`)

---
## ActiveMQ Setup

* Install and start **ActiveMQ Classic**
* Ensure ActiveMQ is running on default ports
* Access ActiveMQ Admin Console via browser
* Verify broker is running before starting the application
* Application publishes messages to a configured queue after order creation

---
## File System Setup (Important)

* Create the following directories manually on your system:

  * `D:/ORDER_PROCESSING/input/orders`
  * `D:/ORDER_PROCESSING/error/orders`
* Camel monitors the input directory for incoming order files
* Failed files are moved automatically to the error directory

---
## Build the Application

* Run Maven clean and build command
* Ensure build completes successfully without dependency errors

---
## Run the Application

* Start the application using Maven or JAR
* Verify application starts without context or placeholder errors
* Confirm embedded Tomcat starts on the configured port
* Ensure Camel routes are initialized successfully

---
## API Testing (Postman)

* Use Postman to test REST APIs
* Send a **Create Order** request via HTTP POST
* Verify:

  * Order is saved in the database
  * Message is published to ActiveMQ queue
  * Order JSON file is created in input directory

---
## File-Based Processing Test (Camel)

* Place a valid order JSON file into the input directory
* Camel automatically picks up the file
* File content is processed and sent to ActiveMQ
* On failure, file is moved to the error directory

---
## ActiveMQ Verification

* Open ActiveMQ Admin Console
* Navigate to Queues section
* Verify messages are available in the order queue

---
## H2 Database Verification

* Open H2 Console in browser
* Connect using configured JDBC URL
* Verify order records in database tables

---
## Common Validation Checklist

* ActiveMQ is running before application startup
* Required directories exist on disk
* Application starts without `BeanCreationException`
* Camel routes are enabled
* Queue messages are visible in ActiveMQ console

---
## Technology Stack

* Java 17
* Spring Boot 2.7.x
* Apache Camel 3.x
* ActiveMQ Classic
* H2 Database
* Maven


Post request on Order API
<img width="941" height="537" alt="image" src="https://github.com/user-attachments/assets/45f4dda5-1b0f-45d4-8379-bd39201a56b4" />

H2 Database
<img width="940" height="605" alt="image" src="https://github.com/user-attachments/assets/eab23af4-46e8-451c-b8cf-da953bb2a5dd" />

JSON files
<img width="940" height="259" alt="image" src="https://github.com/user-attachments/assets/9f68b6a0-347e-40c0-a17c-dde565eb385a" />

ActiveMQ Dashboard
<img width="940" height="418" alt="image" src="https://github.com/user-attachments/assets/71fdd7b6-2f4d-4806-b282-6dc405554c81" />

Logs
<img width="940" height="205" alt="image" src="https://github.com/user-attachments/assets/1981808d-802e-468d-8875-da04895f5b71" />

Get Order By ID
<img width="940" height="552" alt="image" src="https://github.com/user-attachments/assets/c79e152a-7ab1-408f-b793-edf8dff765b6" />

List Orders by Customer
<img width="940" height="592" alt="image" src="https://github.com/user-attachments/assets/fca1a39b-2835-4a89-bd5e-3b342d6e94ad" />






