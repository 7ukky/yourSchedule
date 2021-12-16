package sample.database;

public class User {
	private String name;
	private String surname;
	private int group;
	private String email;
	private String pass;
	
	
	
	public User(String name, String surname, int group, String email, String pass) {
		this.name = name;
		this.surname = surname;
		this.group = group;
		this.email = email;
		this.pass = pass;
	}

	public User(String email, String pass) {
		this.email = email;
		this.pass = pass;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
