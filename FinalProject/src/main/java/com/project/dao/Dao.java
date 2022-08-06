package com.project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.model.Account;
import com.project.model.User;


@Component
public class Dao {
	
	public static Connection getConnection() {
		Connection con = null;
		String user = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/projectjava";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,user,password);
			System.out.println("Connection successfull");
		}catch(ClassNotFoundException e) {
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
			
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, account.getAccountNo());
			pstmt.setString(2, account.getAccountType());
			pstmt.setString(3, account.getAccountBalance());
			pstmt.setString(4, account.getUsername());
			
			rowsInserted = pstmt.executeUpdate(); 
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
//		Map response  = new HashMap<String, String>();
//		response.put("data", "Hello");
		
		return rowsInserted;
	}
	

	public int register(User user) {
		int rowsInserted = 0;
		String sql = "INSERT INTO user(`username`, `password`, `fname`, "
				+ "`address`, `email`, `phone`, `lname`, `sinNumber`, `role`) "
				+ "VALUES (?,?,?,?, ?,?,?,?, ?)";
		
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
		}catch(Exception e) {
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
			
			if(rowsRetured.next()) {
				res.put("isRegistered", "true");
				if(username.equals("admin")) {
					res.put("isAdmin", "true");
				}else {
					res.put("isAdmin", "false");
				}
				
			}else {
				res.put("isRegistered", "false");
				res.put("isAdmin", "false");
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	
		return res;
	}

}
