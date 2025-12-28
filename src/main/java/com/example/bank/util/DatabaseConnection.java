package com.example.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream in = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            
            if (in == null) {
                throw new RuntimeException("Cannot find application.properties");
            }
            
            Properties props = new Properties();
            props.load(in);
            
            // CORRECT: Use property keys, not values
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");  // Changed from "root"
            password = props.getProperty("db.password");  // Changed from "1234"
            
            // If properties are null, use defaults
            if (url == null) url = "jdbc:mysql://localhost:3306/bank_management?useSSL=false&serverTimezone=UTC";
            if (username == null) username = "root";
            if (password == null) password = "1234";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database properties loaded successfully");
            System.out.println("URL: " + url);
            System.out.println("Username: " + username);
            System.out.println("Password: " + (password.isEmpty() ? "[empty]" : "*****"));
            
            // Initialize database schema
            initializeSchema();
            
        } catch (Exception e) {
            System.err.println("Failed to load DB properties: " + e.getMessage());
            e.printStackTrace();
            // Fallback to default values
            url = "jdbc:mysql://localhost:3306/bank_management?useSSL=false&serverTimezone=UTC";
            username = "root";
            password = "1234";
            password = "";
        }
    }
    
    private static void initializeSchema() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             InputStream schemaStream = DatabaseConnection.class.getClassLoader()
                 .getResourceAsStream("schema.sql")) {
            
            System.out.println("=== DATABASE INITIALIZATION START ===");
            System.out.println("Schema stream available: " + (schemaStream != null));
            
            if (schemaStream == null) {
                System.err.println("ERROR: schema.sql not found in classpath!");
                return;
            }
            
            String schema = new String(schemaStream.readAllBytes());
            System.out.println("Schema SQL loaded, length: " + schema.length());
            
            String[] statements = schema.split(";");
            System.out.println("Found " + statements.length + " SQL statements");
            
            for (int i = 0; i < statements.length; i++) {
                String stmt = statements[i].trim();
                if (!stmt.isEmpty()) {
                    System.out.println("Executing statement " + (i+1) + ": " + stmt.substring(0, Math.min(50, stmt.length())) + "...");
                    try (Statement statement = conn.createStatement()) {
                        statement.execute(stmt);
                        System.out.println("✓ Statement " + (i+1) + " executed successfully");
                    } catch (SQLException e) {
                        System.err.println("✗ Error executing statement " + (i+1) + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("=== DATABASE INITIALIZATION COMPLETE ===");
            
        } catch (Exception e) {
            System.err.println("=== DATABASE INITIALIZATION FAILED ===");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}