package org.example;

public class Customer {
    private String customerID;
    private String customerName;
    String username;
    String password;

    public Customer(String customerID, String customerName, String username, String password) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.username = username;
        this.password = password;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
