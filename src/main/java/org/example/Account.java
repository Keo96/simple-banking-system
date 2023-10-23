package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Account {

    // keep track of the money a customer has in their account
    private double balance;

    // cash deposits and withdrawals are done
    private HashSet<String> processedTransactions;
    private List<Transaction> allTransactions;
    private Customer accountHolder;

    private static final double MAX_DEPOSIT_AMOUNT = 50000.0;
    private static final double DAILY_WITHDRAWAL_LIMIT = 2000.0;
    private double dailyWithdrawnAmount = 0.0;

    public Account(Customer accountHolder, double initialBalance) {
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.processedTransactions = new HashSet<>();
        this.allTransactions = new ArrayList<>();
    }
    public Response makeDeposits(Transaction transaction) {
        if (!transaction.getTransactionType().equals("deposit")) {
            return new Response(false, "Failed operation: Incorrect transaction type for deposit.");
        }
        if (processedTransactions.contains(transaction.getTransactionID())) {
            return new Response(false, "Failed operation: Transaction already processed.");
        }
        if (!transaction.getCustomerID().equals(accountHolder.getCustomerID())) {
            return new Response(false, "Failed operation: Transaction customer ID does not match account holder.");
        }
        if (transaction.getTransactionAmount() <= 0) {
            return new Response(false, "Failed operation: Invalid deposit amount.");
        }
        if (transaction.getTransactionAmount() > MAX_DEPOSIT_AMOUNT) {
            return new Response(false, "Deposit amount exceeds the maximum limit!");
        }

        balance += transaction.getTransactionAmount();
        processedTransactions.add(transaction.getTransactionID());
        allTransactions.add(transaction);
        return new Response(true, "Deposit successful");
    }

    public Response makeWithdrawal(Transaction transaction) {
        if (!transaction.getTransactionType().equals("withdrawal")) {
            return new Response(false, "Failed operation: Incorrect transaction type for withdrawal.");
        }
        if (processedTransactions.contains(transaction.getTransactionID())) {
            return new Response(false, "Failed operation: Transaction already processed.");
        }
        if (!transaction.getCustomerID().equals(accountHolder.getCustomerID())) {
            return new Response(false, "Failed operation: Transaction customer ID does not match account holder.");
        }
        if (balance < transaction.getTransactionAmount()) {
            return new Response(false, "Failed operation: Insufficient funds.");
        }
        if (transaction.getTransactionAmount() <= 0) {
            return new Response(false, "Failed operation: Invalid withdrawal amount.");
        }
        if (transaction.getTransactionAmount() + dailyWithdrawnAmount > DAILY_WITHDRAWAL_LIMIT) {
            return new Response(false, "Withdrawal amount exceeds the daily limit!");
        }

        dailyWithdrawnAmount += transaction.getTransactionAmount();
        balance -= transaction.getTransactionAmount();
        processedTransactions.add(transaction.getTransactionID());
        allTransactions.add(transaction);
        return new Response(true, "Withdrawal successful");
    }

    public double getBalance() {
        return balance;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void resetDailyWithdrawnAmount() {
        this.dailyWithdrawnAmount = 0.0;
    }

}