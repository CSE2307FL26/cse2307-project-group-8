package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.*;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(-50));
    }

    @Test
    public void testTransferMovesMoneyBetweenAccounts() {
        BankAccount source = new BankAccount();
        BankAccount destination = new BankAccount();
        source.deposit(100);

        source.transferTo(destination, 35);

        assertEquals(65, source.getBalance(), 0.01);
        assertEquals(35, destination.getBalance(), 0.01);
    }

    @Test
    public void testTransferWithInsufficientFundsDoesNotChangeEitherAccount() {
        BankAccount source = new BankAccount();
        BankAccount destination = new BankAccount();
        source.deposit(20);
        destination.deposit(10);

        assertThrows(IllegalStateException.class, () -> source.transferTo(destination, 30));

        assertEquals(20, source.getBalance(), 0.01);
        assertEquals(10, destination.getBalance(), 0.01);
    }

    @Test
    public void testCannotTransferToSameAccount() {
        BankAccount account = new BankAccount();
        account.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> account.transferTo(account, 10));

        assertEquals(50, account.getBalance(), 0.01);
    }

    @Test
    public void testCannotTransferInvalidAmount() {
        BankAccount source = new BankAccount();
        BankAccount destination = new BankAccount();
        source.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> source.transferTo(destination, -10));
        assertThrows(IllegalArgumentException.class, () -> source.transferTo(destination, 0));

        assertEquals(50, source.getBalance(), 0.01);
        assertEquals(0, destination.getBalance(), 0.01);
    }

    @Test
    public void testNewAccountIsNotClosed() {
        BankAccount account = new BankAccount();

        assertFalse(account.isClosed());
    }

    @Test
    public void testCanCloseAccountWithZeroBalance() {
        BankAccount account = new BankAccount();

        account.close();

        assertTrue(account.isClosed());
    }

    @Test
    public void testCannotCloseAccountWithMoneyInIt() {
        BankAccount account = new BankAccount();
        account.deposit(25);

        assertThrows(IllegalStateException.class, account::close);

        assertFalse(account.isClosed());
        assertEquals(25, account.getBalance(), 0.01);
    }

    @Test
    public void testClosedAccountCannotReceiveDeposit() {
        BankAccount account = new BankAccount();
        account.close();

        assertThrows(IllegalStateException.class, () -> account.deposit(10));

        assertEquals(0, account.getBalance(), 0.01);
    }

    @Test
    public void testClosedAccountCannotSendTransfer() {
        BankAccount source = new BankAccount();
        BankAccount destination = new BankAccount();
        source.close();

        assertThrows(IllegalStateException.class, () -> source.transferTo(destination, 10));

        assertEquals(0, destination.getBalance(), 0.01);
    }

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
