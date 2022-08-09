package com.project.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.model.Account;
import com.project.util.DatabaseConnection;
public class AccountHolder {
	@Autowired
	DatabaseConnection dbConnection;
	
	public List<Account> displayAllrecords()
	{
		List<Account> list=new ArrayList<Account>();
		Connection con=dbConnection.getConnection();
		String sql="select * from account";

		//For Select statement we can use Connection Interface
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				Account acc=new Account(rs.getString("username"),rs.getString("accountNo"),rs.getString("accountType"),rs.getString("accountBalance"));
				list.add(acc);
				System.out.println(list);
			}		
		} 	
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;	
	}

	public void insertRec(Account acc_tobe_inserted)
	{
		
		Connection con=dbConnection.getConnection();
		String sql="insert into account(accountNo,accountType,accountBalance,username) values(?,?,?,?)";
	    try {
			PreparedStatement ptsmt=con.prepareStatement(sql);
			//Set the values of ?
			ptsmt.setString(1, acc_tobe_inserted.getAccountNo());
			ptsmt.setString(4, acc_tobe_inserted.getUsername());
			ptsmt.setString(2, acc_tobe_inserted.getAccountType());
			ptsmt.setString(3, acc_tobe_inserted.getAccountBalance());
			int status=ptsmt.executeUpdate();
			if(status>0)
			{
				System.out.println("Record inserted successfully");
				displayAllrecords();
			}
			else
			{
				System.out.println("Please try again");
			}
			
		} 
	      
	    catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void updateRecById(Account upd_acc, String account_to_beUpdated)
	{
	Connection con = dbConnection.getConnection();
	String sql="update account set accountNo=?, accountType=?, accountBalance=?, username=? where accountNo =?";
	try {
		PreparedStatement ptsmt=con.prepareStatement(sql);
		ptsmt.setString(1, upd_acc.getAccountNo());
		ptsmt.setString(4, upd_acc.getUsername());
		ptsmt.setString(2, upd_acc.getAccountType());
		ptsmt.setString(3, upd_acc.getAccountBalance());
		ptsmt.setString(5, account_to_beUpdated);
		int status=ptsmt.executeUpdate();
		System.out.println(status);
		if(status>0)
		{
			System.out.println("Record updated successfully");
			displayAllrecords();
		}
		else
		{
			System.out.println("Please try updating again");
		}
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	

	}

	public void deleteRecById(String acc_to_beUpdated)
	{
	Connection con = dbConnection.getConnection();
	String sql="delete from account where accountNo=?";
	try {
		PreparedStatement ptsmt=con.prepareStatement(sql);
		ptsmt.setString(1, acc_to_beUpdated);

		int status=ptsmt.executeUpdate();
		if(status>0)
		{
			System.out.println("Record deleted successfully");
			System.out.println(displayAllrecords());
		}
		else
		{
			System.out.println("Please try deleting again");
		}
	} 
	catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
		
	}


}
