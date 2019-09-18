package com.revature;

import java.math.BigDecimal;

// Draft

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;
// ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF // Didn't see these as options, saw...
// warring , info , severe
// config, finest, finer, fine,

//import com.revature.ConnectionUtil;
//import com.revature.StreamCloser;

//import com.revature.utils.ConnectionUtil;
//import com.revature.utils.StreamCloser;

// TODO: Set up database DONE; but if some ideas, or issues come up, adjust as needed ,
//       UPDATE: Need to redesign the database to make it so I don't have to setup JDBC up to in a way that permits dependency injection
//               The modify the scripts in the SQLCode file accordingly <-- rename this and other files
// TODO: set up more like sample JDBC code4
// TODO: reorder file methods in files to make things easier to find
// TODO: code cleanup / remove some commented out code
// TODO: re-comment code, use Java-doc
// TODO: find out why logger level are different than the ones descibed in class
// TODO: change logger levels, learn how to

// TODO: Set up exceptions 
// TODO: Change this to permit multiple users
// TODO: quit making things static and start using classes and objects
// TODO: make classes/objects, ...
// https://www.tutorialspoint.com/java/util/scanner_hasnextbigdecimal.htm

// Non-externalized string literal; it should be followed by //$NON-NLS-<n>$
/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {
	
	// make sure the logs from this class are associated with this class
	//@SuppressWarnings("null") // Null type safety (type annotations): The expression of type 'Logger' needs unchecked conversion to conform to '@NonNull Logger'
	public static Logger logger = Logger.getLogger(Main.class.getName()); // TODO: learn this syntax better
	
	// lazily making everything static, even if it shouldn't be...
	static BigDecimal balance = new BigDecimal(0);
	//static String userName = null;
	
	public static void setBalance(BigDecimal balance) {
		logger.info("in setBalance");
		Main.balance = balance;
	}
	
	public static BigDecimal getBalance(String userName) {
		logger.info("in get Balance");
		try {
			BigDecimal newBalance = SQLCode.getBalance(userName);
			if(newBalance != null) {
				return newBalance;
			}
			throw new BadBalanceException("Balance retrieved is probably bad" + newBalance);
			//return BigDecimal.ZERO; // TODO: should throw/catch error
		} catch(BadBalanceException e) {
			System.out.println(e);
			return BigDecimal.ZERO;
		}
	}

	//static String userName = ""; // TODO: finish refactoring this
	//static String userPassword = "";
	
	// TODO: add log in screen
	static void showActionMenu(String userName) {
		logger.info("in showActionMenu");
		System.out.println("Welcome, What would you like to do?");
		System.out.println("");
		System.out.println("C: Create a new account"); // TODO: This website starts with a log in 
		System.out.println("V: View balance");
		System.out.println("W: Withdraw money");
		System.out.println("D: Deposit money");
		System.out.println("T: Show Transations");
		System.out.println("S: Switch User");
		System.out.println("Q: Quit");
		System.out.println("Your balance is currently " + getBalance(userName));
	}
	static void showWithdrawMenu(String userName) {
		logger.info("in showWithdrawMenu");
		System.out.println("Withdrawing from a a balance of " + getBalance(userName));
		System.out.println("Enter amount to withdraw: ");
	}
	static void showDepositMenu(String userName) {
		logger.info("in DepositMenu");
		System.out.println("Depositing money into an account that has a balance of " + getBalance(userName));
		System.out.println("Enter amount to deposit: ");
	}
	
	static void showBalance(String userName) {
		logger.info("in showBalance");
		System.out.println("You have a balance of " + getBalance(userName));
	}
	
	static BigDecimal getMoneyDelta() {
		logger.info("in getMonyDelta");
		int attempts = 0;
		int attemptLimit = 3;
		while(attempts<attemptLimit) {
			if(sc1.hasNextBigDecimal()) // wc.hasNext() &&
			{ 
				BigDecimal amount = sc1.nextBigDecimal();
				if(amount.compareTo(BigDecimal.ZERO) >= 0) {
					return amount;
				}
				//else {
					System.out.println("Number needs to be >= 0");
					attempts++;
				//}
			}
			else {
				System.out.println("Input error, Try again"); // aka It wasn't a BigDouble
				attempts++;
			}
		}
		return new BigDecimal(0);
	}
	
	static boolean createNewUser() {
		logger.info("in createNewUser");
		int createNewUserAttemptCount = 0;
		int createNewUserAttemptCountLimit = 3;
		boolean userExists = true;
		String userName = null;
		String userPassword;
		// TODO: figure out how to deal with userName... <<<---
		while( createNewUserAttemptCount < createNewUserAttemptCountLimit && userExists == true) {
			System.out.println("What username do you want?");
			userName = sc1.next(); // Arg... userName is in the parameter of ActionMenuInputProcessing // TODO: reorganize this
			// TODO test if user exists, re-prompt if exists
			userExists = SQLCode.usersExists(userName);
			createNewUserAttemptCount++;
		}
		if(userExists == false && userName != null) {
			System.out.println("What password do you want?"); // TODO: get user password // TODO: make up password requirement rules.
			userPassword = sc1.next();
			logger.info("User entered a password");
			return SQLCode.addUserPassword(userName, userPassword);
		}
			logger.warning("user had trouble adding username and password");
			return false;
		// TODO: Finish this part off...
		// RETURN userName/userPassword?
	}
	
	//@SuppressWarnings({ "null" })
	// TODO: these could be made more foreign language friendly using numbers instead of letters.
	static void ActionMenuInputProcessing(String userName) { // TODO: ?Some way to make this in a way that doesn't take userName as input?
		logger.info("in ActionMenuProcessing");
		String currentUserName = userName;
		//System.out.println("ActionMenuInputProcessing() running");
		int attempt_limit = 3;
		int attempts = 0;
		String userInput;
		BigDecimal delta; 
		boolean quit = false;
		while(attempts < attempt_limit && quit == false){
			showActionMenu(currentUserName);
			userInput = sc1.next();
			//System.out.println("Inside if statement");
			switch(userInput.toUpperCase()) {
			case("V"):
				attempts = 0;
				showBalance(currentUserName);
				break;
			case("C"):  // TODO: finish...
				createNewUser();
				break;
			
			case("D"):
				try {
					attempts = 0;
					showDepositMenu(currentUserName);
					delta = getMoneyDelta();
					if(getBalance(currentUserName).add(delta).compareTo(BigDecimal.ZERO)>=0) {
						SQLCode.addTransation(currentUserName, delta);
						balance = getBalance(currentUserName).add(delta); // TODO: combine with Deposit method. // TODO: check if result is <0 or >=0 ...
						showBalance(currentUserName);
					} else { // depositing positive money should never result in negative balance. // TODO: don't assume a positive starting point ...
						logger.warning("deposit input would have created a negative balance" + getBalance(currentUserName).add(delta));
						throw new NegativeBalanceException("New balance would have been less than zero, " + getBalance(currentUserName).add(delta));
					} 
				} catch (NegativeBalanceException e) {
					System.out.println("NegativeBalanceExceptionThrown" + e);
				}
				//System.out.println("The balance is now: " + getBalance(userName));
				break;
			case("W"):
				try {
					attempts = 0;
					showWithdrawMenu(currentUserName);
				// https://www.tutorialspoint.com/java/util/scanner_hasnextbigdecimal.htm
					delta = getMoneyDelta(); 
					// verifies result won't be negative...
					if(getBalance(currentUserName).subtract(delta).compareTo(BigDecimal.ZERO)>=0) {
						SQLCode.addTransation(currentUserName, delta.multiply(BigDecimal.valueOf(-1)));
						balance = getBalance(currentUserName).subtract(delta);
					}
					else {
						logger.warning("withdrawal input would have created a negative balance" + getBalance(currentUserName).subtract(delta));
						throw new NegativeBalanceException("New balance would have been less than zero " + getBalance(currentUserName).subtract(delta));
					}
				}catch(NegativeBalanceException e) {
					System.out.println("NegativeBalanceExceptionThrown" + e);
				}
			break;
			case("T"):
				SQLCode.showTransations(currentUserName);
				break;
			case("S"):
				String newUser = loginMenu();
				if(newUser == null) {
					logger.warning("Error in switching users");
					System.out.println("Switching user failed, still on previous user");
					// TODO: throw/catch bad user exception
				} else {
					currentUserName = newUser;
				}
				break;
			case("Q"):
				System.out.println("Thanks, Session ended sucessfully");
				logger.info("Session ended sucessfully");
				quit = true;
				break;
			default:
				logger.warning("User enter non ActionMenu choice in ActionMenu");
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
	
	static String loginMenu() {// IDEA: throw InvalidNewBalanceException
		logger.info("in loginMenu");
		int attempt_limit = 3;
		int attempts = 0;
		String userName = null;
		String userPassword = null;
		
		System.out.println("Type your user name");
		while(sc1.hasNext() && attempts < attempt_limit){
			userName = sc1.next().trim().toLowerCase();
			System.out.println("Type your password");
			if(sc1.hasNext()) {
				userPassword = sc1.next().trim();
			}
			else {
				userPassword = "";
			}
			// TODO: refactor check...
			//System.out.println("UserName: " + userName);
			//System.out.println("Password: " + userPassword);
			//if(userName.contentEquals(userName) && userPassword.contentEquals(userPassword)) { // TODO: refactor change to SQL script to detect if in security table
			logger.info("Calling SQLCode.validatePassword");
			if(SQLCode.validatePassword(userName, userPassword)) {
				System.out.println("valid username and password");
				logger.info("valid username and password entered");
				//sc1.close();
				return userName;
				//showActionMenu(); // probably too much coupling(spelling?), wish I had paper...
				//ActionMenuInputProcessing();
				//break;
			}
			//else {
				attempts++;
				if(attempts >= attempt_limit) { // TODO: verify not off by one					
					logger.warning("Password attempt limit " + attempt_limit + "exceeded");
					System.out.println("feel free to try later...");
					//sc1.close();
					return null;
					//break; // out of while look
				} //else {
					
					System.out.println("Username and/or Password Wrong, try again");
					System.out.println("Type your user name");
				//}
			//}
		}
		//sc1.close();
		return null;
	}
	// TODO: read from file account name/password/account information
	// TODO: create general input/output interfaces
	// Connection conn; // = ConnectionUtil.getConnection();
	// TODO: use create create security table sql?


	public static void main(String[] args) {
		logger.info("in main");
		// TODO: deal with null users at startup...
		// TODO: program asks to log in first, what if there are no users in table?, ...
		// TODO: assignment-wise: add JUnit
		// TODO: better use of logger
		// TODO: ADD RESTRICTION ON USERNAMES OR RISK SQL INJECTION
		
		// NOTE: Default userName/password: Admin, Password
		
		// TODO: Add the ability to switch to new account without exiting script
		
		logger.setLevel(null);
		logger.info("Starting main");
		// TODO: Create account(s) then...
		// return a value? // this calls Action menu // bad cohesion
		String userName = loginMenu();
		if(userName != null) {
			//showActionMenu(userName);
			logger.info("Finished showActionMenu");
			ActionMenuInputProcessing(userName); // instead of doing the stuff in ActionMenuInputProcessing have it call functions that do it.
		}
		logger.info("end of program");
	}
}
