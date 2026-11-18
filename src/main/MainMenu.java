package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 3;
	private static final int MAX_SELECTION = 3;

    private Bank bank;
    private BankAccount userAccount;
    private Scanner keyboardInput;

    private boolean accountSetUp = false;   
    private boolean loggedIn = false;
    private boolean running = true;

    public MainMenu() {
        this.userAccount = new BankAccount();
        this.bank = new Bank();
        this.bank.addAccount(userAccount);   
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayStartOptions() {
        System.out.println("\nWelcome to the 237 Bank App!");
        if (accountSetUp) {
            System.out.println("1. Log in");
        } else {
            System.out.println("1. Create a username and password");
        }
        System.out.println("2. Exit the app");
    }

    public void displayAccountOptions() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Make a deposit");
        System.out.println("2. Make a withdrawal");
        System.out.println("3. Check balance");
        System.out.println("4. Log out");
        System.out.println("5. Exit the app");
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
                if (accountSetUp) {
                    performLogin();
                } else {
                    performLoginSetup();
                }
                break;
            case 2:
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
                System.out.println("Your balance is: " + userAccount.getBalance());
                break;
            case 4:
                loggedIn = false;
                System.out.println("Logged out.");
                break;
            case 5:
                running = false;
                break;
        }
    }

    public void performLoginSetup() {
        System.out.print("Choose a username: ");
        String username = keyboardInput.nextLine();
        try {
            if (!bank.createUsername(username, userAccount)) {
                System.out.println("That username is taken, or this account already has one.");
                return;
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
            return;
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
        accountSetUp = true;
        System.out.println("Username and password created! You can now log in.");
    }

    public void performLogin() {
        System.out.print("Username: ");
        String username = keyboardInput.nextLine();
        System.out.print("Password: ");
        String password = keyboardInput.nextLine();

        if (bank.login(username, password)) {
            loggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Incorrect username or password.");
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
            keyboardInput.nextLine();
        }
        userAccount.deposit(depositAmount);
        System.out.println("Deposit complete.");
    }

    public void performWithdrawal() {
        System.out.print("How much would you like to withdraw: ");
        double amount = keyboardInput.nextDouble();
        keyboardInput.nextLine();

        try {
            double newBalance = userAccount.withdraw(amount);
            System.out.println("Withdrawal complete. New balance: " + newBalance);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        while (running) {
            if (loggedIn) {
                displayAccountOptions();
                processAccountInput(getUserSelection(5));
            } else {
                displayStartOptions();
                processStartInput(getUserSelection(2));
            }
        }
        System.out.println("Goodbye!");
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }
}