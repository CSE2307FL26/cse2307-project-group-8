package main;

import java.util.Scanner;

public class MainMenu {

    private static final int START_OPTIONS = 3;
    private static final int ACCOUNT_OPTIONS = 7;
    private static final double CHECKING_OVERDRAFT_LIMIT = 100;
    private static final double SAVINGS_INTEREST_RATE = 0.02;

    private Bank bank;
    private BankAccount userAccount;
    private Scanner keyboardInput;

    private boolean running = true;

    public MainMenu() {
        this.bank = new Bank();
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayStartOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        System.out.println("1. Log in");
        System.out.println("2. Create an account");
        System.out.println("3. Exit the app");
    }

    public void displayAccountOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdrawal");
        System.out.println("3. Check balance");
        System.out.println("4. View transaction history");
        if (userAccount instanceof SavingsAccount) {
            System.out.println("5. Apply interest");
        } else {
            System.out.println("5. View overdraft status");
        }
        System.out.println("6. Log out");
        System.out.println("7. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
            keyboardInput.nextLine();
        }
        return selection;
    }

    public void processStartInput(int selection) {
        switch (selection) {
            case 1:
                performLogin();
                break;
            case 2:
                performCreateAccount();
                break;
            case 3:
                running = false;
                break;
        }
    }

    public void processAccountInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdrawal();
                break;
            case 3:
                System.out.printf("Your balance is: %.2f%n", userAccount.getBalance());
                break;
            case 4:
                displayTransactionHistory();
                break;
            case 5:
                if (userAccount instanceof SavingsAccount) {
                    performApplyInterest();
                } else {
                    displayOverdraftStatus();
                }
                break;
            case 6:
                userAccount = null;
                System.out.println("Logged out.");
                break;
            case 7:
                running = false;
                break;
        }
    }

    public void performCreateAccount() {
        System.out.println("What type of account would you like?");
        System.out.println("1. Checking (overdraft up to " + CHECKING_OVERDRAFT_LIMIT + ")");
        System.out.println("2. Savings (" + (SAVINGS_INTEREST_RATE * 100) + "% interest)");
        BankAccount account;
        if (getUserSelection(2) == 1) {
            account = new CheckingAccount(CHECKING_OVERDRAFT_LIMIT);
        } else {
            account = new SavingsAccount(SAVINGS_INTEREST_RATE);
        }
        bank.addAccount(account);

        String username = null;
        while (username == null) {
            System.out.print("Choose a username: ");
            String input = keyboardInput.nextLine();
            try {
                if (bank.createUsername(input, account)) {
                    username = input;
                } else {
                    System.out.println("That username is taken.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        boolean passwordSet = false;
        while (!passwordSet) {
            System.out.print("Choose a password (at least 8 characters): ");
            String password = keyboardInput.nextLine();
            try {
                bank.createPassword(username, password);
                passwordSet = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Account created! You can now log in.");
    }

    public void performLogin() {
        System.out.print("Username: ");
        String username = keyboardInput.nextLine();
        System.out.print("Password: ");
        String password = keyboardInput.nextLine();

        if (bank.login(username, password)) {
            userAccount = bank.getAccount(username);
            System.out.println("Login successful!");
        } else {
            System.out.println("Incorrect username or password.");
        }
    }

    public void performDeposit() {
        System.out.print("How much would you like to deposit: ");
        double amount = keyboardInput.nextDouble();
        keyboardInput.nextLine();

        try {
            userAccount.deposit(amount);
            System.out.println("Deposit complete.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void performWithdrawal() {
        System.out.print("How much would you like to withdraw: ");
        double amount = keyboardInput.nextDouble();
        keyboardInput.nextLine();

        try {
            double newBalance = userAccount.withdraw(amount);
            System.out.printf("Withdrawal complete. New balance: %.2f%n", newBalance);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayTransactionHistory() {
        if (userAccount.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        for (Transaction transaction : userAccount.getTransactionHistory()) {
            System.out.println(transaction.getTimestamp() + " " + transaction.getType() + " " + transaction.getSignedAmount(userAccount));
        }
    }

    public void performApplyInterest() {
        SavingsAccount savings = (SavingsAccount) userAccount;
        double interest = savings.applyInterest();
        System.out.printf("Applied %.2f in interest. New balance: %.2f%n", interest, savings.getBalance());
    }

    public void displayOverdraftStatus() {
        CheckingAccount checking = (CheckingAccount) userAccount;
        System.out.printf("Overdraft limit: %.2f%n", checking.getOverdraftLimit());
        System.out.printf("Available to withdraw: %.2f%n", checking.getBalance() + checking.getOverdraftLimit());
        if (checking.isOverdrawn()) {
            System.out.println("Your account is currently overdrawn.");
        }
    }

    public void run() {
        while (running) {
            if (userAccount != null) {
                displayAccountOptions();
                processAccountInput(getUserSelection(ACCOUNT_OPTIONS));
            } else {
                displayStartOptions();
                processStartInput(getUserSelection(START_OPTIONS));
            }
        }
        System.out.println("Goodbye!");
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}
