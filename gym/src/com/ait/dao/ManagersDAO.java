package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.*;


public class ManagersDAO {

	public List<Managers> findAll() {
		System.out.println("find all");
		List<Managers> managerList = new ArrayList<Managers>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.manager ORDER BY managerLast";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				managerList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return managerList;
	}

	protected Managers processRow(ResultSet rs) throws SQLException {
		Managers manager = new Managers();

		manager.setManagerID(rs.getInt("managerID"));
		manager.setManagerFirst(rs.getString("managerFirst"));
		manager.setManagerLast(rs.getString("managerLast"));
		manager.setManagerEmail(rs.getString("managerEmail"));
		manager.setManagerPassword(rs.getString("managerPassword"));

		return manager;

	}
	 public Managers create(Managers manager) {
	    	Connection c = null;
	    	PreparedStatement ps = null;
	    	try { 
	    		c = ConnectionHelper.getConnection();
	    		ps = c.prepareStatement("INSERT INTO GymProject.manager "+ 
	    		"(managerFirst, managerLast, managerEmail, managerPassword)"+
	    				" VALUES (?, ?, ?, ?)",
	    				new String[] {"ID"});
	    		ps.setString(1, manager.getManagerFirst());
	    		ps.setString(2, manager.getManagerLast());
	    		ps.setString(3, manager.getManagerEmail());
	    		ps.setString(4, manager.getManagerPassword());
	    		ps.executeUpdate();
	    		ResultSet rs = ps.getGeneratedKeys();
	    		rs.next();
	    		
	    		int id = rs.getInt(1);
	    		manager.setManagerID(id);
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	}finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return manager;
	    }
	
	 public Managers update(Managers manager) {
	    	Connection c = null;
	    	try { 
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.manager SET managerFirst=?, managerLast=?, managerEmail=?, " +
	    				"managerPassword=? WHERE managerID=?");
	   
	    		ps.setString(1, manager.getManagerFirst());
	    		ps.setString(2, manager.getManagerLast());
	    		ps.setString(3, manager.getManagerEmail());
	    		ps.setString(4, manager.getManagerPassword());
	    		ps.setInt(5, manager.getManagerID());
	    		ps.executeUpdate();
	    		
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	}finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return manager;
	    }
	 public boolean remove(int id) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.manager WHERE managerID=?");
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
