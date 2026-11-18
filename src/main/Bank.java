package main;
import java.util.*;

public class Bank {

    private ArrayList<BankAccount> accounts;

    public Bank() {
        this.accounts = new ArrayList<BankAccount>();
    }

    public void createAccount() {
        BankAccount newAccount = new BankAccount();
        accounts.add(newAccount);
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}
