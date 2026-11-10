package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.BankAccount;

public class checkBalanceOnAnyAccountTests {

    @Test
    public void testcheckBalanceOnAnyAccountReturnsCorrectBalance() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);

        assertEquals(100, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testcheckBalanceOnAnyAccountNewAccountIsZero() {
        BankAccount testAccount = new BankAccount();

        assertEquals(0, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testcheckBalanceOnAnyAccountAfterWithdrawal() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(100);
        testAccount.withdraw(30);

        assertEquals(70, testAccount.checkBalanceOnAnyAccount(testAccount), 0.001);
    }

    @Test
    public void testcheckBalanceOnAnyAccountOtherAccount() {
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();
        second.deposit(250);

        assertEquals(250, first.checkBalanceOnAnyAccount(second), 0.001);
    }

    @Test
    public void testcheckBalanceOnAnyAccountNullAccount() {
        BankAccount testAccount = new BankAccount();

        assertThrows(IllegalArgumentException.class, () -> testAccount.checkBalanceOnAnyAccount(null));
    }
}