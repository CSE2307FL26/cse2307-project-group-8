package main;
import java.util.*;

public class Bank {

    private ArrayList<BankAccount> accounts;
    private HashMap<String, BankAccount> usernames = new HashMap<>();   // username -> account
    private HashMap<String, String> passwords = new HashMap<>(); 

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

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public boolean createUsername(String username, BankAccount account) {
        if (accounts.isEmpty()) {
            throw new IllegalStateException("No accounts exist yet");
        }
        if (account == null || !accounts.contains(account)) {
            throw new IllegalArgumentException("Account not found");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (usernames.containsKey(username) || usernames.containsValue(account)) {
            return false; // username taken, or this account already has one
        }
        usernames.put(username, account);
        return true;
    }

    public void createPassword(String username, String password) {
        if (!usernames.containsKey(username)) {
            throw new IllegalArgumentException("Create a username first");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        passwords.put(username, password);
    }

    public BankAccount getAccount(String username) {
        return usernames.get(username);
    }

    public boolean login(String username, String password) {
    return passwords.containsKey(username) && passwords.get(username).equals(password);
    }
}
