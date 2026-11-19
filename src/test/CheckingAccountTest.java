package test;

import main.BankAccount;
import main.CheckingAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CheckingAccountTest {

    @Test
    public void testNegativeOverdraftLimitThrows() {
        assertThrows(IllegalArgumentException.class, () -> new CheckingAccount(-50));
    }

    @Test
    public void testWithdrawIntoOverdraft() {
        CheckingAccount account = new CheckingAccount(100);
        account.deposit(50);
        account.withdraw(120);

        assertEquals(-70, account.getBalance(), 0.01);
        assertTrue(account.isOverdrawn());
        assertEquals(Transaction.Type.WITHDRAWAL, account.getTransactionHistory().get(1).getType());
    }

    @Test
    public void testWithdrawPastOverdraftLimit() {
        CheckingAccount account = new CheckingAccount(100);
        account.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(151));
        assertEquals(50, account.getBalance(), 0.01);
    }

    @Test
    public void testTransferIntoOverdraft() {
        CheckingAccount source = new CheckingAccount(100);
        BankAccount destination = new BankAccount();
        source.deposit(20);

        source.transferTo(destination, 70);

        assertEquals(-50, source.getBalance(), 0.01);
        assertEquals(70, destination.getBalance(), 0.01);
    }

    @Test
    public void testTransferPastOverdraftLimit() {
        CheckingAccount source = new CheckingAccount(100);
        BankAccount destination = new BankAccount();
        source.deposit(20);

        assertThrows(IllegalStateException.class, () -> source.transferTo(destination, 121));
        assertEquals(20, source.getBalance(), 0.01);
        assertEquals(0, destination.getBalance(), 0.01);
    }

    @Test
    public void testCannotCloseOverdrawnAccount() {
        CheckingAccount account = new CheckingAccount(100);
        account.withdraw(20);

        assertThrows(IllegalStateException.class, () -> account.close());
    }
}
