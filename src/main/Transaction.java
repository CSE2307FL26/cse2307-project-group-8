package main;

import java.time.LocalDateTime;

public class Transaction {

    public enum Type {DEPOSIT, WITHDRAWAL, TRANSFER, INTEREST}

    private final Type type;
    private final double amount;
    private final BankAccount from;
    private final BankAccount to;
    private final LocalDateTime timestamp;

    public Transaction(Type type, double amount, BankAccount from, BankAccount to) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be positive");
        }
        if (from == null && to == null) {
            throw new IllegalArgumentException("Transaction must involve at least one account");
        }
        this.type = type;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.timestamp = LocalDateTime.now();
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public BankAccount getFrom() {
        return from;
    }

    public BankAccount getTo() {
        return to;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getSignedAmount(BankAccount account) {
        if (account == to) {
            return amount;
        }
        if (account == from) {
            return -amount;
        }
        return 0;
    }

    @Override
    public String toString() {
        return timestamp + " " + type + " " + String.format("%.2f", amount);
    }
}
