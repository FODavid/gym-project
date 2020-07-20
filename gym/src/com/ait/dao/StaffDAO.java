package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.*;

public class StaffDAO {

	public List<Staff> findAll() {
		System.out.println("find all");
		List<Staff> staffList = new ArrayList<Staff>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.staff ORDER BY staffID";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				staffList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return staffList;
	}

	protected Staff processRow(ResultSet rs) throws SQLException {
		Staff staff = new Staff();

		staff.setStaffID(rs.getInt("staffID"));
		staff.setStaffFirst(rs.getString("staffFirst"));
		staff.setStaffLast(rs.getString("staffLast"));
		staff.setStaffEmail(rs.getString("staffEmail"));
		staff.setStaffPassword(rs.getString("staffPassword"));

		return staff;

	}
	public Staff create(Staff staff) {
    	Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO GymProject.staff "+ 
    		"(staffFirst, staffLast, staffEmail, staffPassword)"+
    				" VALUES (?, ?, ?, ?)",
    				new String[] {"ID"});
    		ps.setString(1, staff.getStaffFirst());
    		ps.setString(2, staff.getStaffLast());
    		ps.setString(3, staff.getStaffEmail());
    		ps.setString(4, staff.getStaffPassword());
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		staff.setStaffID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return staff;
	}
	 public Staff update(Staff staff) {
	    	Connection c = null;
	    	try { 
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.staff SET staffFirst=?, staffLast=?, staffEmail=?, " +
	    				"staffPassword=? WHERE staffID=?");
	   
	    		ps.setString(1, staff.getStaffFirst());
	    		ps.setString(2, staff.getStaffLast());
	    		ps.setString(3, staff.getStaffEmail());
	    		ps.setString(4, staff.getStaffPassword());
	    		ps.setInt(5, staff.getStaffID());
	    		ps.executeUpdate();
	    		
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	}finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return staff;
	    }
	 public boolean remove(int id) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.staff WHERE staffID=?");
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
