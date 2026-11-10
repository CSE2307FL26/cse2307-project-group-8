package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.BankAccount;

import java.util.*;

public class TransactionHistoryTest {
    @Test
    public void testEmptyHistoryOnNewAccount() {
        BankAccount testAccount = new BankAccount();
        ArrayList<Double> expectedHistory = new ArrayList<Double>();
        assertEquals(testAccount.getTransactionHistory(), expectedHistory);
    }

    @Test
    public void testMultipleDepositsAndWithdrawals() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(30);
        testAccount.deposit(40);
        testAccount.withdraw(10);
        ArrayList<Double> expectedHistory = new ArrayList<Double>(Arrays.asList(50.0, -30.0, 40.0, -10.0));
        assertEquals(testAccount.getTransactionHistory(), expectedHistory);
    }

    @Test
    public void testInvalidActions() {
        BankAccount testAccount = new BankAccount();

        assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(-50));
        assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(0));

        ArrayList<Double> expectedHistory = new ArrayList<Double>();
        assertEquals(testAccount.getTransactionHistory(), expectedHistory);
    }

    @Test
    public void testTransferBetweenAccounts() {
        BankAccount account1 = new BankAccount();
        BankAccount account2 = new BankAccount();
        account1.deposit(50);
        account1.transferTo(account2, 30);

        assertEquals(new ArrayList<Double>(Arrays.asList(50.0, -30.0)), account1.getTransactionHistory());
        assertEquals(new ArrayList<Double>(Arrays.asList(30.0)), account2.getTransactionHistory());
    }
}
