package main;
import java.util.*;

public class BankAccount {

    private double balance;
    private ArrayList<Double> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.transactionHistory = new ArrayList<Double>();
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
            transactionHistory.add(amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    public ArrayList<Double> getTransactionHistory() {
        return this.transactionHistory;
    }
}
