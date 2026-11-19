package main;

public class SavingsAccount extends BankAccount {

    private double interestRate;

    public SavingsAccount(double interestRate) {
        super();
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public double applyInterest() {
        double interest = this.balance * interestRate;
        if (interest > 0) {
            this.balance += interest;
            this.transactionHistory.add(new Transaction(Transaction.Type.INTEREST, interest, null, this));
        }
        return interest;
    }

}
