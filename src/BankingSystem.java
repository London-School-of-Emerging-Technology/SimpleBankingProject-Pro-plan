import java.io.*;
import java.util.*;

public class BankingSystem {
    private static final String FILE_NAME = "accounts.dat";
    private static List<BankAccount> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadAccounts();
        while (true) {
            System.out.println("\n--- Banking System Menu ---");
            System.out.println("1. Create new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Check balance");
            System.out.println("5. Search account");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                int option = Integer.parseInt(choice);
                switch (option) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        depositMoney();
                        break;
                    case 3:
                        withdrawMoney();
                        break;
                    case 4:
                        checkBalance();
                        break;
                    case 5:
                        searchAccount();
                        break;
                    case 6:
                        saveAccounts();
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accNumber = scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String accHolderName = scanner.nextLine();
        BankAccount account = new BankAccount(accNumber, accHolderName);
        accounts.add(account);
        System.out.println("Account created successfully.");
    }

    private static void depositMoney() {
        BankAccount account = getAccount();
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double depositAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline left-over
            account.deposit(depositAmount);
        }
    }

    private static void withdrawMoney() {
        BankAccount account = getAccount();
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double withdrawalAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline left-over
            account.withdraw(withdrawalAmount);
        }
    }

    private static void checkBalance() {
        BankAccount account = getAccount();
        if (account != null) {
            account.checkBalance();
        }
    }

    private static void searchAccount() {
        System.out.println("Search by:");
        System.out.println("1. Account Number");
        System.out.println("2. Account Holder Name");
        System.out.print("Choose an option: ");

        String searchChoice = scanner.nextLine();

        try {
            int option = Integer.parseInt(searchChoice);
            if (option == 1) {
                System.out.print("Enter account number: ");
                String accNumber = scanner.nextLine();
                for (BankAccount account : accounts) {
                    if (account.getAccountNumber().equals(accNumber)) {
                        account.checkBalance();
                        return;
                    }
                }
                System.out.println("Account not found.");
            } else if (option == 2) {
                System.out.print("Enter account holder name: ");
                String accHolderName = scanner.nextLine();
                for (BankAccount account : accounts) {
                    if (account.getAccountHolderName().equalsIgnoreCase(accHolderName)) {
                        account.checkBalance();
                        return;
                    }
                }
                System.out.println("Account not found.");
            } else {
                System.out.println("Invalid option.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private static BankAccount getAccount() {
        System.out.print("Enter account number: ");
        String accNumber = scanner.nextLine();
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accNumber)) {
                return account;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    private static void loadAccounts() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                accounts = (List<BankAccount>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading accounts: " + e.getMessage());
            }
        }
    }
}