package com.example.bank.ui;

import com.example.bank.model.Customer;
import com.example.bank.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final BankService service = new BankService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            printMainMenu();
            int choice = readInt();
            try {
                switch (choice) {
                    case 1: createCustomer(); break;
                    case 2: listCustomers(); break;
                    case 3: createAccount(); break;
                    case 4: deposit(); break;
                    case 5: withdraw(); break;
                    case 6: transfer(); break;
                    case 7: balanceEnquiry(); break;
                    case 0: System.out.println("Exiting..."); return;
                    default: System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=== BANK MANAGEMENT ===");
        System.out.println("1. Create Customer");
        System.out.println("2. List Customers");
        System.out.println("3. Create Account");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Transfer");
        System.out.println("7. Balance Enquiry");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private int readInt() {
        try { return Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { return -1; }
    }

    private long readLong() {
        try { return Long.parseLong(sc.nextLine().trim()); } catch (Exception e) { return -1; }
    }

    private BigDecimal readMoney() {
        try { return new BigDecimal(sc.nextLine().trim()); } catch (Exception e) { return BigDecimal.ZERO; }
    }

    private void createCustomer() throws Exception {
        Customer c = new Customer();
        System.out.print("Full name: "); c.setFullName(sc.nextLine().trim());
        System.out.print("DOB (yyyy-mm-dd): "); c.setDob(sc.nextLine().trim());
        System.out.print("Email: "); c.setEmail(sc.nextLine().trim());
        System.out.print("Phone: "); c.setPhone(sc.nextLine().trim());
        System.out.print("Address: "); c.setAddress(sc.nextLine().trim());
        int id = service.createCustomer(c);
        System.out.println("Customer created with ID: " + id);
    }

    private void listCustomers() throws Exception {
        System.out.println("Customers:");
        // reuse DAO via service
        // For brevity we call service.getCustomer individually or extend service to listAll
        // We'll attempt to retrieve up to some ids - but better to implement listing in CustomerDao; we added listAll earlier
        List<com.example.bank.model.Customer> list = new com.example.bank.dao.CustomerDao().listAll();
        for (Customer c : list) {
            System.out.printf("%d: %s, Email:%s, Phone:%s\n", c.getCustomerId(), c.getFullName(), c.getEmail(), c.getPhone());
        }
    }

    private void createAccount() throws Exception {
        System.out.print("Customer ID: "); int cid = readInt();
        System.out.print("Account Type (SAVINGS/CURRENT): "); String type = sc.nextLine().trim();
        System.out.print("Initial deposit: "); BigDecimal m = readMoney();
        long accId = service.createAccount(cid, type, m);
        System.out.println("Account created: " + accId);
    }

    private void deposit() throws Exception {
        System.out.print("Account ID: "); int aid = (int) readLong();
        System.out.print("Amount: "); BigDecimal amt = readMoney();
        boolean ok = service.deposit(aid, amt);
        System.out.println(ok ? "Deposit successful" : "Deposit failed");
    }

    private void withdraw() throws Exception {
        System.out.print("Account ID: "); int aid = (int) readLong();
        System.out.print("Amount: "); BigDecimal amt = readMoney();
        boolean ok = service.withdraw(aid, amt);
        System.out.println(ok ? "Withdrawal successful" : "Insufficient funds or error");
    }

    private void transfer() throws Exception {
        System.out.print("From Account ID: "); int from = (int) readLong();
        System.out.print("To Account ID: "); int to = (int) readLong();
        System.out.print("Amount: "); BigDecimal amt = readMoney();
        boolean ok = service.transfer(from, to, amt);
        System.out.println(ok ? "Transfer successful" : "Transfer failed (insufficient funds or error)");
    }

    private void balanceEnquiry() throws Exception {
        System.out.print("Account ID: "); int aid = (int) readLong();
        com.example.bank.model.Account a = service.getAccount(aid);
        if (a != null) {
            System.out.printf("Account %d Balance: %s\n", a.getAccountId(), a.getBalance().toPlainString());
        } else System.out.println("Account not found");
    }
}
