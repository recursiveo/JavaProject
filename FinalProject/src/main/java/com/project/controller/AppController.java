package com.project.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Account;
import com.project.model.Login;
import com.project.model.User;
import com.project.service.Services;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class AppController {
	
	@Autowired
	Services service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET )
	public String home() {
		return "hello";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Map<String, Integer> Register(@RequestBody User user) {
		System.out.println(user);
		//return ResponseEntity.status(HttpStatus.CREATED).body("Registered user: " + user.getFname());
		
		return service.register(user);
	}
	
	@RequestMapping(value = "/createAcc", method = RequestMethod.POST)
	public Map<String, Integer> createAcc(@RequestBody Account account){
		
		return service.createAccount(account);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, String> login(@RequestBody Login login){
		
		// System.out.println(login.getUsername() + " " + login.getPassword());
		return service.login(login.getUsername(), login.getPassword());
	}
	
	@RequestMapping(value = "/getAllUserAcc", method = RequestMethod.GET)
	public Map<String, ArrayList<String>> getAllUserAccounts() {
		return service.getAllUserAccounts();
	}
	
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
	public Map<String, String> getUserDetails(@RequestParam("username") String username){
		
		return service.getUserDetails(username);
	}
	
	@RequestMapping(value = "/getAccountsForUser", method = RequestMethod.GET)
	public List<Account> getAccountsForUser(@RequestParam("username") String username){
		
		return service.getAccountsForUser(username);
	}
	
	@RequestMapping(value = "/depositMoney", method = RequestMethod.POST)
	public Boolean depositMoney(@RequestBody Map<String, String> accountDetails) {
		
		return service.depositMoney(accountDetails);
	}
	
	@RequestMapping(value = "/withdrawMoney", method = RequestMethod.POST)
	public Map<String, String> withdrawMoney(@RequestBody Map<String, String> accountDetails) {
		
		String msg = service.withdrawMoney(accountDetails);
		
		HashMap<String, String> hashMap = new HashMap<String, String>(){{ put("message", msg); }};
		return hashMap;
	}
	
	@RequestMapping(value = "/transferMoney", method = RequestMethod.POST)
	public Map<String, String> transferMoney(@RequestBody Map<String, String> transferDetails){
		
		String msg = service.transferMoney(transferDetails);
		HashMap<String, String> hashMap = new HashMap<String, String>(){{ put("message", msg); }};
		return hashMap;
	}
	
	@RequestMapping(value = "/isValidAccountNo", method = RequestMethod.GET)
	public Boolean isValidAccountNo(@RequestParam String accNo) {		
		return service.checkAccountNo(accNo);
	}
	
}
