package com.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dao.Dao;
import com.project.model.Account;
import com.project.model.User;

@Component
public class Services {
	
	@Autowired
	Dao dao;
	
	public Map<String, Integer> createAccount(Account account) {
		
		int rowsInserted = dao.createAccount(account);
		
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("AccountCreated", rowsInserted);
		
		return res;
	}
	
	public Map<String, Integer> register(User user){
		
		int rowsInserted = dao.register(user);
		Map<String, Integer> res = new HashMap<String, Integer>();
		res.put("RowsInserted", rowsInserted);
		
		return res;
		
	}

	public Map<String,String> login(String username, String password) {
		//Map<String, Boolean> res = new HashMap<String, Boolean>();
		return dao.login(username, password);
	}
	
	public Map<String, ArrayList<String>> getAllUserAccounts() {
		Map<String, ArrayList<String>> map = dao.getAllUserDetails();
		return map;
	}
	
	public Map<String, String> getUserDetails(String username){
		
		return dao.getUserDetails(username);
	}
	
	public List<Account> getAccountsForUser(String username){
		
		return dao.getAccountDetails(username);
	}

	public Boolean depositMoney(Map<String, String> accountDetails) {
		String accountNo = accountDetails.get("accountNo");
		String amount = accountDetails.get("amount");
		String currentBalance = dao.getAccount(accountNo).getAccountBalance();
		
		int updatedBalance = Integer.parseInt(currentBalance) + Integer.parseInt(amount);
		int count = dao.depositMoney(accountNo, String.valueOf(updatedBalance));
		
		return count > 0 ? true : false; 
	}

	public String withdrawMoney(Map<String, String> accountDetails) {
		String accountNo = accountDetails.get("accountNo");
		String amount = accountDetails.get("amount");
		String currentBalance = dao.getAccount(accountNo).getAccountBalance();
		
		int updatedBalance = Integer.parseInt(currentBalance) - Integer.parseInt(amount);
		
		if(updatedBalance < 0) {
			return "Insufficient funds in account";
		}
		int count = dao.depositMoney(accountNo, String.valueOf(updatedBalance));
		
		if(count ==  1) {
			return "Deposited";
		}
		
		return "Error";
	}

}
