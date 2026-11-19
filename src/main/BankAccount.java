package main;
import java.util.*;

public class BankAccount {

    protected double balance;
    private boolean closed;
    protected ArrayList<Transaction> transactionHistory;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if(closed) {
            throw new IllegalStateException();
        }
        if(amount > 0) {
            this.balance += amount;
            transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, amount, null, this));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void transferTo(BankAccount destination, double amount) {
        if(destination == this || amount <= 0) {
            throw new IllegalArgumentException();
        }
        if(amount > this.balance) {
            throw new IllegalStateException();
        }
        Transaction transfer = new Transaction(Transaction.Type.TRANSFER, amount, this, destination);
        this.balance -= amount;
        this.transactionHistory.add(transfer);
        destination.balance += amount;
        destination.transactionHistory.add(transfer);
    }

    public void close() {
        if(balance != 0) {
            throw new IllegalStateException();
        }
        this.closed = true;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public double getBalance() {
        return this.balance;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return this.transactionHistory;
    }

    public double withdraw(double withdrawValue){
        if (withdrawValue <= 0 || withdrawValue > this.balance) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        this.balance -= withdrawValue;
        this.transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, withdrawValue, this, null));
        return this.balance;
    }

    public double checkBalanceOnAnyAccount(BankAccount account){
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        return account.getBalance();
    }

}