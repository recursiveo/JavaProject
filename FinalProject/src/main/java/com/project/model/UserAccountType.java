package com.project.model;

import java.util.List;
import java.util.Map;

public class UserAccountType {
	
	private String username;
	private List<String> accountTypeList;
	public UserAccountType(String username, List<String> accountTypeList) {
		super();
		this.username = username;
		this.accountTypeList = accountTypeList;
	}
	public UserAccountType() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getAccountTypeList() {
		return accountTypeList;
	}
	public void setAccountTypeList(List<String> accountTypeList) {
		this.accountTypeList = accountTypeList;
	}
	
	
	

}
