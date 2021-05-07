package model;

public class Employee {
	
	private int id;
	private String name;
	private int employee_number;
	private int passcode;
	
	public Employee() {
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

	public int getEmployee_number() {
		return employee_number;
	}

	public void setEmployee_number(int employee_number) {
		this.employee_number = employee_number;
	}

	public int getPasscode() {
		return passcode;
	}

	public void setPasscode(int passcode) {
		this.passcode = passcode;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", employee_number=" + employee_number + ", passcode="
				+ passcode + "]";
	}
	
	

}
