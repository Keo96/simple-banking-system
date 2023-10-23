package org.example;

import java.util.UUID;

public class Transaction {
    /* method to indicate where operations should fail by printing a brief description
      of the failed operation and returning FALSE
    */

    // customerID
    // avoid duplicate transactions
    // withdrawal cannot be used to make deposits
    private String transactionType;
    private String transactionID; //deposit or withdrawals
    private double transactionAmount;
    private String customerID;

    public Transaction(double transactionAmount, String transactionType, String customerID) {
        this.transactionID = UUID.randomUUID().toString();
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.customerID = customerID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getCustomerID() {
        return customerID;
    }
}
