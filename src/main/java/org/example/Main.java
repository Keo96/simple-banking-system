package org.example;

import java.util.HashMap;
import java.util.Map;


public class Main {
    static final Map<String, Map<String, String>> CUSTOMERS = new HashMap<>();
    static {
        CUSTOMERS.put("51772256c4b1", Map.of("name", "Zoe-Kalidas", "username", "ZoeUser", "password", "ZoePass"));
        CUSTOMERS.put("7cdfed58dd3e", Map.of("name", "Nassim-Jessup", "username", "NassimUser", "password", "NassimPass"));
        CUSTOMERS.put("7b38acd86777", Map.of("name", "Clarabel-Murphy", "username", "ClarabelUser", "password", "ClarabelPass"));
    }

    public static void main(String[] args) {
        Map<String, Customer> customerMap = new HashMap<>();
        for (String customerID : CUSTOMERS.keySet()) {
            String name = CUSTOMERS.get(customerID).get("name");
            String username = CUSTOMERS.get(customerID).get("username");
            String password = CUSTOMERS.get(customerID).get("password");
            customerMap.put(customerID, new Customer(customerID, name, username, password));
        }
        Account myAccount = new Account(customerMap.get("51772256c4b1"), 2000);
        System.out.println("Account Holder: " + myAccount.getAccountHolder().getCustomerName());
        System.out.println("Initial balance: $" + myAccount.getBalance());

        Transaction depositTransaction = new Transaction(100.0,"deposit", customerMap.get("51772256c4b1").getCustomerID());
        Response depositResponse1 = myAccount.makeDeposits(depositTransaction);
        System.out.println(depositResponse1.getMessage());
        System.out.println("Updated balance: $" + myAccount.getBalance());

        Transaction withdrawalTransaction = new Transaction(50.0, "withdrawal", customerMap.get("51772256c4b1").getCustomerID());
        Response withdrawalResponse1 = myAccount.makeWithdrawal(withdrawalTransaction);
        System.out.println(withdrawalResponse1.getMessage());
        System.out.println("Final balance: $" + myAccount.getBalance());

        System.out.println("Recent Transactions");
        for (Transaction transaction : myAccount.getAllTransactions()) {
            System.out.println(transaction.getTransactionID() + " " + customerMap.get(transaction.getCustomerID()).getCustomerName() + " " +
            transaction.getTransactionAmount() + " " + transaction.getTransactionType());
        }
    }
}
