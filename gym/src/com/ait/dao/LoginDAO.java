package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ait.gym.ConnectionHelper;
import com.ait.gym.LoginUser;

public class LoginDAO {

	public LoginUser authenticateUser(String email, String password) {
		System.out.println("authenticate user");
		Connection c = null;
		LoginUser user = new LoginUser();
		
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement("SELECT * FROM GymProject.login where email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user.setLoginID(rs.getInt("loginID"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getString("userType"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return user;
	}

	public int createLogin(String email, String password, String userType) {
    	Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO Login "+ 
    		"(email, password, userType)"+
    				" VALUES (?, ?, ?)",
    				new String[] {"loginID"});
    		ps.setString(1, email);
    		ps.setString(2, password);
    		ps.setString(3, userType);
    		
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		return id;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    }
	
	
}