package ATM;

//account class
public class Account {

    private String accountName;
    private double balance;

    //initializing an account
    public Account(String accountName) {
        setAccountName(accountName);
        setBalance(0);
    }

    //get and set method
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //toString method
    public String toString() {
        String result = "" + balance;
        return result;
    }


}
