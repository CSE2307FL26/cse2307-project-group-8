package test;

import main.SavingsAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SavingsAccountTest {

    @Test
    public void testNegativeInterestRate() {
        assertThrows(IllegalArgumentException.class, () -> new SavingsAccount(-0.01));
    }

    @Test
    public void testApplyInterest() {
        SavingsAccount account = new SavingsAccount(0.05);
        account.deposit(100);

        assertEquals(5, account.applyInterest(), 0.01);
        assertEquals(105, account.getBalance(), 0.01);
    }

    @Test
    public void testApplyInterestCompounds() {
        SavingsAccount account = new SavingsAccount(0.10);
        account.deposit(100);

        account.applyInterest();
        account.applyInterest();

        assertEquals(121, account.getBalance(), 0.01);
    }

    @Test
    public void testApplyInterestRecord() {
        SavingsAccount account = new SavingsAccount(0.10);
        account.deposit(200);

        account.applyInterest();

        Transaction interest = account.getTransactionHistory().get(1);
        assertEquals(Transaction.Type.INTEREST, interest.getType());
        assertEquals(20, interest.getAmount(), 0.01);
    }

    @Test
    public void testApplyInterestOnEmptyAccount() {
        SavingsAccount account = new SavingsAccount(0.05);

        assertEquals(0, account.applyInterest(), 0.01);
        assertTrue(account.getTransactionHistory().isEmpty());
    }

    @Test
    public void testCannotOverdraw() {
        SavingsAccount account = new SavingsAccount(0.05);
        account.deposit(50);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(51));
        assertEquals(50, account.getBalance(), 0.01);
    }
}
