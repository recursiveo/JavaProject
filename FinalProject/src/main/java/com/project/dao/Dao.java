package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.model.Account;
import com.project.model.Transaction;
import com.project.model.User;
import com.project.util.DatabaseConnection;

import ch.qos.logback.classic.db.names.DBNameResolver;

@Component
public class Dao {
	
//	@Autowired
//	private static DatabaseConnection dbConnection;

	public static Connection getConnection() {
		 Connection con = null;
		String user = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/projectjava";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection successfull");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return con;
	}

	public int createAccount(Account account) {
		Random r = new Random( System.currentTimeMillis() );
	    long accNo = 10000 + r.nextInt(20000);
	    account.setAccountNo(String.valueOf(accNo));
		int rowsInserted = 0;
		String sql = "INSERT INTO account(`accountNo`, `accountType`, `accountBalance`, `username`) "
				+ "VALUES (? , ? , ? , ?)";
		
		System.out.println(account);
		try {
			
			
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getAccountNo());
			pstmt.setString(2, account.getAccountType());
			pstmt.setString(3, account.getAccountBalance());
			pstmt.setString(4, account.getUsername());

			rowsInserted = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowsInserted;
	}

	public int register(User user) {
		int rowsInserted = 0;
		String sql = "INSERT INTO user(`username`, `password`, `fname`, "
				+ "`address`, `email`, `phone`, `lname`, `sinNumber`, `role`) " + "VALUES (?,?,?,?, ?,?,?,?, ?)";

		try {
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFname());
			pstmt.setString(4, user.getAddress());

			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getPhone());
			pstmt.setString(7, user.getLname());
			pstmt.setString(8, user.getSinNumber());
			pstmt.setString(9, user.getRole());

			rowsInserted = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowsInserted;
	}

	public Map<String, String> login(String username, String password) {

		Map<String, String> res = new HashMap<String, String>();
		String sql = "SELECT * FROM user where username =? and password =?";

		try {

			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rowsRetured = pstmt.executeQuery();

			if (rowsRetured.next()) {
				res.put("isRegistered", "true");
				if (username.equals("admin")) {
					res.put("isAdmin", "true");
				} else {
					res.put("isAdmin", "false");
				}

			} else {
				res.put("isRegistered", "false");
				res.put("isAdmin", "false");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public Map<String, ArrayList<String>> getAllUserDetails(){
		
		Map<String, ArrayList<String>> accMap = new HashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> userAccMap = new HashMap<String, ArrayList<String>>();
		
		String sql1 = "SELECT username,fname,lname FROM user where role= 'user'";
		String sql2 = "SELECT username, accountNo, accountType FROM account";
		
		try {
			
			Connection con = getConnection();
			//PreparedStatement pstmt = con.prepareStatement(sql);
			PreparedStatement st1 = con.prepareStatement(sql1);
			PreparedStatement st2 = con.prepareStatement(sql2);
			
			//ResultSet rowsRetured = pstmt.executeQuery();
			ResultSet res1 = st1.executeQuery();
			ResultSet res2 = st2.executeQuery();
			
			while(res2.next()) {
				
				if(!(accMap.containsKey(res2.getString(1)))) {
					ArrayList<String> list = new ArrayList<String>();
					list.add(res2.getString(3));
					accMap.put(res2.getString(1), list);
				}else {
					ArrayList<String> list = accMap.get(res2.getString(1));
					list.add(res2.getString(3));
					accMap.put(res2.getString(1), list);
				}	
			}
		while(res1.next()) {
			if(!(userAccMap.containsKey(res1.getString(1)))) {
				ArrayList<String> accList = accMap.get(res1.getString(1));
				
				if(accList == null) {
					accList = new ArrayList<String>();
				}
				
				userAccMap.put(res1.getString(1), accList);
			}
		}
					
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userAccMap;
	}
	
	public Map<String, String> getUserDetails(String username){
		Map<String, String> userDetails = new HashMap<String, String>();
		String sql = "SELECT username, fname, lname, address, email, phone, sinNumber, role FROM user WHERE username=?";
		//System.out.println("username" + username);
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				userDetails.put("username", rs.getString(1));
				userDetails.put("fname", rs.getString(2));
				userDetails.put("lname", rs.getString(3));
				userDetails.put("address", rs.getString(4));
				userDetails.put("email", rs.getString(5));
				userDetails.put("phone", rs.getString(6));
				userDetails.put("sinNumber", rs.getString(7));
				userDetails.put("role", rs.getString(8));
			}
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return userDetails;
	}
	
	public List<Account> getAccountDetails(String username){
		ArrayList<Account> accArr = new ArrayList<Account>();
		String sql = "SELECT `accountNo`, `accountType`, `accountBalance`, `username` FROM `account` WHERE username = ?";
		
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			
			ResultSet rs = pst.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				Account acc = new Account(username, rs.getString(1),rs.getString(2), rs.getString(3));
				accArr.add(acc);

			}
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return accArr;
	}

	public int depositMoney(String accountNo, String amount) {
		String sql = "UPDATE `account` SET `accountBalance`= ? WHERE `accountNo`=?";
		int rowsAffected = 0;
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, amount);
			pst.setString(2, accountNo);
			
			rowsAffected = pst.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rowsAffected;
	}
	
	public Account getAccount(String accountNo) {
		String sql = "SELECT `accountNo`, `accountType`, `accountBalance`, `username` FROM `account` WHERE `accountNo`=?";
		Account acc = null;
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, accountNo);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				acc = new Account(rs.getString(4), rs.getString(1),rs.getString(2), rs.getString(3));

			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return acc;
	}

	public int checkAccountNo(String accNo) {
		String sql = "SELECT `accountNo` FROM `account` WHERE accountNo = ?";
		int count =0;
		
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, accNo);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				count++;
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return count;
		
	}
	
	public int insertTransaction(Map<String, String> row) {
		String sql = "INSERT INTO `transaction`(`transactionId`, `fromAccount`, `toAccount`, `status`, `Amount`, `Date`, `username`) VALUES"
				+ " (?, ?, ?, ?, ?, ?, ?)";
		//System.out.println("FEEEE-----------");
		//System.out.println("map  : " + row.toString());
		int insertCount = 0;
		try {
			String uid = UUID.randomUUID().toString();
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, uid);
			pst.setString(2, row.get("fromAcc"));
			pst.setString(3, row.get("toAcc"));
			pst.setString(4, row.get("status"));
			pst.setString(5, row.get("amount"));
			pst.setString(6, row.get("date"));
			pst.setString(7, row.get("username"));
			
			insertCount = pst.executeUpdate();
			
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return insertCount;
	}
	
	public String getUnameForAcc(String acc) {
		String sql = "SELECT `username` FROM `account` WHERE accountNo = ?";
		String username = null;
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, acc);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				username = rs.getString(1);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return username;
	}

	public List<Transaction> getTransactionForUser(String username) {
		String sql = "SELECT `transactionId`, `fromAccount`, `toAccount`, `status`, "
				+ "`Amount`, `Date`, `username` FROM `transaction` WHERE username = ?";
		
		ArrayList<Transaction> transactionList = new ArrayList<Transaction>(); 
		try {
			Connection con = getConnection();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				Transaction transaction = new Transaction(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7)
						);
				transactionList.add(transaction);
				
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return transactionList;
	}

}
