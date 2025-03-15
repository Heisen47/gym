# Gym Management System

This project is a Gym Management System built using Java and Spring Boot. It allows users to manage gym memberships, including user registration with unique phone numbers and email addresses, and invoice management.

## Features

- User registration with validation for unique phone numbers and email addresses.
- Custom exception handling for duplicate phone numbers and email addresses.
- Invoice management with validation to prevent duplicate invoices for the same user.
- RESTful API endpoints for user and invoice management.
- Pagination support for retrieving invoices.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- Lombok

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA)

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Heisen47/gym-management-system.git
   ```

2. Navigate to the project directory:
   ```sh
   cd gym-management-system
   ```

3. Install the dependencies:
   ```sh
   mvn install
   ```

### Running the Application

1. Build the project:
   ```sh
   mvn clean package
   ```

2. Run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

#### Login
**POST** `/auth/login`: Authenticate a user and generate a JWT token.

**Request Body:**
```json
{
  "username": "admin@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiryDate": "2023-10-02T10:00:00Z",
  "roles": ["ROLE_ADMIN"]
}
```

#### Register Admin
**POST** `/admin/register`: Register a new admin.

**Request Body:**
```json
{
  "email": "admin@example.com",
  "password": "password123",
  "mobile": "1234567890",
  "name": "Admin Name"
}
```

**Response:**
```json
{
  "message": "Admin registered successfully"
}
```

### User Management

#### Retrieve All Users
**GET** `/users`: Retrieve all users.

**Response:**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "membership": true
  }
]
```

#### Add a New User
**POST** `/users`: Add a new user.

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "membership": true
}
```

**Response:**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "membership": true
}
```

### Invoice Management

#### Create a New Invoice
**POST** `/admin/invoice`: Create a new invoice.

**Request Body:**
```json
{
  "invoiceAmount": "100.00",
  "invoiceDate": "2023-10-01T10:00:00Z",
  "invoicedBy": "Admin",
  "payment": {
    "paymentId": 1
  },
  "product": "Gym Membership",
  "user": {
    "userId": 1
  }
}
```

**Response:**
```json
{
  "invoiceId": 1,
  "invoiceAmount": "100.00",
  "invoiceDate": "2023-10-01T10:00:00Z",
  "invoicedBy": "Admin",
  "payment": {
    "paymentId": 1
  },
  "product": "Gym Membership",
  "user": {
    "userId": 1
  }
}
```

#### Retrieve the Latest Invoice for a User
**GET** `/admin/invoice/{userId}/latest`: Retrieve the latest invoice for a user.

**Response:**
```json
{
  "invoiceId": 1,
  "invoiceAmount": "100.00",
  "invoiceDate": "2023-10-01T10:00:00Z",
  "invoicedBy": "Admin",
  "payment": {
    "paymentId": 1
  },
  "product": "Gym Membership",
  "user": {
    "userId": 1
  }
}
```

#### Retrieve Paginated Invoices for a User
**GET** `/admin/invoice/{userId}/paginated`: Retrieve paginated invoices for a user. Requires pagination parameters (e.g., `?page=0&size=10`).

**Response:**
```json
{
  "content": [
    {
      "invoiceId": 1,
      "invoiceAmount": "100.00",
      "invoiceDate": "2023-10-01T10:00:00Z",
      "invoicedBy": "Admin",
      "payment": {
        "paymentId": 1
      },
      "product": "Gym Membership",
      "user": {
        "userId": 1
      }
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

## Exception Handling
The application handles duplicate phone numbers, email addresses, and invoices by throwing custom exceptions and returning appropriate HTTP status codes.

### Custom Exceptions
- `DuplicatePhoneNumberException`
- `DuplicateEmailException`
- `InvalidInvoiceDataException`

### Global Exception Handler
The `GlobalExceptionHandler` class handles these exceptions and returns appropriate HTTP status codes.

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Contact
For any inquiries, please contact Heisen47 on GitHub.

