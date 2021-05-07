package model;

public class Transaction {
	private int id;
	private String trans_date;
	private String description;
	private double amount;
	private String trans_type;
	private int account_id;
	
	public Transaction() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(String date) {
		this.trans_date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", trans_date=" + trans_date + ", description=" + description + ", amount="
				+ amount + ", trans_type=" + trans_type + ", account_id=" + account_id + "]";
	}
	
	
}
