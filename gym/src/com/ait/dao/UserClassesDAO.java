package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ait.gym.ConnectionHelper;

import com.ait.gym.UserClasses;


public class UserClassesDAO {
	
	public List<UserClasses> findAll(){
		System.out.println("find all");
		List<UserClasses> userClassesList = new ArrayList<UserClasses>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.userclasses";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				userClassesList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return userClassesList;
		}
	
	public List<UserClasses> findCustomerStartedClass(int custId){
		System.out.println("find all");
		List<UserClasses> userClassesList = new ArrayList<UserClasses>();
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			
			PreparedStatement ps = c.prepareStatement("SELECT * FROM GymProject.userclasses "
					+ "where custID=? and endDate > ?");
   
    		ps.setInt(1, custId);
    		ps.setDate(2, new java.sql.Date(new Date().getTime()));
    		ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				userClassesList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return userClassesList;
		}
	
	
	protected UserClasses processRow(ResultSet rs) throws SQLException {
		UserClasses userClasses= new UserClasses();
		
		userClasses.setUserClassesID(rs.getInt("userClassesID"));
		userClasses.setCustID(rs.getInt("custID"));
		userClasses.setServiceID(rs.getInt("serviceID"));
		userClasses.setStartDate(rs.getDate("startDate"));
		userClasses.setEndDate(rs.getDate("endDate"));
		userClasses.setExpectedAmount(rs.getDouble("expectedAmount"));
		userClasses.setOriginalAmount(rs.getDouble("originalAmount"));
		userClasses.setWalkinCus(rs.getBoolean("walkinCus"));
		
		
		return userClasses;
	}
	
	public UserClasses create(UserClasses userClasses) {
		Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO userclasses "+ 
    		"(custID, serviceID, startDate, endDate, expectedAmount, originalAmount, walkinCus)"+
    				" VALUES ( ?, ?, ?, ?, ?, ?, ?)",
    				new String[] {"userClassesID"});
    		
    		ps.setInt(1, userClasses.getCustID());
    		ps.setInt(2, userClasses.getServiceID());
    		ps.setDate(3, new java.sql.Date(userClasses.getStartDate().getTime()));
    		ps.setDate(4, new java.sql.Date(userClasses.getEndDate().getTime()));
    		ps.setDouble(5, userClasses.getExpectedAmount());
    		ps.setDouble(6, userClasses.getOriginalAmount());
    		ps.setBoolean(7, userClasses.isWalkinCus());
    		
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		userClasses.setUserClassesID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return userClasses;
    		
    	}
	
	
	public UserClasses update(UserClasses userClasses) {
		Connection c = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.userclasses SET userClassesID= ?, custID=?, serviceID=?, startDate=?, " +
    		"endDate=?, expectedAmount=?, originalAmount=?,walkinCus=? WHERE userclassesID=?");
    		
    		ps.setInt(1, userClasses.getUserClassesID());
    		ps.setInt(2, userClasses.getCustID());
    		ps.setInt(3, userClasses.getServiceID());
    		ps.setDate(4, new java.sql.Date(userClasses.getStartDate().getTime()));
    		ps.setDate(5, new java.sql.Date(userClasses.getEndDate().getTime()));
    		ps.setDouble(6, userClasses.getExpectedAmount());
    		ps.setDouble(7, userClasses.getOriginalAmount());
    		ps.setBoolean(8, userClasses.isWalkinCus());
    	}
    		
    		catch(Exception e) {
        		e.printStackTrace();
        		throw new RuntimeException(e);
        	}finally {
        		ConnectionHelper.close(c);
        	}
        	return userClasses;
    	}
	
	public boolean remove(int id) {
		Connection c = null;
    	try {
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.userclasses WHERE userClassesID=?");
    		ps.setInt(1, id);
    		int count = ps.executeUpdate();
    		return count == 1;
    	}catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
    	finally {
    		ConnectionHelper.close(c);
    	}
	}
	
	}
	
