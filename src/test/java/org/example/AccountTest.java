package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("51772256c4b1", "Zoe-Kalidas", "zoe", "password");
        account = new Account(customer, 3000.0);
    }

    @Test
    public void testMakeDeposit() {
        Transaction depositTransaction = new Transaction(100.0, "deposit", customer.getCustomerID());
        Response response = account.makeDeposits(depositTransaction);
        assertTrue(response.isSuccess());
        assertEquals("Deposit successful", response.getMessage());
        assertEquals(3100.0, account.getBalance());
    }

    @Test
    public void testFailedDepositDueToMaxLimit() {
        Transaction depositTransaction = new Transaction(60000.0, "deposit", customer.getCustomerID());
        Response response = account.makeDeposits(depositTransaction);
        assertFalse(response.isSuccess());
        assertEquals("Deposit amount exceeds the maximum limit!", response.getMessage());
        assertEquals(3000.0, account.getBalance()); // No change in balance
    }

    @Test
    public void testFailedDepositDueToNegativeAmount() {
        Transaction depositTransaction = new Transaction(-100.0, "deposit", customer.getCustomerID());
        Response response = account.makeDeposits(depositTransaction);
        assertFalse(response.isSuccess());
        assertEquals("Failed operation: Invalid deposit amount.", response.getMessage());
        assertEquals(3000.0, account.getBalance()); // No change in balance
    }


    @Test
    public void testMakeWithdrawal() {
        Transaction withdrawalTransaction = new Transaction(50.0, "withdrawal", customer.getCustomerID());
        Response response = account.makeWithdrawal(withdrawalTransaction);
        assertTrue(response.isSuccess());
        assertEquals("Withdrawal successful", response.getMessage());
        assertEquals(2950.0, account.getBalance());
    }

    @Test
    public void testWithdrawalInvalidAmount() {
        Transaction withdrawalTransaction = new Transaction(-50.0, "withdrawal", customer.getCustomerID());
        Response response = account.makeWithdrawal(withdrawalTransaction);
        assertFalse(response.isSuccess());
        assertEquals("Failed operation: Invalid withdrawal amount.", response.getMessage());
        assertEquals(3000.0, account.getBalance());
    }

    @Test
    public void testWithdrawalExceedsLimit() {
        Transaction withdrawalTransaction1 = new Transaction(1500.0, "withdrawal", customer.getCustomerID());
        account.makeWithdrawal(withdrawalTransaction1);

        Transaction withdrawalTransaction2 = new Transaction(600.0, "withdrawal", customer.getCustomerID());
        Response response = account.makeWithdrawal(withdrawalTransaction2);
        assertFalse(response.isSuccess());
        assertEquals("Withdrawal amount exceeds the daily limit!", response.getMessage());
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    public void testWithdrawalInsufficientFunds() {
        Transaction withdrawalTransaction = new Transaction(3100.0, "withdrawal", customer.getCustomerID());
        Response response = account.makeWithdrawal(withdrawalTransaction);
        assertFalse(response.isSuccess());
        assertEquals("Failed operation: Insufficient funds.", response.getMessage());
        assertEquals(3000.0, account.getBalance());
    }
}