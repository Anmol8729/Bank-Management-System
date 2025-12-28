package com.example.bank.model;

import java.math.BigDecimal;

public class Account {
    private long accountId;
    private int customerId;
    private String accountType;
    private BigDecimal balance;

    public Account() {}

    // Getters & Setters
    public long getAccountId() { return accountId; }
    public void setAccountId(long id) { this.accountId = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int cid) { this.customerId = cid; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String t) { this.accountType = t; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal b) { this.balance = b; }
}
