package com.project.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.model.UtilityProvider;
import com.project.util.DatabaseConnection;

public class UtiliityProvider {
	@Autowired
	DatabaseConnection dbConnection;
		
		public List<UtilityProvider> displayAllrecords()
		{
			List<UtilityProvider> list=new ArrayList<UtilityProvider>();
			Connection con=dbConnection.getConnection();
			String sql="select * from utilityprovider";

			//For Select statement we can use Connection Interface
			try {
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					UtilityProvider uti=new UtilityProvider(rs.getString("providerName"),rs.getString("type"),rs.getString("username"),rs.getString("utilityAcc"));
					list.add(uti);
					System.out.println(list);
				}		
			} 	
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return list;	
		}

		public void insertRec(UtilityProvider acc_tobe_inserted)
		{
			
			Connection con=dbConnection.getConnection();
			String sql="insert into utilityprovider(providerName,type,username,utilityAcc) values(?,?,?,?)";
		    try {
				PreparedStatement ptsmt=con.prepareStatement(sql);
				//Set the values of ?
				ptsmt.setString(1, acc_tobe_inserted.getProviderName());
				ptsmt.setString(4, acc_tobe_inserted.getUtilityAcc());
				ptsmt.setString(2, acc_tobe_inserted.getType());
				ptsmt.setString(3, acc_tobe_inserted.getUsername());
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

		public void updateRecById(UtilityProvider upd_util, String account_to_beUpdated)
		{
		Connection con = dbConnection.getConnection();
		String sql="update utilityprovider set providerName=?, type=?, 	username=?, utilityAcc=? where utilityAcc=?";
		try {
			PreparedStatement ptsmt=con.prepareStatement(sql);
			ptsmt.setString(1, upd_util.getProviderName());
			ptsmt.setString(4, upd_util.getUtilityAcc());
			ptsmt.setString(2, upd_util.getType());
			ptsmt.setString(3, upd_util.getUsername());
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

		public void deleteRecById(String util_to_beUpdated)
		{
		Connection con = dbConnection.getConnection();
		String sql="delete from utilityprovider where id =?";
		try {
			PreparedStatement ptsmt=con.prepareStatement(sql);
			ptsmt.setString(1, util_to_beUpdated);

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
