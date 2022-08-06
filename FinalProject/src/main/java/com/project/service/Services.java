package com.project.service;

import java.util.HashMap;
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
		
		int rowsInserted = dao.account(account);
		
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

}
