package com.project.model;

public class Account {

	private String username;
	private String accountNo;
	private String accountType;
	private String accountBalance;
	
	
	public Account() {
		super();
	}


	public Account(String username, String accountNo, String accountType, String accountBalance) {
		super();
		this.username = username;
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}
	
	public Account(String username, String accountType, String accountBalance) {
		super();
		this.username = username;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}
	
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getAccountBalance() {
		return accountBalance;
	}


	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}


	@Override
	public String toString() {
		return "Account [username=" + username + ", accountNo=" + accountNo + ", accountType=" + accountType
				+ ", accountBalance=" + accountBalance + "]";
	}
	
	
}
