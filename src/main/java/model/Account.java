package model;

public class Account {
	private int id;
	private String name;
	private String primary_account;
	private double balance;
	private int customer_id;
	private int approved;
	
	public Account() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimary_account() {
		return primary_account;
	}

	public void setPrimary_account(String primary_account) {
		this.primary_account = primary_account;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", primary_account=" + primary_account + ", balance=" + balance
				+ ", customer_id=" + customer_id + ", approved=" + approved + "]";
	}

}
