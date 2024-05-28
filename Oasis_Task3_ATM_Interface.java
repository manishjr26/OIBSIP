import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
{
        Scanner scanner = new Scanner(System.in);

        
        User user = new User("user123", "1234", 1000.0);

        System.out.println("Welcome to the ATM!");

       
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (user.authenticate(userId, pin))
 {
            System.out.println("Authentication successful!\n");

            
            while (true)
 {
                System.out.println("Choose an option:");
                System.out.println("1. Transactions History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");

                int option = scanner.nextInt();
                scanner.nextLine();  

                switch (option) 
{
                    case 1:
                        user.displayTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        user.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        user.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient's user ID: ");
                        String recipientUserId = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        user.transfer(recipientUserId, transferAmount);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM!");
                        return;
                    default:
                        System.out.println("Invalid option! Please choose again.");
                }
            }
        } else 
{
            System.out.println("Authentication failed! Please try again later.");
        }

        scanner.close();
    }
}

class User
 {
    private String userId;
    private String pin;
    private double balance;
    private TransactionHistory transactionHistory;

    public User(String userId, String pin, double balance)
 {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new TransactionHistory();
    }

    public boolean authenticate(String userId, String pin) 
{
        return this.userId.equals(userId) && this.pin.equals(pin);
    }

    public void displayTransactionHistory() 
{
        transactionHistory.display();
    }

    public void withdraw(double amount) 
{
        if (amount > 0 && amount <= balance) 
{
            balance -= amount;
            transactionHistory.addTransaction("Withdrawal", -amount);
            System.out.println("Withdrawal successful! Current balance: " + balance);
        } else 
{
            System.out.println("Invalid amount or insufficient funds!");
        }
    }

    public void deposit(double amount) 
{
        if (amount > 0) 
{
            balance += amount;
            transactionHistory.addTransaction("Deposit", amount);
            System.out.println("Deposit successful! Current balance: " + balance);
        } else 
{
            System.out.println("Invalid amount!");
        }
    }

    public void transfer(String recipientUserId, double amount) 
{
        if (amount > 0 && amount <= balance)
 {
            balance -= amount;
            transactionHistory.addTransaction("Transfer to " + recipientUserId, -amount);
            System.out.println("Transfer successful! Current balance: " + balance);
        } else 
{
            System.out.println("Invalid amount or insufficient funds!");
        }
    }
}

 class TransactionHistory 
{
    private StringBuilder history;

    public TransactionHistory() 
{
        this.history = new StringBuilder();
    }

    public void addTransaction(String transactionType, double amount) 
{
        history.append("[").append(transactionType).append(": ").append(amount).append("]\n");
    }

    public void display() 
{
        System.out.println("Transaction History:");
        System.out.println(history);
    }
}


