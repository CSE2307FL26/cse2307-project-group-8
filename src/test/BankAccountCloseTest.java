package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountCloseTest {

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
}