package ATM;

import java.util.ArrayList;

//ATMSystem class
//an ATMSystem has an ArrayList that stores objects(customers)
public class ATMSystem {
	
	private static ArrayList<Customer> customers; 
	
	//initializing an ATMSystem
	public ATMSystem(){
		customers = new ArrayList<Customer>();
	}
	
	//add a customer to ATMSystem
	public void addCustomer(Customer c){
		if (customers.contains(c)) {

		}

		else{
			customers.add(c);
		}
		
	}
	
	//get a customer according to a card number
	public Customer getCustomer(String c){
		
		Customer result = null;
		//search a customer according to a card number
		for(int i =0 ; i< customers.size(); i++){
			if(customers.get(i).getCardNumber().equals(c))
				return customers.get(i);
		}
		
		return result;
		
		
	}
	
	//check if it is a valid account
	public static boolean isCorrectAccount(String cardNumber, String password){
		boolean result = false;
		
		//match for a cardnumber according to a given password
		for(int i =0 ; i< customers.size(); i++){
			if(customers.get(i).getCardNumber().equals(cardNumber) && customers.get(i).getPassword().equals(password) )
				return true;
		}
		
		return false;
	}
	
}
