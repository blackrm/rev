package model;

public class Customer {
	private int id;
	private String name;
	private String member_id;
	private String username;
	private String join_date;
	
	public Customer() {
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

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String date) {
		this.join_date = date;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", member_id=" + member_id + ", username=" + username
				+ ", join_date=" + join_date + "]";
	}
	
}
