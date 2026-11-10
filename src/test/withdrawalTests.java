package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class withdrawalTests {

    @Test
    public void testWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);

        double newBalance = testAccount.withdraw(20);

        assertEquals(80, newBalance, 0.001);
        assertEquals(80, testAccount.getBalance(), 0.001);
    }
    @Test
    public void testInvalidWithdrawalAmount() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);

        assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(-50));
        assertEquals(100, testAccount.getBalance(), 0.001); // balance unchanged
    }

    @Test
    public void testOverdraftWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);

        assertThrows(IllegalArgumentException.class, () -> testAccount.withdraw(150));
        assertEquals(100, testAccount.getBalance(), 0.001);
    }
}