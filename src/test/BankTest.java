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
        assertEquals(testBank.getAccounts().size(), 3, 0.01);
    }
}
