package com.example.bank.model;

import java.math.BigDecimal;

public class TransactionRecord {
    private long txnId;
    private long accountId;
    private String txnType;
    private BigDecimal amount;
    private String description;
    private Long relatedAccount;

    // Getters & setters
    public long getTxnId() { return txnId; }
    public void setTxnId(long id) { this.txnId = id; }
    public long getAccountId() { return accountId; }
    public void setAccountId(long a) { this.accountId = a; }
    public String getTxnType() { return txnType; }
    public void setTxnType(String t) { this.txnType = t; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
    public Long getRelatedAccount() { return relatedAccount; }
    public void setRelatedAccount(Long r) { this.relatedAccount = r; }
}
