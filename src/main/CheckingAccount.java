package main;

public class CheckingAccount extends BankAccount {

    private double overdraftLimit;

    public CheckingAccount(double overdraftLimit) {
        super();
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return this.overdraftLimit;
    }

    public boolean isOverdrawn() {
        return this.balance < 0;
    }

    @Override
    public double withdraw(double withdrawValue) {
        if (isClosed()) {
            throw new IllegalStateException("Account is closed");
        }
        if (withdrawValue <= 0 || this.balance - withdrawValue < -overdraftLimit) {
            throw new IllegalArgumentException("Invalid withdrawal amount");
        }
        this.balance -= withdrawValue;
        this.transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, withdrawValue, this, null));
        return this.balance;
    }

    @Override
    public void transferTo(BankAccount destination, double amount) {
        if (destination == this || amount <= 0) {
            throw new IllegalArgumentException();
        }
        if (isClosed() || this.balance - amount < -overdraftLimit) {
            throw new IllegalStateException();
        }
        Transaction transfer = new Transaction(Transaction.Type.TRANSFER, amount, this, destination);
        this.balance -= amount;
        this.transactionHistory.add(transfer);
        destination.balance += amount;
        destination.transactionHistory.add(transfer);
    }

}
