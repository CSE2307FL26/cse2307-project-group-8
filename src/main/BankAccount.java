package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
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
        this.balance -= amount;
        destination.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }
}