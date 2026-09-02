# Petrol Pump Management System

A comprehensive web-based Petrol Pump Management System built with Spring Boot, Thymeleaf, and MySQL. This system helps manage fuel inventory, sales/billing, customers, employees, expenses, and generate reports.

## 🛠️ Technologies Used

- **Java 8** - Core programming language
- **Spring Boot 2.7.18** - Backend web application framework
- **Spring MVC** - Request handling and application architecture
- **Spring Data JPA / Hibernate** - Database operations and ORM
- **Spring Security** - Authentication and authorization
- **Thymeleaf** - Dynamic HTML pages / frontend
- **MySQL** - Database management
- **Bootstrap 5** - Styling and UI framework
- **Font Awesome** - Icons
- **Maven** - Dependency and project management

## 📋 Features

### 1. Authentication & Authorization
- User login/logout functionality
- Role-based access control (Admin/Staff)
- Secure password encryption with BCrypt
- User management (Admin only)

### 2. Dashboard
- Overview of petrol pump operations
- Today's sales and profit summary
- Monthly statistics
- Low stock alerts
- Quick access to major modules

### 3. Fuel Management
- Add and update fuel types (Petrol, Diesel, etc.)
- Maintain current fuel quantity and price
- Automatic low-stock alerts
- Fuel stock tracking

### 4. Sales & Billing
- Record fuel sales with automatic bill calculation
- Customer information capture (optional)
- Multiple payment methods (Cash, Card, UPI)
- Automatic fuel stock reduction
- Today's sales view

### 5. Customer Management
- Store customer and vehicle details
- Vehicle number tracking
- Customer search functionality
- Customer history tracking

### 6. Employee Management
- Add, update, and remove employee details
- Employee position and salary tracking
- Contact information management
- Employee search functionality

### 7. Expense Management
- Record various expenses (Maintenance, Electricity, Salary, etc.)
- Expense categorization
- Today's expenses view
- Expense tracking for financial monitoring

### 8. Reports
- **Sales Report**: Generate sales reports with date range filtering
- **Expense Report**: Track expenses with date range filtering
- **Fuel Stock Report**: Monitor fuel inventory levels
- **Profit Report**: Analyze profit and loss

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- MySQL 5.7+ or MySQL 8.0+
- IDE (NetBeans, IntelliJ IDEA, or Eclipse)

### Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE petrol_pump_db;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petrol_pump_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd petrol-pump-management
```

2. Build the project using Maven:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

Or run the main class `PetrolPumpManagementApplication` from your IDE.

### Default Users

The application automatically creates default users on first run:

- **Admin User**
  - Username: `admin`
  - Password: `admin123`
  - Role: ADMIN

- **Staff User**
  - Username: `staff`
  - Password: `staff123`
  - Role: STAFF

### Default Data

The application also initializes sample data:
- Fuel types: Petrol (₹95.50/L), Diesel (₹85.00/L)
- Sample employee: John Doe (Pump Attendant)

## 📁 Project Structure

```
petrol-pump-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── petrolpump/
│   │   │           ├── config/          # Configuration classes
│   │   │           ├── controller/      # Spring MVC controllers
│   │   │           ├── model/           # JPA entities
│   │   │           ├── repository/      # Spring Data JPA repositories
│   │   │           ├── service/         # Business logic layer
│   │   │           └── PetrolPumpManagementApplication.java
│   │   └── resources/
│   │       ├── static/                 # CSS, JS, images
│   │       ├── templates/              # Thymeleaf templates
│   │       └── application.properties   # Application configuration
│   └── test/
└── pom.xml                             # Maven configuration
```

## 🔧 Configuration

### Application Properties

Key configuration options in `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/petrol_pump_db
spring.datasource.username=root
spring.datasource.password=root

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf Configuration
spring.thymeleaf.cache=false
```

## 🎯 Usage

### 1. Login
- Access the application at `http://localhost:8080`
- Login with default credentials or create new users

### 2. Dashboard
- View overview of operations
- Check low stock alerts
- Access quick actions

### 3. Fuel Management
- Navigate to Fuel Management
- Add new fuel types with price and stock
- Update fuel prices and quantities
- Monitor low stock alerts

### 4. Sales & Billing
- Navigate to Sales & Billing
- Click "New Sale"
- Select fuel type and enter quantity
- Optionally add customer information
- Complete the sale (stock automatically reduces)

### 5. Reports
- Navigate to Reports section
- Generate sales, expense, fuel stock, or profit reports
- Filter by date range
- Analyze business performance

## 🔒 Security

- Passwords are encrypted using BCrypt
- Role-based access control (Admin/Staff)
- CSRF protection enabled
- Session management

## 📊 Database Schema

### Users Table
- User authentication and authorization
- Roles: ADMIN, STAFF

### Fuels Table
- Fuel types, prices, and stock levels
- Low stock threshold configuration

### Customers Table
- Customer and vehicle information
- Sales history tracking

### Employees Table
- Employee details and positions
- Salary and contact information

### Expenses Table
- Expense tracking and categorization
- Financial monitoring

### Sales Table
- Sales transactions
- Links to fuel, customer, and employee

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

## 📝 API Endpoints

### Public Endpoints
- `GET /` - Redirect to dashboard
- `GET /login` - Login page
- `POST /login` - Login form submission
- `GET /register` - Registration page
- `POST /register` - User registration

### Authenticated Endpoints
- `GET /dashboard` - Dashboard
- `GET /fuels` - Fuel management
- `GET /sales` - Sales management
- `GET /customers` - Customer management
- `GET /employees` - Employee management
- `GET /expenses` - Expense management
- `GET /reports` - Reports section

### Admin Only Endpoints
- `GET /admin/users` - User management

## 🐛 Troubleshooting

### Database Connection Issues
- Ensure MySQL is running
- Check database credentials in application.properties
- Verify database exists

### Port Already in Use
- Change server port in application.properties:
```properties
server.port=8081
```

### Build Errors
- Ensure Java 8+ is installed
- Verify Maven is properly configured
- Clean and rebuild: `mvn clean install`

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👥 Authors

- Your Name - Initial work

## 🙏 Acknowledgments

- Spring Boot documentation
- Thymeleaf documentation
- Bootstrap 5 framework
- Font Awesome icons

## 📞 Support

For support, please open an issue in the repository or contact the development team.

---

**Note**: This is a college project and can be freely used for educational purposes.
#   P e t r o l - P u m p - M a n a g e m e n t - S y s t e m -  
 