package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Account;
import model.Customer;
import model.Transaction;
import model.User;
import util.JDBCConnection;

public class BankDAO {
	
	static Connection conn = JDBCConnection.makeConnection();
	private static final Logger logger = LogManager.getLogger(BankDAO.class);

	public ArrayList<User> logInUser(String username, String pwd) {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select id, username, pwd from bankuser where username = ? and pwd = ?");
			logger.debug("logging in");
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				users.add(user);
			}
		} catch (SQLException e) {
			logger.error("couldn't log in", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	public void createNewUser(String username, String pwd) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into bankuser(id, username, pwd) values (default, ?,?)");
			logger.debug("creating new user");
			pstmt.setString(1, username);
			pstmt.setString(2, pwd);
			int k = pstmt.executeUpdate();
			System.out.println(k + " user added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("could not create new user", e);
			e.printStackTrace();
		}
	}

	public void createNewCustomer(String name, String member_id, String username) throws Exception {
		// TODO Auto-generated method stub
		try {
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String datestr = formatter.format(date);
//			Date date1 = formatter.parse(datestr);
//			String dateStr = formatter.format(datestr);
			PreparedStatement pstmt = conn.prepareStatement("insert into bankcustomer(name, member_id, username, join_date) values (?,?,?,?)");
			logger.debug("creating new customer");
			pstmt.setString(1, name);
			pstmt.setString(2, member_id);
			pstmt.setString(3, username);
			pstmt.setString(4, datestr);
			int k = pstmt.executeUpdate();
			System.out.println(k + " customer added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("could not create customer", e);
			e.printStackTrace();
		}
	}

	public void createWithdrawal(String username, Transaction transaction) {
		// TODO Auto-generated method stub
		double balance = viewBalance(username, transaction.getAccount_id());
		if(balance < transaction.getAmount())
			System.out.println("Insufficient funds for transaction");
		else {
			try {
				PreparedStatement pstmt = conn.prepareStatement("insert into banktransaction(id, trans_date, description, amount, trans_type, account_id) values (default, ?,?,?,?,?)");
				logger.debug("creating withdrawal");
				pstmt.setString(1, transaction.getTrans_date());
				pstmt.setString(2, transaction.getDescription());
				pstmt.setDouble(3, transaction.getAmount());
				pstmt.setString(4, transaction.getTrans_type());
				pstmt.setInt(5, transaction.getAccount_id());
				int k = pstmt.executeUpdate();
				System.out.println(k + " transaction added");
				
				double newbalance = balance - transaction.getAmount();
				PreparedStatement pstmt2 = conn.prepareStatement("update bankaccount set balance = ? where id = ?");
				logger.debug("adjusting balance");
				pstmt2.setDouble(1, newbalance);
				pstmt2.setInt(2, transaction.getAccount_id());
				pstmt2.executeUpdate();
				System.out.println("balance updated");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("withdrawal could not be created", e);
				e.printStackTrace();
			}
		}
	}

	public void createDeposit(Transaction transaction) {
		// TODO Auto-generated method stub
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = findAccountbyId(transaction.getAccount_id());
		if(accounts.size() == 0)
			System.out.println("Customer account not found");
		else {
			try {
				PreparedStatement pstmt = conn.prepareStatement("insert into banktransaction(id, trans_date, description, amount, trans_type, account_id) values (default, ?,?,?,?,?)");
				logger.debug("creating deposit");
				pstmt.setString(1, transaction.getTrans_date());
				pstmt.setString(2, transaction.getDescription());
				pstmt.setDouble(3, transaction.getAmount());
				pstmt.setString(4, transaction.getTrans_type());
				pstmt.setInt(5, transaction.getAccount_id());
				int k = pstmt.executeUpdate();
				System.out.println(k + " transaction added");
				
				double newbalance = accounts.get(0).getBalance() + transaction.getAmount();
				PreparedStatement pstmt2 = conn.prepareStatement("update bankaccount set balance = ? where id = ?");
				logger.debug("adjusting balance");
				pstmt2.setDouble(1, newbalance);
				pstmt2.setInt(2, transaction.getAccount_id());
				pstmt2.executeUpdate();
				System.out.println("balance updated");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("deposit could not be created", e);
				e.printStackTrace();
			}
		}
	}

	public double viewBalance(String username, int num) {
		// TODO Auto-generated method stub
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers = findCustomerbyUsername(username);
		int cid = 0;
		boolean found = false;
		double balance = 0;
		if(customers.size() > 0)
			cid = customers.get(0).getId();
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = findAccountbyCustomerId(num);
		logger.debug("making sure account belongs to customer");
		if(accounts.size() > 0) {
			for(int k = 0; k < accounts.size(); k++) {
				if(cid == accounts.get(k).getCustomer_id())
					found = true;
			}
		}
		if(found == true) {
			try {
				PreparedStatement pstmt = conn.prepareStatement("select balance from bankaccount where id = ?");
				logger.debug("retrieving balance of account for " + num);
				pstmt.setInt(1, num);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					balance = rs.getDouble(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("balance could not be read", e);
				e.printStackTrace();
			}
		}
		else
			System.out.println("This account is not yours");
		return balance;
	}

	public void openAccount(String username, Account account) {
		// TODO Auto-generated method stub
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers = findCustomerbyUsername(username);
		logger.debug("making sure user is a customer before they can open account");
		if(customers.size() == 0)
			System.out.println("Customer account not found");
		else {
			try {
				Customer customer = customers.get(0);
				PreparedStatement pstmt = conn.prepareStatement("insert into bankaccount(id, name, primary_account, balance, customer_id) values (default, ?,?,?,?)");
				logger.debug("opening account");
				pstmt.setString(1, customer.getName());
				pstmt.setString(2, account.getPrimary_account());
				pstmt.setDouble(3, account.getBalance());
				pstmt.setInt(4, customer.getId());
				int k = pstmt.executeUpdate();
				System.out.println(k + " account added");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("account could not be opened", e);
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Account> findAccountbyCustomerId(int cid) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select id, name, primary_account, balance, customer_id from bankaccount where customer_id = ?");
			pstmt.setInt(1, cid);
			logger.debug("finding accounts for " + cid);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				account.setPrimary_account(rs.getString(3));
				account.setBalance(rs.getDouble(4));
				account.setCustomer_id(rs.getInt(5));
				accounts.add(account);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("accounts could not be found for " + cid, e);
			e.printStackTrace();
		}
		return accounts;
		
	}

	public ArrayList<Account> findAccountbyId(int num) {
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select id, name, primary_account, balance, customer_id from bankaccount where id = ?");
			logger.debug("finding account for " + num);
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				account.setPrimary_account(rs.getString(3));
				account.setBalance(rs.getDouble(4));
				account.setCustomer_id(rs.getInt(5));
				accounts.add(account);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("account could not be found", e);
			e.printStackTrace();
		}
		return accounts;
		
	}
	
	public ArrayList<User> findUserbyUsername(String username) {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select id, username, pwd from bankuser where username = ?");
			logger.debug("finding user with username " + username);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("user record could not be found", e);
			e.printStackTrace();
		}

		return users;
	}
	
	public ArrayList<Customer> findCustomerbyUsername(String username) {
		// TODO Auto-generated method stub
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select id, name from bankcustomer where username = ?");
			logger.debug("finding customer record for " + username);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt(1));
				customer.setName(rs.getString(2));
				customers.add(customer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("customer record could not be found", e);
			e.printStackTrace();
		}

		return customers;
	}


	
}
