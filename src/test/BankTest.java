package test;

import main.BankAccount;
import main.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.util.*;

public class BankTest {

    @Test
    public void testCreateAccount() {
        Bank testBank = new Bank();
        testBank.createAccount();
        testBank.createAccount();
        testBank.createAccount();
        assertEquals(testBank.getAccounts().size(), 3);
    }

    @Test
    public void testAccountInteraction() {
        Bank testBank = new Bank();
        testBank.createAccount();
        testBank.createAccount();
        assertEquals(testBank.getAccounts().size(), 2);

        BankAccount account1 = testBank.getAccounts().get(0);
        BankAccount account2 = testBank.getAccounts().get(1);
        account1.deposit(20);
        account1.transferTo(account2, 10);
        assertEquals(account1.getBalance(), 10, 0.01);
        assertEquals(account2.getBalance(), 10, 0.01);
    }

    @Test
    public void testCreateUsernameSuccess() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount();
        bank.addAccount(account);

        assertTrue(bank.createUsername("sam123", account));
    }

    @Test
    public void testCreateUsernameAlreadyTaken() {
        Bank bank = new Bank();
        BankAccount first = new BankAccount();
        BankAccount second = new BankAccount();
        bank.addAccount(first);
        bank.addAccount(second);

        bank.createUsername("sam123", first);

        assertFalse(bank.createUsername("sam123", second));
    }

    @Test
    public void testCreateUsernameBlankThrows() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount();
        bank.addAccount(account);

        assertThrows(IllegalArgumentException.class, () -> bank.createUsername("   ", account));
    }

    // createPassword tests

    @Test
    public void testCreatePasswordSuccess() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount();
        bank.addAccount(account);
        bank.createUsername("sam123", account);

        assertDoesNotThrow(() -> bank.createPassword("sam123", "securePass1"));
    }

    @Test
    public void testCreatePasswordWithoutUsernameThrows() {
        Bank bank = new Bank();

        assertThrows(IllegalArgumentException.class, () -> bank.createPassword("nobody", "securePass1"));
    }

    @Test
    public void testCreatePasswordTooShortThrows() {
        Bank bank = new Bank();
        BankAccount account = new BankAccount();
        bank.addAccount(account);
        bank.createUsername("sam123", account);

        assertThrows(IllegalArgumentException.class, () -> bank.createPassword("sam123", "short"));
    }

    @Test
    public void testGetAccountByUsername() {
        Bank testBank = new Bank();
        BankAccount account = new BankAccount();
        testBank.addAccount(account);
        testBank.createUsername("alice", account);

        assertTrue(testBank.getAccount("alice") == account);
        assertTrue(testBank.getAccount("nobody") == null);
    }
}
