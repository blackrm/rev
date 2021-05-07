package client;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import business.BankManager;
import model.Account;
import model.Customer;
import model.User;

public class BankInteractive {
	private static final Logger logger = LogManager.getLogger(BankInteractive.class);
	static Scanner scan = new Scanner(System.in);
	static User myUser = new User();
	static boolean loggedIn = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String choice = "";
		logger.info("Application started");
		System.out.println("RB Bank");
		System.out.println("=======");
		System.out.println();
		try {
			logger.debug("trying to log user in");
			initialLogin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("user could not be logged in", e);
			e.printStackTrace();
		}
		
		logger.debug("giving choices if logged in");
		if(loggedIn == true) {
		while(choice.compareTo("5") != 0) {
			displayMenu();
			choice = scan.next();
			switch(choice) {
			case "1":
				newAccount();
				break;
			case "2":
				getBalance();
				break;
			case "3":
				makeDeposit();
				break;
			case "4":
				makeWithdrawal();
				break;
			case "5":
				System.out.println("Thank you! Have a nice day!");
				logger.info("program ending");
				scan.close();
				break;
			default:
				System.out.println("Invalid entry, please try again:  ");
				choice = scan.next();
			}
		}
		}
	}

	private static void makeWithdrawal() {
		// TODO Auto-generated method stub
		logger.debug("making a withdrawal");
		boolean checked = false;
		BankManager manager = new BankManager();
		System.out.println("Please enter the account number:  ");
		int num = scan.nextInt();
		System.out.println("How much would you like to withdraw?  ");
		double amt = scan.nextDouble();
		checked = manager.validateNumber(amt);
		if(checked == true) {
			manager.createWithdrawal(myUser.getUsername(), num, amt);
			manager.viewBalance(myUser.getUsername(), num);
		}
		else
			System.out.println("Cannot withdraw a negative number");
	}

	private static void makeDeposit() {
		// TODO Auto-generated method stub
		logger.debug("making a deposit");
		boolean checked = false;
		BankManager manager = new BankManager();
		System.out.println("Please enter the account number:  ");
		int num = scan.nextInt();
		System.out.println("How much would you like to deposit?  ");
		double amt = scan.nextDouble();
		checked = manager.validateNumber(amt);
		if(checked == true) {
			manager.createDeposit(num, amt);
			manager.viewBalance(myUser.getUsername(), num);
		}
		else
			System.out.println("Cannot deposit a negative number");
		
	}

	private static void getBalance() {
		// TODO Auto-generated method stub
		logger.debug("getting an account balance");
		BankManager manager = new BankManager();
		System.out.println("Please enter the account number:  ");
		int num = scan.nextInt();
		double balance = manager.viewBalance(myUser.getUsername(), num);
		System.out.println("$" + balance);
	}

	private static void newAccount() {
		// TODO Auto-generated method stub
		logger.debug("opening an account");
		BankManager manager = new BankManager();
		Account account = new Account();
		String answer = "";
		char response = ' ';
		boolean checked = false;
		while(response == ' ') {
			System.out.println("Will this be your primary account? 'Y' or 'N':  ");
			answer = scan.next().toUpperCase();
			response = answer.charAt(0);
			if((response == 'Y') || (response == 'N'))
				account.setPrimary_account(answer);
			else {
				System.out.println("Invalid answer");
				answer = "";
				response = ' ';
			}
		}
		System.out.println("What will the beginning balance be? ");
		double begBalance = scan.nextDouble();
		checked = manager.validateNumber(begBalance);
		if(checked == true) {
			account.setBalance(begBalance);
			manager.openAccount(myUser.getUsername(), account);
		}
		else
			System.out.println("Cannot give an account a negative balance");
		
	}

	private static void initialLogin() throws Exception {
		// TODO Auto-generated method stub
		logger.debug("trying to log user in");
		System.out.println("Would you like to (please enter 1 or 2: )");
		System.out.println("1.  Log in");
		System.out.println("2.  Become a customer");
		String init = scan.next();
		switch(init) {
		case "1":
			logIn();
			break;
		case "2":
			createNewCustomer();
			break;
		default:
			System.out.println("Invalid Choice");
		}
		
	}

	private static void createNewCustomer() throws Exception {
		// TODO Auto-generated method stub
		logger.debug("creating new customer");
		BankManager manager = new BankManager();
		Customer customer = new Customer();
		logIn();
		if(loggedIn ==  true) {
			System.out.println("Enter your name: ");
			customer.setName(scan.next());
			customer.setUsername(myUser.getUsername());
			manager.createNewCustomer(customer);
		}else
			createNewLogin();
		
	}

	private static void createNewLogin() {
		// TODO Auto-generated method stub
		logger.debug("creating a new user");
		BankManager manager = new BankManager();
		String message = "";
		System.out.println("Enter user name: ");
		String username = scan.next();
		System.out.println("Enter user password:  ");
		String pwd = scan.next();
		message = manager.createNewUser(username, pwd);
		System.out.println(message);
		if(message.compareTo("Login could not be created.  Username already in use") == 0)
			return;
		System.out.println("Your user account has been created");
		System.out.println("Would you like to log in?  ");
	}

	private static void logIn() {
		// TODO Auto-generated method stub
		logger.debug("logging the user in and making sure they have been authenticated");
		BankManager manager = new BankManager();
		System.out.println("Please log in: Enter user name: ");
		String username = scan.next();
		System.out.println("Enter user password:  ");
		String pwd = scan.next();
		loggedIn = manager.logInUser(username, pwd);
		if(loggedIn == true)
			myUser.setUsername(username);
		else
			System.out.println("Username or password is invalid");
	}

	private static void displayMenu() {
		// TODO Auto-generated method stub
		logger.info("showing the user menu");
		System.out.println("1.  Apply for a New Bank Account");
		System.out.println("2.  Get the Balance of an Account");
		System.out.println("3.  Make a Deposit");
		System.out.println("4.  Make a Withdrawal");
		System.out.println("5.  Exit");
		
	}

}
