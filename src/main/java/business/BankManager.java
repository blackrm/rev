package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Account;
import model.Customer;
import model.Transaction;
import model.User;
import dao.BankDAO;

public class BankManager {
	
	private static final Logger logger = LogManager.getLogger(BankManager.class);
	
	BankDAO bankdao = new BankDAO();

	public boolean logInUser(String username, String pwd) {
		// TODO Auto-generated method stub
		logger.debug("logging in user");
		boolean loggedIn = false;
		ArrayList<User> users = new ArrayList<User>();
		users = bankdao.logInUser(username, pwd);
		if(users.size() == 1)
			loggedIn = true;
		return loggedIn;		
	}

	public String createNewUser(String username, String pwd) {
		// TODO Auto-generated method stub
		logger.debug("creating a new user");
		ArrayList<User> users = new ArrayList<User>();
		String resp = "";
		users = bankdao.findUserbyUsername(username);
		if(users.size() == 0) {
			bankdao.createNewUser(username, pwd);
			resp = "Your new login has been created";
			System.out.println(resp);
		}
		else {
			resp = "Login could not be created.  Username already in use";
			System.out.println(resp);
		}
		return resp;
			
		
	}

	public void createNewCustomer(Customer customer) throws Exception {
		// TODO Auto-generated method stub
		logger.debug("creating new customer");
		int mem = new Random().nextInt(9999999) + 1000000;
		String mem2 = Integer.toString(mem);
		customer.setMember_id(mem2);
	//	String datestr = customer.getJoin_date();
		bankdao.createNewCustomer(customer.getName(), customer.getMember_id(), customer.getUsername());
	}

	public void createWithdrawal(String username, int num, double amount) {
		// TODO Auto-generated method stub
		logger.debug("creating new withdrawal");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = formatter.format(date);
		Transaction transaction = new Transaction();
		transaction.setTrans_date(datestr);
		transaction.setAccount_id(num);
		transaction.setAmount(amount);
		transaction.setTrans_type("W");
		transaction.setDescription("Withdrawal");
		bankdao.createWithdrawal(username, transaction);
	}

	public void createDeposit(int num, double amount) {
		// TODO Auto-generated method stub
		logger.debug("creating a deposit");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = formatter.format(date);
		Transaction transaction = new Transaction();
		transaction.setTrans_date(datestr);
		transaction.setAccount_id(num);
		transaction.setAmount(amount);
		transaction.setTrans_type("D");
		transaction.setDescription("Deposit");
		bankdao.createDeposit(transaction);
	}

	public double viewBalance(String username, int num) {
		// TODO Auto-generated method stub
		logger.debug("viewing balance of account " + num);
		double balance = bankdao.viewBalance(username, num);
		
		return balance;
	}

	public void openAccount(String username, Account account) {
		// TODO Auto-generated method stub
		logger.debug("opening account");
		bankdao.openAccount(username, account);
		
	}

	public boolean validateNumber(double num) {
		// TODO Auto-generated method stub
		logger.debug("making sure number passed in is not less than 0");
		boolean myState = true;
		if(num < 0)
			myState = false;
		return myState;
	}

}
