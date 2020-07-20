package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.*;

public class MembershipDAO {

	public List<Memberships> findAll() {
		System.out.println("find all");
		List<Memberships> membershipList = new ArrayList<Memberships>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.memberships ORDER BY serviceID";
				
//				"SELECT serviceName, custFirst, custLast from GymProject.customers inner join GymProject.memberships on memberships.custID = customers.custID "+
//		"inner join GymProject.services on memberships.serviceID = services.serviceID order by services.serviceID";
		
		//select serviceName, custFirst, custLast from customers 
//		inner join memberships on memberships.custID = customers.custID 
//				inner join services on memberships.serviceID = services.serviceID
//				order by services.serviceID;
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				membershipList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return membershipList;
	}

	protected Memberships processRow(ResultSet rs) throws SQLException {
		Memberships membership = new Memberships();

		membership.setMembID(rs.getInt("membID"));
		membership.setCustID(rs.getInt("custID"));
		membership.setPaid(rs.getBoolean("paid"));
		membership.setDateJoined(rs.getDate("dateJoined"));
		membership.setamountPaid(rs.getDouble("amountPaid"));
		
		return membership;

	}
	
	public Memberships findById(int id) throws SQLException {
		String sql = "SELECT * FROM GymProject.memberships WHERE membID=?";
		Memberships memberships= null;
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs =ps.executeQuery();
			if (rs.next()) {
				memberships = processRow(rs);
			}
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally {
			ConnectionHelper.close(c);
		}
		return memberships;
	}
	
	
	public Memberships create(Memberships membership) {
    	Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO GymProject.memberships "+ 
    		"(custID, paid, dateJoined, amountPaid)"+
    				" VALUES (?, ?, ?, ?)",
    				new String[] {"ID"});
    		ps.setInt(1, membership.getCustID());
    		ps.setBoolean(2, membership.isPaid());
    		ps.setDate(3, new java.sql.Date(membership.getDateJoined().getTime()));
    		ps.setDouble(4, membership.getamountPaid());
    		
    	
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		membership.setMembID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return membership;
    }
	 public Memberships update(Memberships  membership) {
	    	Connection c = null;
	    	try { 
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.memberships SET custID=?, paid=?, amountPaid=?" +
	    				"WHERE membID=?");
	   
	    		ps.setInt(1, membership.getCustID());
	    		ps.setBoolean(2, membership.isPaid());
	    		ps.setInt(3, membership.getMembID());
	    		ps.setDouble(4, membership.getamountPaid());
	    		
	    		ps.executeUpdate();
	    		
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	}finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return membership;
	    }
	 
	 public boolean remove(int id) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.memberships WHERE membID=?");
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
