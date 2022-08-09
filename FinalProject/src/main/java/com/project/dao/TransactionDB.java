package com.project.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.model.Transaction;
import com.project.util.DatabaseConnection;
public class TransactionDB {
	@Autowired
	DatabaseConnection dbConnection;
	
		public List<Transaction> displayAllrecords()
		{
			List<Transaction> list=new ArrayList<Transaction>();
			Connection con=dbConnection.getConnection();
			String sql="select * from transaction";

			//For Select statement we can use Connection Interface
			try {
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					Transaction trans=new Transaction(rs.getString("transactionId"),rs.getString("fromAccount"),rs.getString("toAccount"),rs.getString("status"));
					list.add(trans);
					System.out.println(list);
				}		
			} 	
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return list;	
		}

		public void insertRec(Transaction trans_tobe_inserted)
		{
			
			Connection con=dbConnection.getConnection();
			String sql="insert into transaction(transactionId,fromAccount,toAccount,status) values(?,?,?,?)";
		    try {
				PreparedStatement ptsmt=con.prepareStatement(sql);
				//Set the values of ?
				ptsmt.setString(1, trans_tobe_inserted.getTransactionId());
				ptsmt.setString(2, trans_tobe_inserted.getFromAccount());
				ptsmt.setString(3, trans_tobe_inserted.getToAccount());
				ptsmt.setString(4, trans_tobe_inserted.getStatus());
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

		public void updateRecById(Transaction trans_tobe_updated, String account_to_beUpdated)
		{
		Connection con = dbConnection.getConnection();
		String sql="update transaction set transactionId=?,fromAccount=?, toAccount=?, status=? where transactionId=?";
		try {
			PreparedStatement ptsmt=con.prepareStatement(sql);
			ptsmt.setString(1, trans_tobe_updated.getTransactionId());
			ptsmt.setString(2, trans_tobe_updated.getFromAccount());
			ptsmt.setString(3, trans_tobe_updated.getToAccount());
			ptsmt.setString(4, trans_tobe_updated.getStatus());
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
		String sql="delete from transction where transactionId =?";
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
