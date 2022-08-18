package com.project.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.model.User;
import com.project.util.DatabaseConnection;

public class UserDB {
	@Autowired
	DatabaseConnection dbConnection;

	public List<User> displayAllrecords() {
		List<User> list = new ArrayList<User>();
		Connection con = dbConnection.getConnection();
		String sql = "select * from user";

		// For Select statement we can use Connection Interface
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("fname"),
						rs.getString("address"), rs.getString("email"), rs.getString("phone"), rs.getString("lname"),
						rs.getString("sinNumber"), rs.getString("role"));
				list.add(user);
				System.out.println(list);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void insertRec(User user_tobe_inserted) {

		Connection con = dbConnection.getConnection();
		String sql = "insert into user(username,password,fname,address,email,phone,lname,sinNumber,role) values(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ptsmt = con.prepareStatement(sql);
			// Set the values of ?
			ptsmt.setString(1, user_tobe_inserted.getUsername());
			ptsmt.setString(2, user_tobe_inserted.getPassword());
			ptsmt.setString(3, user_tobe_inserted.getFname());
			ptsmt.setString(4, user_tobe_inserted.getAddress());
			ptsmt.setString(5, user_tobe_inserted.getEmail());
			ptsmt.setString(6, user_tobe_inserted.getPhone());
			ptsmt.setString(7, user_tobe_inserted.getLname());
			ptsmt.setString(8, user_tobe_inserted.getSinNumber());
			ptsmt.setString(9, user_tobe_inserted.getRole());
			int status = ptsmt.executeUpdate();
			if (status > 0) {
				System.out.println("Record inserted successfully");
				displayAllrecords();
			} else {
				System.out.println("Please try again");
			}

		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateRecById(User user_tobe_updated, String account_to_beUpdated) {
		Connection con = dbConnection.getConnection();
		String sql = "update user set username=?, password=?, fname=?, address=?,email=?,phone=?,lname=?,sinNumber=?,role=? where username=?";
		try {
			PreparedStatement ptsmt = con.prepareStatement(sql);
			ptsmt.setString(1, user_tobe_updated.getUsername());
			ptsmt.setString(2, user_tobe_updated.getPassword());
			ptsmt.setString(3, user_tobe_updated.getFname());
			ptsmt.setString(4, user_tobe_updated.getAddress());
			ptsmt.setString(5, user_tobe_updated.getEmail());
			ptsmt.setString(6, user_tobe_updated.getPhone());
			ptsmt.setString(7, user_tobe_updated.getLname());
			ptsmt.setString(8, user_tobe_updated.getSinNumber());
			ptsmt.setString(9, user_tobe_updated.getRole());
			ptsmt.setString(10, account_to_beUpdated);
			int status = ptsmt.executeUpdate();
			System.out.println(status);
			if (status > 0) {
				System.out.println("Record updated successfully");
				displayAllrecords();
			} else {
				System.out.println("Please try updating again");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteRecById(String user_to_beUpdated) {
		Connection con = dbConnection.getConnection();
		String sql = "delete from user where username=?";
		try {
			PreparedStatement ptsmt = con.prepareStatement(sql);
			ptsmt.setString(1, user_to_beUpdated);

			int status = ptsmt.executeUpdate();
			if (status > 0) {
				System.out.println("Record deleted successfully");
				System.out.println(displayAllrecords());
			} else {
				System.out.println("Please try deleting again");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
