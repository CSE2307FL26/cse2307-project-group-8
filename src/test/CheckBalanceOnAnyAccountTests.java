package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.BankAccount;

public class CheckBalanceOnAnyAccountTests {

    @Test
    public void testCheckBalanceOnAnyAccountReturnsCorrectBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);

        assertEquals(100, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testCheckBalanceOnAnyAccountNewAccountIsZero() {
        BankAccount testAccount = new BankAccount();

        assertEquals(0, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testCheckBalanceOnAnyAccountAfterWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(30);

        assertEquals(70, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testCheckBalanceOnAnyAccountOtherAccount() {
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();
        second.deposit(250);

        assertEquals(250, first.checkBalanceOnAnyAccount(second), 0.001);
    }

    @Test
    public void testCheckBalanceOnAnyAccountNullAccount() {
        BankAccount testAccount = new BankAccount();

        assertThrows(IllegalArgumentException.class, () -> testAccount.checkBalanceOnAnyAccount(null));
    }
}