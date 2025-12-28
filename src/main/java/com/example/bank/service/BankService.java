package com.example.bank.service;

import com.example.bank.dao.AccountDao;
import com.example.bank.dao.CustomerDao;
import com.example.bank.dao.TransactionDao;
import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.model.TransactionRecord;
import com.example.bank.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.Connection;

public class BankService {
    private final CustomerDao customerDao = new CustomerDao();
    private final AccountDao accountDao = new AccountDao();
    private final TransactionDao txnDao = new TransactionDao();

    // customer
    public int createCustomer(Customer c) throws Exception {
        return customerDao.createCustomer(c);
    }
    public Customer getCustomer(int id) throws Exception {
        return customerDao.findById(id);
    }

    // account creation with optional initial deposit (transaction created too)
    public int createAccount(int customerId, String accType, BigDecimal initialDeposit) throws Exception {
        Account acc = new Account();
        acc.setCustomerId(customerId);
        acc.setAccountType(accType);
        acc.setBalance(initialDeposit == null ? BigDecimal.ZERO : initialDeposit);
        int accId = accountDao.createAccount(acc);
        if (initialDeposit != null && initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.setAutoCommit(false);
                TransactionRecord tr = new TransactionRecord();
                tr.setAccountId(accId);
                tr.setTxnType("DEPOSIT");
                tr.setAmount(initialDeposit);
                tr.setDescription("Initial deposit");
                txnDao.recordTransaction(tr, conn);
                conn.commit();
            }
        }
        return accId;
    }

    // deposit
    public boolean deposit(int accountId, BigDecimal amount) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            Account a = accountDao.findById(accountId);
            if (a == null) throw new Exception("Account not found");
            BigDecimal newBal = a.getBalance().add(amount);
            boolean ok = accountDao.updateBalance(accountId, newBal, conn);
            if (!ok) { conn.rollback(); return false; }
            TransactionRecord tr = new TransactionRecord();
            tr.setAccountId(accountId);
            tr.setTxnType("DEPOSIT");
            tr.setAmount(amount);
            tr.setDescription("Deposit");
            txnDao.recordTransaction(tr, conn);
            conn.commit();
            return true;
        }
    }

    // withdraw
    public boolean withdraw(int accountId, BigDecimal amount) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            Account a = accountDao.findById(accountId);
            if (a == null) throw new Exception("Account not found");
            if (a.getBalance().compareTo(amount) < 0) {
                return false; // insufficient funds
            }
            BigDecimal newBal = a.getBalance().subtract(amount);
            boolean ok = accountDao.updateBalance(accountId, newBal, conn);
            if (!ok) { conn.rollback(); return false; }
            TransactionRecord tr = new TransactionRecord();
            tr.setAccountId(accountId);
            tr.setTxnType("WITHDRAW");
            tr.setAmount(amount.negate());
            tr.setDescription("Withdrawal");
            txnDao.recordTransaction(tr, conn);
            conn.commit();
            return true;
        }
    }

    // transfer
    public boolean transfer(int fromAcc, int toAcc, BigDecimal amount) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            Account aFrom = accountDao.findById(fromAcc);
            Account aTo = accountDao.findById(toAcc);
            if (aFrom == null || aTo == null) throw new Exception("Account not found");
            if (aFrom.getBalance().compareTo(amount) < 0) {
                return false;
            }
            boolean ok1 = accountDao.updateBalance(fromAcc, aFrom.getBalance().subtract(amount), conn);
            boolean ok2 = accountDao.updateBalance(toAcc, aTo.getBalance().add(amount), conn);
            if (!ok1 || !ok2) { conn.rollback(); return false; }
            TransactionRecord t1 = new TransactionRecord();
            t1.setAccountId(fromAcc);
            t1.setTxnType("TRANSFER");
            t1.setAmount(amount.negate());
            t1.setDescription("Transfer to " + toAcc);
            t1.setRelatedAccount((long) toAcc);
            txnDao.recordTransaction(t1, conn);

            TransactionRecord t2 = new TransactionRecord();
            t2.setAccountId(toAcc);
            t2.setTxnType("TRANSFER");
            t2.setAmount(amount);
            t2.setDescription("Transfer from " + fromAcc);
            t2.setRelatedAccount((long) fromAcc);
            txnDao.recordTransaction(t2, conn);

            conn.commit();
            return true;
        }
    }

    public Account getAccount(int accountId) throws Exception {
        return accountDao.findById(accountId);
    }
}
