package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.*;
//test
//test-oladejo
//keex

public class CustomersDAO {
	
	private LoginDAO _loginDAO = new LoginDAO();

	public List<Customers> findAll() {
		System.out.println("find all");
		List<Customers> custList = new ArrayList<Customers>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.customers ORDER BY custLast";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				custList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return custList;
	}

	protected Customers processRow(ResultSet rs) throws SQLException {
		Customers customer = new Customers();

		customer.setCustID(rs.getInt("custID"));
		customer.setCustFirst(rs.getString("custFirst"));
		customer.setCustLast(rs.getString("custLast"));
		customer.setCustPhone(rs.getString("custPhone"));
		customer.setCustEmail(rs.getString("custEmail"));
		customer.setCustPass(rs.getString("custPass"));
		customer.setCustMember(rs.getBoolean("custMember"));
		customer.setCategoryID(rs.getInt("categoryID"));
		customer.setcusBalance(rs.getDouble("cusBalance"));

		return customer;

	}
	
	
	public Customers create(Customers customer) {
    	Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		
    	 int loginId = _loginDAO.createLogin(customer.getCustEmail(), 
    			 customer.getCustPass(),"customer");
    		
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO customers "+ 
    		"(custFirst, custLast, custPhone, custEmail, custPass, custMember, categoryID, loginId, cusBalance)"+
    				" VALUES (?, ?, ?, ?, ?, ?,?,?, ?)",
    				new String[] {"ID"});
    		
    		ps.setString(1, customer.getCustFirst());
    		ps.setString(2, customer.getCustLast());
    		ps.setString(3, customer.getCustPhone());
    		ps.setString(4, customer.getCustEmail());
    		ps.setString(5, customer.getCustPass());
    		ps.setBoolean(6, customer.isCustMember());
    		ps.setInt(7, customer.getCategoryID());
    		ps.setInt(8, loginId);
    		ps.setDouble(9, customer.getcusBalance());
    		
    	
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		customer.setCustID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return customer;
    }
	
	public Customers update(Customers customer) {
    	Connection c = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = c.prepareStatement("UPDATE GymProject.customers SET custFirst= ?, custLast= ?, custPhone= ?, " +
    		"custEmail= ?, custPass= ?, custMember= ?, categoryID= ?, cusBalance= ? WHERE custID= ?");
   
    		ps.setString(1, customer.getCustFirst());
    		ps.setString(2, customer.getCustLast());
    		ps.setString(3, customer.getCustPhone());
    		ps.setString(4, customer.getCustEmail());
    		ps.setString(5, customer.getCustPass());
    		ps.setBoolean(6, customer.isCustMember());
    		ps.setInt(7, customer.getCategoryID());
    		ps.setDouble(8, customer.getcusBalance());
    		ps.setInt(9, customer.getCustID());
    		ps.executeUpdate();
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return customer;
    }
	 public boolean remove(int id) {
	    	Connection c = null;
	    	try {
	    		c = ConnectionHelper.getConnection();
	    		PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.customers WHERE custID=?");
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
	 
	 public Customers findById(int id) throws SQLException {
		 String sql = "SELECT * FROM GymProject.customers WHERE custID=? ";
		 Customers customers = null;
		 Connection c = null;
		 
		 try {
			 c = ConnectionHelper.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ps.setInt(1, id);
			 ResultSet rs = ps.executeQuery();
			 if (rs.next()) {
				 customers = processRow(rs);
			 }
		 }catch (NumberFormatException e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 }finally {
			 ConnectionHelper.close(c);
		 }
		 return customers;
	 }
	 
	 public Customers findByLoginId(int id) throws SQLException {
		 String sql = "SELECT * FROM GymProject.customers WHERE loginID=? ";
		 Customers customers = null;
		 Connection c = null;
		 
		 try {
			 c = ConnectionHelper.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ps.setInt(1, id);
			 ResultSet rs = ps.executeQuery();
			 if (rs.next()) {
				 customers = processRow(rs);
			 }
		 }catch (NumberFormatException e) {
			 e.printStackTrace();
			 throw new RuntimeException(e);
		 }finally {
			 ConnectionHelper.close(c);
		 }
		 return customers;
	 }
	 
	 
}
