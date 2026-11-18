package test;

import main.BankAccount;
import main.Bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
}
