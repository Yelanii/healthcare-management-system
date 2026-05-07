
# Healthcare Management System

## Overview

This project is a Java-based Healthcare Management System designed to manage core healthcare operations such as:

* Patient management
* Doctor management
* Appointment scheduling
* Medical records
* Prescriptions
* Billing management

The system follows a structured client-server architecture and demonstrates backend software engineering concepts including RESTful API development, layered architecture, database persistence, and object-oriented programming principles.

---

# Key Features

* Patient registration and management
* Doctor profile management
* Appointment scheduling and tracking
* Medical record management
* Prescription handling
* Billing and payment management
* REST API implementation
* Database persistence using JPA
* Exception handling and validation

---

# Technologies Used

* Java
* Maven
* RESTful APIs
* Jersey (JAX-RS)
* JSON / Jackson
* Object-Oriented Programming (OOP)

---

# Project Structure

```text
healthcare-management-system/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dao/
│   │   │   ├── model/
│   │   │   ├── resources/
│   │   │   ├── exceptions/
│   │   │   └── services/
│   │   │
│   │   └── resources/
│   │       └── META-INF/
│   │           └── persistence.xml
│
├── pom.xml
├── README.md
└── .gitignore
```

---

# System Architecture

The application follows a layered architecture:

```text
Client Request
      ↓
REST API Resources
      ↓
Business Logic / Services
      ↓
DAO Layer
      ↓
Database
```

This architecture improves:

* Maintainability
* Scalability
* Separation of concerns
* Code organization

---

# Core Components

## Resource Layer

Handles REST API endpoints and client requests.

Examples:

* PatientResource
* DoctorResource
* AppointmentResource
* PrescriptionResource
* BillingResource

## DAO Layer

Responsible for database operations and persistence.

Examples:

* PatientDAO
* DoctorDAO
* AppointmentDAO
* BillingDAO

## Model Layer

Contains entity classes representing healthcare system objects.

Examples:

* Patient
* Doctor
* Appointment
* MedicalRecord
* Prescription
* Billing

---

# Example API Operations

## Patient Management

```http
GET /patients
POST /patients
PUT /patients/{id}
DELETE /patients/{id}
```

## Appointment Management

```http
GET /appointments
POST /appointments
PUT /appointments/{id}
DELETE /appointments/{id}
```

## Billing Management

```http
GET /billing
POST /billing
```

---

# Database Persistence

The project uses JPA persistence configuration through:

```text
persistence.xml
```

The persistence layer manages:

* Entity mapping
* Database communication
* Transaction management
* CRUD operations

---

# Skills Demonstrated

* Backend Development
* REST API Design
* Java Programming
* Database Persistence
* Object-Oriented Programming
* Layered Software Architecture
* CRUD Operations
* Exception Handling
* Software Design Principles
* Healthcare System Modelling

---

# How to Run the Project

## 1. Clone the Repository

```bash
git clone https://github.com/your-username/healthcare-management-system.git
```

## 2. Open the Project

Open the project using:

* IntelliJ IDEA
* Eclipse
* VS Code

---

## 3. Install Dependencies

Maven dependencies are managed through:

```text
pom.xml
```

---

## 4. Configure Database

Update database configuration settings inside:

```text
src/main/resources/META-INF/persistence.xml
```

---

## 5. Run the Application

Run the server/application from your IDE or using Maven.

---

# Future Improvements

Potential enhancements for future development:

* Authentication and authorization
* Role-based access control
* Frontend user interface
* Appointment notifications
* Reporting and analytics dashboard
* Cloud deployment
* API documentation using Swagger

---

# Academic Context

This project was developed for educational purposes to demonstrate backend software engineering concepts, RESTful service development, and healthcare system management.

---

# License

This project is licensed under the MIT License.

---

# Author

Y.M.Hanwella
