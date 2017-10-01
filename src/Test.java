package ATM;

public class Test {

    public static void main(String[] args) {
        Customer john = new Customer("John", "9999", "1234");

        john.getChequingAccount().setBalance(500);


        ATMSystem ATM = new ATMSystem();

        ATM.addCustomer(john);
        john.login();


        john.transferFrom(john.getChequingAccount(), john.getSavingAccount(), 200);

        System.out.println("John's chequing account " + john.getChequingAccount());


        System.out.println(john.getFinalReceipt());

        System.out.println(ATM.getCustomer("9999").getChequingAccount());

    }


}
