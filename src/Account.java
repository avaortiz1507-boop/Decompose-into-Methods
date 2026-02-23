// Base Account class - parent class for all accounts
public class Account {
    private String owner;
    protected double balance;
    private InterestStrategy interestStrategy; // Composition: Account HAS-A strategy

    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    // Allows changing the interest strategy at runtime
    public void setInterestStrategy(InterestStrategy s) {
        this.interestStrategy = s;
    }

    // Apply interest using the current strategy
    public void applyInterest() {
        if (interestStrategy != null) {
            double interest = interestStrategy.calculate(balance);
            balance += interest;
        }
    }

    public String getOwner() {
        return owner;
    }

    // Common deposit behavior - called by all subclasses
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            onTransaction("Deposit", amount);
        }
    }

    // Common withdraw behavior - can be overridden by subclasses
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            onTransaction("Withdraw", amount);
        }
    }

    public double getBalance() {
        return balance;
    }

    // Protected hook method - subclasses can override to add custom behavior
    protected void onTransaction(String type, double amount) {
        // Default: do nothing. Subclasses override this.
    }

    // Strategy interface - different interest calculation methods
    public static interface InterestStrategy {
        double calculate(double balance);
    }

    // Concrete Strategy 1: Simple flat-rate interest
    public static class SimpleInterest implements InterestStrategy {
        private double rate;

        public SimpleInterest(double rate) {
            this.rate = rate;
        }

        @Override
        public double calculate(double balance) {
            return balance * rate;
        }
    }

    // Concrete Strategy 2: Interest rates vary by balance amount
    public static class TieredInterest implements InterestStrategy {
        @Override
        public double calculate(double balance) {
            if (balance >= 5000) {
                return balance * 0.05; // 5% for high balances
            } else if (balance >= 1000) {
                return balance * 0.03; // 3% for medium balances
            } else {
                return balance * 0.01; // 1% for low balances
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("\n========== DEMO 1: Polymorphism ==========\n");
        System.out.println("Creating an ArrayList that holds different account types...");
        System.out.println("Both SavingsAccount and CheckingAccount ARE-A Account.\n");

        // Polymorphism: ArrayList holds parent type, but contains child objects
        java.util.ArrayList<Account> accounts = new java.util.ArrayList<>();
        accounts.add(new SavingsAccount("Alice", 1000, 0.05, 3));
        accounts.add(new CheckingAccount("Bob", 500, 2.50, 100));

        // Same method calls, different behavior (polymorphism in action)
        for (Account acc : accounts) {
            System.out.println(acc.getOwner() + " starts with: $" + acc.getBalance());
            acc.deposit(200);
            acc.withdraw(100);
            System.out.println(acc.getOwner() + " now has: $" + acc.getBalance());
            System.out.println();
        }

        System.out.println("========== DEMO 2: Strategy Pattern ==========\n");
        System.out.println("Creating an account and swapping strategies at runtime...\n");

        // Create a plain account
        Account account = new Account("Charlie", 1000);

        // Strategy 1: Simple Interest
        System.out.println("Using SimpleInterest strategy:");
        account.setInterestStrategy(new SimpleInterest(0.05));
        System.out.println("Before: $" + account.getBalance());
        account.applyInterest();
        System.out.println("After: $" + account.getBalance() + "\n");

        // Strategy 2: Switch strategy without changing Account class
        System.out.println("Switching to TieredInterest strategy:");
        account.setInterestStrategy(new TieredInterest());
        System.out.println("Before: $" + account.getBalance());
        account.applyInterest();
        System.out.println("After: $" + account.getBalance());

        System.out.println("\nNote: We changed strategies without modifying the Account class.");
        System.out.println("This is the Open/Closed Principle - open for extension, closed for modification.");
    }
}

class SavingsAccount extends Account implements Account.InterestStrategy {
    private double interestRate;
    private int withdrawalLimit;
    private int withdrawalsThisMonth;

    public SavingsAccount(String owner, double balance, double interestRate, int withdrawalLimit) {
        super(owner, balance);
        this.interestRate = interestRate;
        this.withdrawalLimit = withdrawalLimit;
        this.withdrawalsThisMonth = 0;
    }

    public void applyInterest() {
        double interest = calculate(balance);
        balance += interest;
    }

    @Override
    public double calculate(double balance) {
        return balance * interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (withdrawalsThisMonth < withdrawalLimit) {
            super.withdraw(amount);
            withdrawalsThisMonth++;
        }
    }

    public void resetMonthlyLimit() {
        withdrawalsThisMonth = 0;
    }

    @Override
    protected void onTransaction(String type, double amount) {
        if (type.equals("Withdraw")) {
            System.out.println(
                    "Withdrawal made. Withdrawals remaining this month: " + (withdrawalLimit - withdrawalsThisMonth));
        }
    }
}

class CheckingAccount extends Account {
    private double transactionFee;
    private double overdraftLimit;

    public CheckingAccount(String owner, double balance, double transactionFee, double overdraftLimit) {
        super(owner, balance);
        this.transactionFee = transactionFee;
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            onTransaction("Withdraw", amount);
        }
    }

    @Override
    protected void onTransaction(String type, double amount) {
        if (type.equals("Withdraw")) {
            balance -= transactionFee;
            System.out.println("Withdrawal fee applied: " + transactionFee);
        }
    }
}
