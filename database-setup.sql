-- Database Setup Script for Bank Management System
-- Run this script to create the database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS bank_management;
USE bank_management;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    dob DATE,
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create accounts table
CREATE TABLE IF NOT EXISTS accounts (
    account_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    account_type ENUM('SAVINGS', 'CURRENT') NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

-- Create transactions table
CREATE TABLE IF NOT EXISTS transactions (
    txn_id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    txn_type ENUM('DEPOSIT', 'WITHDRAW', 'TRANSFER') NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    description VARCHAR(255),
    txn_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    related_account INT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE,
    FOREIGN KEY (related_account) REFERENCES accounts(account_id) ON DELETE SET NULL
);

-- Insert default admin user
-- Password: admin123 (hashed with SHA-256)
INSERT INTO users (username, password, role) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'admin')
ON DUPLICATE KEY UPDATE password = VALUES(password);

-- Insert sample customer
INSERT INTO customers (full_name, dob, email, phone, address) VALUES
('John Doe', '1990-01-15', 'john.doe@email.com', '+1-555-0123', '123 Main St, City, State 12345')
ON DUPLICATE KEY UPDATE full_name = full_name;

-- Insert sample account for the customer
INSERT INTO accounts (customer_id, account_type, balance) VALUES
(1, 'SAVINGS', 1000.00)
ON DUPLICATE KEY UPDATE balance = balance;

-- Create indexes for better performance
CREATE INDEX idx_username ON users(username);
CREATE INDEX idx_customer_id ON accounts(customer_id);
CREATE INDEX idx_account_id ON transactions(account_id);
CREATE INDEX idx_transaction_date ON transactions(txn_date);

-- Grant permissions (adjust as needed for your MySQL setup)
-- GRANT ALL PRIVILEGES ON bank_management.* TO 'root'@'localhost';
-- FLUSH PRIVILEGES;