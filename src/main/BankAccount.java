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

    public double getBalance() {
        return this.balance;
    }

    public double withdraw(double withdrawValue){
    if (withdrawValue <= 0 || withdrawValue > this.balance) {
        throw new IllegalArgumentException("Invalid withdrawal amount");
    }
    this.balance -= withdrawValue;
    return this.balance;
    }

    public double checkBalanceOnAnyAccount(BankAccount account){
    if (account == null) {
        throw new IllegalArgumentException("Account cannot be null");
    }
    return account.getBalance();
    }

}
