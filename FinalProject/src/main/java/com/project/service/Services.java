package com.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.dao.Dao;
import com.project.exception.TransactionException;
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
		int count = 0;
		
		try {
			String currentBalance = dao.getAccount(accountNo).getAccountBalance();
			
			int updatedBalance = Integer.parseInt(currentBalance) + Integer.parseInt(amount);
			count = dao.depositMoney(accountNo, String.valueOf(updatedBalance));
			
			if(count > 0) {
				
				Map<String, String> details = createTrasactionMap("", accountNo, "deposit", amount);
				recordTransation(details);
			}
			
		}catch(TransactionException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return count > 0 ? true : false; 
	}

	public String withdrawMoney(Map<String, String> accountDetails) {
		String accountNo = accountDetails.get("accountNo");
		String amount = accountDetails.get("amount");
		
		try {
			String currentBalance = dao.getAccount(accountNo).getAccountBalance();
			
			int updatedBalance = Integer.parseInt(currentBalance) - Integer.parseInt(amount);
			
			if(updatedBalance < 0) {
				return "Insufficient funds in account";
			}
			int count = dao.depositMoney(accountNo, String.valueOf(updatedBalance));
			
			if(count > 0) {
				
				Map<String, String> details = createTrasactionMap(accountNo, "", "withdraw", amount);
				recordTransation(details);
			}
			
			if(count ==  1) {
				return "withdraw success";
			}
		}catch(TransactionException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "Error";
	}

	public String transferMoney(Map<String, String> transferDetails) {
		String fromAcc = transferDetails.get("fromAcc");
		String toAcc = transferDetails.get("toAcc");
		String amount = transferDetails.get("amount");
		
		try {
			if(checkAccountNo(toAcc)) {
				String currentBalance = dao.getAccount(fromAcc).getAccountBalance();
				int updatedBalance_Acc1 = Integer.parseInt(currentBalance) - Integer.parseInt(amount);
				
				if(updatedBalance_Acc1 < 0) {
					return "Insufficient funds in account";
				}
				//withdrawing money from *fromAcc*, The method name is just depositMoney --ignore.
				int count = dao.depositMoney(fromAcc, String.valueOf(updatedBalance_Acc1));
				
				
				
				if(count == 1) {
					String toAccountCurrentBalance = dao.getAccount(toAcc).getAccountBalance();
					int updatedBalance_Acc2 = Integer.parseInt(toAccountCurrentBalance) + Integer.parseInt(amount);
					
					//Depositing money to toAcc
					count+= dao.depositMoney(toAcc, String.valueOf(updatedBalance_Acc2));
				}
				
				if(count == 2) {
					if(count > 0) {
						
						Map<String, String> details = createTrasactionMap(fromAcc, toAcc, "transfer", amount);
						recordTransation(details);
					}
					return "Money Transfer Successfull";
				}
			}else {
				return "Invalid account number provided for toAccount";
			}
		}catch (TransactionException e) {
			
			e.printStackTrace();
		}catch (Exception e) {
		
			e.printStackTrace();
		}
				
		return "Error";
	}

	public Boolean checkAccountNo(String accNo) {
		int count = dao.checkAccountNo(accNo);		
		return count == 1 ? true : false;
	}
	
	public Map<String, String> createTrasactionMap(String fromAcc, String toAcc, String status, String amount){
		Date now = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		Map<String, String> details= new HashMap<String, String>();
		String uname = null;
		
		if(status.equals("deposit")) {
			uname = dao.getUnameForAcc(toAcc);
		}else if(status.equals("withdraw")) {
			uname = dao.getUnameForAcc(fromAcc);
		}else {
			uname = dao.getUnameForAcc(fromAcc);
		}
		
		
		details.put("fromAcc", fromAcc);
		details.put("toAcc", toAcc);
		details.put("status", status);
		details.put("amount", amount);
		details.put("date", ft.format(now));
		details.put("username", uname);
		
		return details;
	}
	
	public void recordTransation(Map<String, String> details) throws TransactionException {
		Boolean status = dao.insertTransaction(details) > 0 ? true : false;
		if(status == false) {
			throw new TransactionException("error in recording transaction");
		}
		return;
	}

}
