package com.project.model;

public class User {

	private String username;
	private String password;
	//TODO: add other fields like fname, lname, address
	private String fname;
	private String lname;
	private String address;
	private String email;
	private String phone;
	private String sinNumber;
	private String role;
	
	public User() {
		super();
	}

	/**
	 * @param username
	 * @param password
	 * @param fname
	 * @param lname
	 * @param address
	 * @param email
	 * @param phone
	 */
	public User(String username, String password, String fname, String lname, String address, String email,
			String phone, String sinNumber, String role) {
		super();
		this.username = username;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.sinNumber = sinNumber;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public String getSinNumber() {
		return sinNumber;
	}

	public void setSinNumber(String sinNumber) {
		this.sinNumber = sinNumber;
	}
	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fname=" + fname + ", lname=" + lname
				+ ", address=" + address + ", email=" + email + ", phone=" + phone + ", sinNumber=" + sinNumber
				+ ", role=" + role + "]";
	}
	
	
}
