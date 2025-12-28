package com.example.bank.model;

public class Customer {
    private int customerId;
    private String fullName;
    private String dob; // yyyy-mm-dd string for simplicity
    private String email;
    private String phone;
    private String address;

    public Customer() {}

    // Getters and setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int id) { this.customerId = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String name) { this.fullName = name; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }
    public String getPhone() { return phone; }
    public void setPhone(String p) { this.phone = p; }
    public String getAddress() { return address; }
    public void setAddress(String addr) { this.address = addr; }
}
