package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BankAccountTransferTest {

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
}