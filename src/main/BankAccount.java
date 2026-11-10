package main;

public class BankAccount {

    private double balance;
    private boolean closed;

    public BankAccount() {
        this.balance = 0;
        this.closed = false;
    }

    public void deposit(double amount) {
        if(closed) {
            throw new IllegalStateException();
        }
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
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
}