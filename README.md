# SecureBank Management System

A comprehensive Java-based web banking management system built with modern technologies and following MVC architecture patterns.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### 🔐 Authentication & Authorization
- Secure user login/logout system
- Role-based access control (Admin/User)
- Session management
- Authentication filter for protected resources

### 👥 Customer Management
- Create new customer profiles
- View customer information
- Update customer details
- Customer search functionality

### 💳 Account Management
- Create savings and current accounts
- Account balance tracking
- Multiple accounts per customer
- Account status management

### 💰 Transaction Processing
- Deposit funds to accounts
- Withdraw funds from accounts
- Transfer money between accounts
- Transaction history and records
- Real-time balance updates

### 🎨 Modern Web Interface
- Responsive design with modern UI/UX
- Professional banking interface
- Mobile-friendly layout
- Intuitive navigation

## 🛠 Technology Stack

### Backend
- **Java**: 17 (LTS)
- **Web Server**: Jetty 11.0.15
- **Build Tool**: Maven 3.x
- **Database**: MySQL 8.x
- **Servlet API**: Jakarta Servlet 6.0

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with CSS Variables
- **JavaScript**: ES6+ features
- **Font Awesome**: Icons and UI elements
- **Google Fonts**: Inter & Poppins typography

### Architecture
- **MVC Pattern**: Model-View-Controller architecture
- **DAO Pattern**: Data Access Object for database operations
- **Service Layer**: Business logic separation
- **Servlets**: Request handling and response generation

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java Development Kit (JDK)**: Version 17 or higher
- **Apache Maven**: Version 3.6 or higher
- **MySQL Server**: Version 8.0 or higher
- **Git**: For cloning the repository (optional)

### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **RAM**: Minimum 2GB
- **Disk Space**: 500MB free space
- **Network**: Internet connection for Maven dependencies

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd bank-management
```

### 2. Configure Database
Create a MySQL database and user:

```sql
-- Create database
CREATE DATABASE bank_management;

-- Create user (optional - you can use root)
CREATE USER 'bankuser'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON bank_management.* TO 'bankuser'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Update Database Configuration
Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/bank_management?useSSL=false&serverTimezone=UTC
db.username=root
db.password=1234
db.driver=com.mysql.cj.jdbc.Driver
```

### 4. Build the Project
```bash
mvn clean compile
```

## 🗄 Database Setup

### Option 1: Automatic Setup (Recommended)
The application will automatically create and populate the database tables on first run.

### Option 2: Manual Setup
Run the provided SQL script:

```bash
mysql -u root -p < database-setup.sql
```

### Database Schema

The system uses four main tables:

#### `users` - User Authentication
- `user_id` (Primary Key)
- `username` (Unique)
- `password` (SHA-256 hashed)
- `role` (admin/user)
- `created_at`

#### `customers` - Customer Information
- `customer_id` (Primary Key)
- `full_name`
- `dob` (Date of Birth)
- `email`
- `phone`
- `address`
- `created_at`

#### `accounts` - Bank Accounts
- `account_id` (Primary Key)
- `customer_id` (Foreign Key)
- `account_type` (SAVINGS/CURRENT)
- `balance`
- `created_at`

#### `transactions` - Transaction Records
- `txn_id` (Primary Key)
- `account_id` (Foreign Key)
- `txn_type` (DEPOSIT/WITHDRAW/TRANSFER)
- `amount`
- `description`
- `txn_date`
- `related_account` (For transfers)

### Default Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `admin`

## ▶ Running the Application

### Development Mode (with Jetty)
```bash
mvn jetty:run
```

The application will start on `http://localhost:8080`

### Production Deployment
1. Build the WAR file:
```bash
mvn clean package
```

2. Deploy the generated `bank-management.war` to your servlet container (Tomcat, Jetty, etc.)

### Alternative: Run with External Server
```bash
# Build WAR
mvn clean package

# Deploy to Tomcat/Jetty
# Copy target/bank-management.war to your server's webapps directory
```

## 📖 Usage

### Accessing the Application
1. Open your browser and navigate to `http://localhost:8080`
2. You'll be redirected to the login page

### Admin Functions
- **Login** with admin credentials (admin/admin123)
- **Create Customers**: Add new bank customers
- **Create Accounts**: Open new accounts for customers
- **View Transactions**: Monitor all banking activities
- **Manage Users**: User administration (if implemented)

### Customer Functions
- **View Account Balance**: Check current balance
- **Deposit Funds**: Add money to accounts
- **Withdraw Funds**: Remove money from accounts
- **Transfer Money**: Move funds between accounts

### Navigation
- Use the sidebar menu to navigate between different sections
- All forms include validation and error handling
- Session timeout after 30 minutes of inactivity

## 🏗 Project Structure

```
bank-management/
├── src/
│   └── main/
│       ├── java/com/example/bank/
│       │   ├── dao/           # Data Access Objects
│       │   │   ├── AccountDao.java
│       │   │   ├── CustomerDao.java
│       │   │   ├── TransactionDao.java
│       │   │   └── UserDao.java
│       │   ├── filter/        # Servlet Filters
│       │   │   └── AuthFilter.java
│       │   ├── model/         # Data Models
│       │   │   ├── Account.java
│       │   │   ├── Customer.java
│       │   │   ├── TransactionRecord.java
│       │   │   └── User.java
│       │   ├── service/       # Business Logic
│       │   │   ├── AuthService.java
│       │   │   └── BankService.java
│       │   ├── servlet/       # Web Controllers
│       │   │   ├── BalanceServlet.java
│       │   │   ├── CreateAccountServlet.java
│       │   │   ├── CreateCustomerServlet.java
│       │   │   ├── DepositServlet.java
│       │   │   ├── LoginServlet.java
│       │   │   ├── LogoutServlet.java
│       │   │   ├── TransferServlet.java
│       │   │   └── WithdrawServlet.java
│       │   ├── ui/            # Console Interface (legacy)
│       │   │   └── ConsoleUI.java
│       │   └── util/          # Utilities
│       │       └── DatabaseConnection.java
│       ├── resources/
│       │   ├── application.properties
│       │   └── schema.sql
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml
│           ├── bank.html       # Main application page
│           └── index.html      # Redirect page
├── database-setup.sql          # Database initialization script
├── pom.xml                     # Maven configuration
└── README.md                   # This file
```

## 🔗 API Endpoints

| Endpoint | Method | Description | Parameters |
|----------|--------|-------------|------------|
| `/login` | POST | User authentication | username, password |
| `/logout` | GET | User logout | - |
| `/createCustomer` | POST | Create new customer | fullName, dob, email, phone, address |
| `/createAccount` | POST | Create new account | customerId, accountType, initialDeposit |
| `/deposit` | POST | Deposit funds | accountId, amount, description |
| `/withdraw` | POST | Withdraw funds | accountId, amount, description |
| `/transfer` | POST | Transfer funds | fromAccountId, toAccountId, amount, description |
| `/balance` | GET | Check balance | accountId |

## 🔒 Security Features

### Authentication
- SHA-256 password hashing
- Session-based authentication
- Automatic session timeout (30 minutes)
- Secure logout functionality

### Authorization
- Role-based access control
- Authentication filter for protected resources
- Admin-only functions restriction

### Data Protection
- SQL injection prevention (Prepared Statements)
- XSS protection (Input validation)
- CSRF protection (Session validation)

### Database Security
- Connection pooling (future enhancement)
- Parameterized queries
- Secure credential storage

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow Java naming conventions
- Add proper error handling
- Include unit tests for new features
- Update documentation for API changes
- Ensure code compiles without warnings

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Troubleshooting

### Common Issues

**Database Connection Failed**
- Verify MySQL server is running
- Check database credentials in `application.properties`
- Ensure database `bank_management` exists

**Application Won't Start**
- Check Java version: `java -version` (should be 17+)
- Verify Maven installation: `mvn -version`
- Check for port conflicts on 8080

**Login Issues**
- Ensure database is properly initialized
- Check admin user exists: username `admin`, password `admin123`
- Verify password hashing is consistent

**Build Errors**
- Run `mvn clean` to clear cache
- Check for missing dependencies
- Verify Java version compatibility

### Getting Help
- Check the [Issues](../../issues) page for known problems
- Review the [Wiki](../../wiki) for detailed guides
- Contact maintainers for support

## 🔄 Future Enhancements

- [ ] REST API endpoints
- [ ] User registration system
- [ ] Email notifications
- [ ] PDF statement generation
- [ ] Multi-branch support
- [ ] Audit logging
- [ ] Two-factor authentication
- [ ] Mobile app companion
- [ ] API rate limiting
- [ ] Database connection pooling

---

**SecureBank Management System** - Built with ❤️ by Anmol Yadav
