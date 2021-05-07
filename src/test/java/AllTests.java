import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import business.BankManager;
import dao.BankDAO;
import model.Account;
import model.User;

@RunWith(Suite.class)
@SuiteClasses({})
public class AllTests {

	BankManager manager = new BankManager();
	BankDAO bankdao = new BankDAO();
	
	@Test
	public void testLogInUser() {
		boolean works = manager.logInUser("minnie", "mouse");
		assertTrue(works);
	}
	
	@Test
	public void testAnotherLogInUser() {
		boolean works = manager.logInUser("larry", "moe");
		assertFalse(works);
	}
	
	@Test
	public void testViewBalance() {
		double balance = manager.viewBalance("minnie", 2);
		assertNotNull(balance);
	}
	
	@Test
	public void testNumber() {
		boolean checked = manager.validateNumber(-15);
		assertFalse(checked);
	}
	
	@Test
	public void testAnotherNumber() {
		boolean checked = manager.validateNumber(15);
		assertFalse(checked);
	}
	
	@Test
	public void testFindAccountbyCustomerId() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = bankdao.findAccountbyCustomerId(100);
		assertNull(accounts);
	}
	
	@Test
	public void testFindAnotherAccountbyCustomerId() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = bankdao.findAccountbyCustomerId(1);
		assertNotNull(accounts);
	}
	
	@Test
	public void testFindAccountbyId() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = bankdao.findAccountbyId(521);
		assertNull(accounts);
	}
	
	@Test
	public void testFindAnotherAccountbyId() {
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = bankdao.findAccountbyId(2);
		assertNotNull(accounts);
	}
	
	@Test
	public void testFindUserbyUsername(String username) {
		ArrayList<User> users = new ArrayList<User>();
		users = bankdao.findUserbyUsername("keanu");
		assertNotNull(users);
	}
}
