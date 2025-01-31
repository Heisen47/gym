# Gym Management System

This project is a Gym Management System built using Java and Spring Boot. It allows users to manage gym memberships, including user registration with unique phone numbers and email addresses.

## Features

- User registration with validation for unique phone numbers and email addresses.
- Custom exception handling for duplicate phone numbers and email addresses.
- RESTful API endpoints for user management.

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

### API Endpoints

- **GET /users**: Retrieve all users.
- **POST /users**: Add a new user. Requires a JSON body with `name`, `email`, `phoneNumber`, and `membership`.

### Example JSON for Adding a User

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "1234567890",
  "membership": true
}
```

## Exception Handling

The application handles duplicate phone numbers and email addresses by throwing custom exceptions and returning a 409 Conflict status code.

### Custom Exceptions

- `DuplicatePhoneNumberException`
- `DuplicateEmailException`

### Global Exception Handler

The `GlobalExceptionHandler` class handles these exceptions and returns appropriate HTTP status codes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Contact

For any inquiries, please contact Heisen47 on GitHub.