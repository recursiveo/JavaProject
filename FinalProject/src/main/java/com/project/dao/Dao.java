package com.project.dao;

import java.lang.reflect.Array;
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

import org.springframework.stereotype.Component;

import com.project.model.Account;
import com.project.model.User;
import com.project.model.UserAccountType;

@Component
public class Dao {

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

	public int account(Account account) {
		int rowsInserted = 0;
		String sql = "INSERT INTO account(`accountNo`, `accountType`, `accountBalance`, `username`) "
				+ "VALUES (? , ? , ? , ?)";

		try {
			
			Random r = new Random( System.currentTimeMillis() );
		    long accNo = 10000 + r.nextInt(20000);
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(accNo));
			pstmt.setString(2, account.getAccountType());
			pstmt.setString(3, account.getAccountBalance());
			pstmt.setString(4, account.getUsername());

			rowsInserted = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
//		Map response  = new HashMap<String, String>();
//		response.put("data", "Hello");

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
		//Map<String, String> res = new HashMap<String, String>();
		Map<String, ArrayList<String>> accMap = new HashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> userAccMap = new HashMap<String, ArrayList<String>>();
		//String sql = "SELECT a.username, a.fname, a.lname, b.accountNo, b.accountType FROM user a, account b WHERE a.username = b.username order by username;";
		String sql1 = "SELECT username,fname,lname FROM user";
		String sql2 = "SELECT username, accountNo, accountType FROM account";
		
		try {
			
			Connection con = getConnection();
			//PreparedStatement pstmt = con.prepareStatement(sql);
			PreparedStatement st1 = con.prepareStatement(sql1);
			PreparedStatement st2 = con.prepareStatement(sql2);
			
			//ResultSet rowsRetured = pstmt.executeQuery();
			ResultSet res1 = st1.executeQuery();
			ResultSet res2 = st2.executeQuery();
			
//			while(rowsRetured.next()) {
//				
//				if(!(userMap.containsKey(rowsRetured.getString(1)))) {
//					ArrayList<String> list = new ArrayList<String>();
//					list.add(rowsRetured.getString(5));
//					userMap.put(rowsRetured.getString(1), list);
//				}else {
//					ArrayList<String> list = userMap.get(rowsRetured.getString(1));
//					list.add(rowsRetured.getString(5));
//					userMap.put(rowsRetured.getString(1), list);
//				}				
//				
//			}
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
				//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$" + accMap);
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
		//System.out.println("^^^^^^^^^^^^^^^^^^^^^" + userAccMap);
		return userAccMap;
	}

}
