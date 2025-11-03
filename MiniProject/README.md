# Online Student Management System

## Project Overview
This is a Spring and Hibernate-based mini project that demonstrates Dependency Injection, CRUD operations, and Transaction Management for an online student management system.

## Technologies Used
- Java (JDK 11+)
- Spring Framework (Core, Context)
- Hibernate ORM
- MySQL Database
- Maven (for dependency management)

## Project Modules

### 1. Student-Course Management
- **Concept**: Dependency Injection using Spring Java-based configuration
- **Description**: Demonstrates how Student depends on a Course using Spring DI
- **Classes**: `Student.java`, `Course.java`, `AppConfig.java`

### 2. CRUD Operations
- **Concept**: Hibernate ORM for database operations
- **Description**: Perform Create, Read, Update, Delete operations on Student and Course entities
- **Classes**: `StudentDAO.java`, `Student.java`, `Course.java`
- **Operations**:
  - Add new student
  - Assign course to student
  - Update student records
  - Delete student records
  - View all students or specific student

### 3. Fee Payment & Refund
- **Concept**: Transaction Management using @Transactional
- **Description**: Ensures atomicity of payment and refund operations
- **Classes**: `FeeService.java`
- **Features**:
  - Deduct amount from student balance on payment
  - Refund on cancellation
  - Automatic rollback on failure

### 4. Spring + Hibernate Integration
- **Concept**: Layered Architecture
- **Description**: Spring handles services and Hibernate handles persistence logic
- **Components**:
  - **Model Layer**: `Student.java`, `Course.java`
  - **DAO Layer**: `StudentDAO.java`
  - **Service Layer**: `FeeService.java`
  - **Configuration Layer**: `AppConfig.java`
  - **Application Layer**: `MainApp.java`

## Project Structure
```
MiniProject/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── student/
│       │           └── management/
│       │               ├── model/
│       │               │   ├── Student.java
│       │               │   └── Course.java
│       │               ├── dao/
│       │               │   └── StudentDAO.java
│       │               ├── service/
│       │               │   └── FeeService.java
│       │               ├── config/
│       │               │   └── AppConfig.java
│       │               └── MainApp.java
│       └── resources/
│           └── hibernate.cfg.xml
├── pom.xml
└── README.md
```

## Database Schema

### students table
- `student_id` (Primary Key)
- `name` (VARCHAR)
- `course_id` (Foreign Key)
- `balance` (DECIMAL)

### courses table
- `course_id` (Primary Key)
- `course_name` (VARCHAR)
- `duration` (INT)

### payments table
- `payment_id` (Primary Key)
- `student_id` (Foreign Key)
- `amount` (DECIMAL)
- `date` (DATE)

## How to Run

1. **Setup Database**:
   - Create a MySQL database named `student_management`
   - Update database credentials in `hibernate.cfg.xml`

2. **Build the Project**:
   ```bash
   mvn clean install
   ```

3. **Run the Application**:
   ```bash
   java -cp target/classes com.student.management.MainApp
   ```

## Features
- Console-based interface with menu-driven options
- Real-time feedback for operations
- Error handling and validations
- Transaction management for payment operations
- Spring-managed beans with dependency injection
- Hibernate-managed database persistence

## Key Concepts Demonstrated

1. **Dependency Injection**: Beans are managed by Spring container and injected where needed
2. **CRUD Operations**: Full database interaction using Hibernate
3. **Transaction Management**: Ensures data consistency during payment operations
4. **Layered Architecture**: Separation of concerns with Model, DAO, Service, and Configuration layers
5. **ORM (Object-Relational Mapping)**: Hibernate maps Java objects to database tables

## Author
Vinayak - 23BCS12507
