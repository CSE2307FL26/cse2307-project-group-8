package test;

import main.BankAccount;
import main.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    public void testGetters() {
        BankAccount from = new BankAccount();
        BankAccount to = new BankAccount();
        Transaction t = new Transaction(Transaction.Type.TRANSFER, 25, from, to);

        assertEquals(Transaction.Type.TRANSFER, t.getType());
        assertEquals(25, t.getAmount(), 0.01);
        assertEquals(from, t.getFrom());
        assertEquals(to, t.getTo());
    }

    @Test
    public void testTimestampIsCorrect() {
        LocalDateTime before = LocalDateTime.now();
        Transaction t = new Transaction(Transaction.Type.DEPOSIT, 10, null, new BankAccount());
        LocalDateTime after = LocalDateTime.now();

        assertFalse(t.getTimestamp().isBefore(before));
        assertFalse(t.getTimestamp().isAfter(after));
    }

    @Test
    public void testGetAmountForTransfer() {
        BankAccount from = new BankAccount();
        BankAccount to = new BankAccount();
        Transaction t = new Transaction(Transaction.Type.TRANSFER, 25, from, to);

        assertEquals(-25, t.getSignedAmount(from), 0.01);
        assertEquals(25, t.getSignedAmount(to), 0.01);
        assertEquals(0, t.getSignedAmount(new BankAccount()), 0.01);
    }

    @Test
    public void testInvalidTransactions() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> new Transaction(Transaction.Type.DEPOSIT, 0, null, account));
        assertThrows(IllegalArgumentException.class, () -> new Transaction(null, 10, null, account));
        assertThrows(IllegalArgumentException.class, () -> new Transaction(Transaction.Type.DEPOSIT, 10, null, null));
    }

    @Test
    public void testDepositAndWithdrawalRecordParties() {
        BankAccount account = new BankAccount();
        account.deposit(40);
        account.withdraw(15);

        Transaction deposit = account.getTransactionHistory().get(0);
        assertNull(deposit.getFrom());
        assertEquals(account, deposit.getTo());

        Transaction withdrawal = account.getTransactionHistory().get(1);
        assertEquals(account, withdrawal.getFrom());
        assertNull(withdrawal.getTo());
    }
}
