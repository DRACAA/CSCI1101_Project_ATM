package ATM;

import java.util.Date;

import javax.swing.JOptionPane;

//customer class
public class Customer {
    private String customerName;
    private String cardNumber;
    private String password;

    //every customer has 2 accounts: chequing account and saving accounts
    private Account SavingAccount;
    private Account ChequingAccount;

    //a String that record operations for a receipts
    private String receipt = "";

    //creating a customer, set customer name and card number ,and its password. Also create 2 accounts for this customers
    public Customer(String customerName, String cardNumber, String password) {
        this.setCustomerName(customerName);
        this.cardNumber = cardNumber;
        this.setPassword(password);
        SavingAccount = new Account("Saving");
        ChequingAccount = new Account("Chequing");

    }

    //when a custom login, then reset the receipt
    public void login() {
        receipt = "\tReceipt Details\n---------------------------------\n";
        receipt += "Card Number: " + cardNumber + "\nCustomer Name: " + customerName + "\n\n";
    }

    //transferFrom account a to account b
    public void transferFrom(Account a, Account b, double amount) {
        //error check, if the account balance is less than the amount, show a message dialog.
        if (a.getBalance() < amount) {
            JOptionPane.showMessageDialog(null, "You don't have $" + amount + " in " + a.getAccountName() + " account.");
            return;
        } else {
            //do the transfer operations..
            JOptionPane.showMessageDialog(null, "Transaction successful.\nYou have transferred $" + amount + " from " + a.getAccountName() + " account to" + b.getAccountName() + " account.");
            a.setBalance(a.getBalance() - amount);
            b.setBalance(b.getBalance() + amount);
            //after a successful operation, record it to receipt
            setReceipt(getReceipt() + "Transfer \t-$" + amount + " " + a.getAccountName() + "\n");
            setReceipt(getReceipt() + "Transfer \t+$" + amount + " " + b.getAccountName() + "\n");
        }

    }

    //deposit an amount of money to an account
    public void deposit(Account a, double amount) {

        //doing deposit operation
        JOptionPane.showMessageDialog(null, "Deposit successful. You have deposited $" + amount + " to your " + a.getAccountName() + " account.");
        a.setBalance(a.getBalance() + amount);
        //after a successful operation, record it to receipt
        setReceipt(getReceipt() + "Deposit \t+$" + amount + " " + a.getAccountName() + "\n");


    }

    public void withdraw(Account a, double amount) {
        //error check, if the account balance is less than the amount, show a message dialog.
        if (a.getBalance() < amount) {
            JOptionPane.showMessageDialog(null, "You don't have $" + amount + " in " + a.getAccountName() + " account.");
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Withdraw $" + amount + " from your " + a.getAccountName() + " successful.");
            //after a successful operation, record it to receipt
            setReceipt(getReceipt() + "Withdraw \t-$" + amount + " " + a.getAccountName() + "\n");

            //if withdraw from a saving account, charge extra 2 dollars
            if (a.getAccountName().equals("Saving")) {
                amount += 2;
                setReceipt(getReceipt() + "Service Charge\t-$" + 2.00 + " " + a.getAccountName() + "\n");
            }

            a.setBalance(a.getBalance() - amount);
        }


    }


    //get and set methods

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Account getSavingAccount() {
        return SavingAccount;
    }

    public void setSavingAccount(Account savingAccount) {
        SavingAccount = savingAccount;
    }


    public Account getChequingAccount() {
        return ChequingAccount;
    }

    public void setChequingAccount(Account chequingAccount) {
        ChequingAccount = chequingAccount;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getReceipt() {
        return receipt;
    }

    //get a final receipt when a customer asks for a receipt
    public String getFinalReceipt() {
        receipt += "\nBalance:\tChequing $" + getChequingAccount() + "CAD\n";
        receipt += "\t\t\tSaving   $" + getSavingAccount() + "CAD\n";
        receipt += "---------------------------------\n";
        receipt += "\nThank you for using Dalhousie ATM!\n";
        return receipt += (new Date());

    }


    //toString method
    public String toString() {
        return customerName;
    }


}
