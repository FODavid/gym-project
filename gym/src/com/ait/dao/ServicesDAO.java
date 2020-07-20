package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.*;

public class ServicesDAO {

	public List<Services> findAll() {
		System.out.println("find all");
		List<Services> serviceList = new ArrayList<Services>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.services ORDER BY serviceID";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				serviceList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return serviceList;
	}

	protected Services processRow(ResultSet rs) throws SQLException {
		Services service = new Services();

		service.setServiceID(rs.getInt("serviceID"));
		service.setServiceName(rs.getString("serviceName"));
		service.setServicePrice(rs.getDouble("servicePrice"));
		service.setServiceLimit(rs.getInt("serviceLimit"));
		service.setServiceInstructorFirst(rs.getString("serviceInstructorFirst"));
		service.setServiceInstructorLast(rs.getString("serviceInstructorLast"));
		service.setCategoryID(rs.getInt("categoryID"));
		service.setStartDate(rs.getDate("startDate"));
		service.setEndDate(rs.getDate("endDate"));
		

		return service;

	}
	public Services create(Services service) {
    	Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO GymProject.services "+ 
    		"(serviceName, servicePrice, serviceLimit, serviceInstructorFirst, serviceInstructorLast, startDate, endDate, categoryID )"+
    				" VALUES (?, ?, ?, ?, ?,?,?,?)",
    				new String[] {"ID"});
    		ps.setString(1, service.getServiceName());
    		ps.setDouble(2, service.getServicePrice());
    		ps.setInt(3, service.getServiceLimit());
    		ps.setString(4, service.getServiceInstructorFirst());
    		ps.setString(5,  service.getServiceInstructorLast());
    		ps.setDate(6, new java.sql.Date(service.getStartDate().getTime()));
    		ps.setDate(7, new java.sql.Date(service.getEndDate().getTime()));
    		ps.setInt(8, service.getCategoryID());
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		service.setServiceID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return service;
    }
	 public Services update(Services service) {
	    	Connection c = null;
	    	try { 
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.services SET serviceName=?, servicePrice=?, serviceLimit=?, " +
	    				"serviceInstructorFirst=?, serviceInstructorLast=?, startDate=?, endDate=?, categoryID=?, WHERE serviceID=?");
	   
	    		ps.setString(1, service.getServiceName());
	    		ps.setDouble(2, service.getServicePrice());
	    		ps.setInt(3, service.getServiceLimit());
	    		ps.setString(4, service.getServiceInstructorFirst());
	    		ps.setString(5, service.getServiceInstructorLast());
	    		ps.setInt(6, service.getServiceID());
	    		ps.setDate(7, new java.sql.Date(service.getStartDate().getTime()));
	    		ps.setDate(8, new java.sql.Date(service.getEndDate().getTime()));
	    		ps.setInt(9, service.getCategoryID());
	    		ps.executeUpdate();
	    		
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	    		throw new RuntimeException(e);
	    	}finally {
	    		ConnectionHelper.close(c);
	    	}
	    	return service;
	    }
	 public boolean remove(int id) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.services WHERE serviceID=?");
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
