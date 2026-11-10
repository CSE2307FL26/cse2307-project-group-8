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
    public void testTransactionHistory() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        testAccount.withdraw(30);
        testAccount.deposit(40);
        ArrayList<Double> expectedHistory = new ArrayList<Double>(Arrays.asList(50.0, -30.0, 40.0));
        assertEquals(testAccount.getTransactionHistory(), expectedHistory);
    }
}
