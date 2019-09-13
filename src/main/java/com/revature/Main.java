package com.revature;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Logger;
// ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF // Didn't see these as options, saw...
// warring , info , severe
// config, finest, finer, fine,

// TODO: Set up database
// TODO: Set up exceptions
// TODO: Change this to permit multiple users
// TODO: quit making things static and start using classes and objects
// TODO: make classes/objects, ...
// https://www.tutorialspoint.com/java/util/scanner_hasnextbigdecimal.htm

/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {
	
	// make sure the logs from this class are associated with this class
	public static Logger logger = Logger.getLogger(Main.class.getName()); // TODO: learn this syntax better
	
	// lazily making everything static even if it shouldn't be...
	static BigDecimal balance = new BigDecimal(0);

	static String ownerName = "I";
	static String ownerPassword = "-";
	
	// TODO: add log in screen
	static void showActionMenu() {
		logger.info("in showActionMenu");
		System.out.println("Welcome, What would you like to do?");
		System.out.println("");
		System.out.println("V: View balance");
		System.out.println("W: Withdraw money");
		System.out.println("D: Deposit money");
		System.out.println("Q: Quit");
		System.out.println("Your balance is currently " + balance);
	}
	static void showWithdrawMenu() {
		System.out.println("Withdrawing from a a balance of " + balance);
		System.out.println("Enter amount to withdraw: ");
	}
	static void showDepositMenu() {
		System.out.println("Depositing money into an account that has a balance of " + balance);
		System.out.println("Enter amount to deposit: ");
	}
	static void showBalance() {
		System.out.println(balance);
	}
	
	static BigDecimal getMoneyDelta() {
		int attempts = 0;
		int attemptLimit = 3;
		while(attempts<attemptLimit) {
			if(sc1.hasNextBigDecimal()) // wc.hasNext() &&
			{ 
				BigDecimal amount = sc1.nextBigDecimal();
				if(amount.compareTo(BigDecimal.ZERO) > 0) {
					return amount;
				}
				else {
					System.out.println("Number needs to be >= 0");
					attempts++;
				}
			}
			else {
				System.out.println("Input error, Try again"); // aka It wasn't a BigDouble
				attempts++;
			}
		}
		return new BigDecimal(0);
	}
	
	
	static void ActionMenuInputProcessing() {
		logger.info("in ActionMenuProcessing");
		//System.out.println("ActionMenuInputProcessing() running");
		int attempt_limit = 3;
		int attempts = 0;
		String userInput;
		BigDecimal delta; 

		if(attempts < attempt_limit){
			userInput = sc1.next();
			System.out.println("Inside if statement");
			switch(userInput.toUpperCase()) {
			case("V"):
				showBalance();
				break;
			
			// TODO: for deposit/withdraw add data to database
			
			case("W"):
				showDepositMenu();
				delta = getMoneyDelta();
			
				balance = balance.add(delta); // TODO: combine with Deposit method. // TODO: check if result is <0 or >=0 ...
				showBalance();
				//System.out.println("The balance is now: " + balance);

				break;
			case("D"):
				showWithdrawMenu();
			// https://www.tutorialspoint.com/java/util/scanner_hasnextbigdecimal.htm
				delta = getMoneyDelta(); 
				// verifies result won't be negative...
				if(balance.subtract(delta).compareTo(BigDecimal.ZERO)>=0) {
					balance = balance.subtract(delta);
				}
				else {
					logger.warning("input would have created a negative balance");
				}
			break;
			case("Q"):
				System.out.println("Thanks, session was sucessful");
				break;
			default:
				System.out.println("What happened?, try again?"); // TODO: an actual try again...
				attempts++;
				break;
			}
		// replay menu then run this again...
		}
	}
	
	static Scanner sc1 = new Scanner(System.in);
	
//    class InvalidNewBalanceException extends Exception{  
//        InvalidNewBalanceException(String s){  
//         super(s); 
//        }  
//       }  
	
	static boolean loginMenu() {//; ////throws InvalidNewBalanceException{
		logger.info("in loginMenu");
		int attempt_limit = 3;
		int attempts = 0;
		String userName = null;
		String userPassword = null;
		
		System.out.println("Type your user name");
		while(sc1.hasNext() && attempts < attempt_limit){
			userName = sc1.next().trim();
			System.out.println("Type your password");
			if(sc1.hasNext()) {
				userPassword = sc1.next().trim();
			}
			System.out.println("UserName: " + userName);
			System.out.println("Password: " + userPassword);
			if(userName.contentEquals(ownerName) && userPassword.contentEquals(ownerPassword)) {
				System.out.println("valid username and password");
				//sc1.close();
				return true;
				//showActionMenu(); // probably too much coupling(spelling?), wish I had paper...
				//ActionMenuInputProcessing();
				//break;
			}
			else {
				attempts++;
				if(attempts >= attempt_limit) { // TODO: verify not off by one
					// throw new InvalidNewBalanceException("New balance is too small"); // This vs. returning false :(
					System.out.println("Password attempt limit exceeded, feel free to try later...");
					//sc1.close();
					return false;
					//break; // out of while look
				} else {
					logger.warning("Password attempt limit exceeded");
					System.out.println("Username and/or Password Wrong, try again");
					System.out.println("Type your user name");
				}
			}
		}
		//sc1.close();
		return false;
	}
	
	// TODO: read from file account name/password/account information

	public static void main(String[] args) {
		logger.setLevel(null);
		logger.info("Starting main");
		// TODO: Create account(s) then...
		// return a value? // this calls Action menu // bad cohesion 
		if(loginMenu()) {
			showActionMenu();
			logger.info("Finished showActionMenu");
			ActionMenuInputProcessing(); // instead of doing the stuff in ActionMenuInputProcessing have it call functions that do it.
		}
		logger.info("end of program");
	}
}
