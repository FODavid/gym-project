package com.ait.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ait.gym.ConnectionHelper;

import com.ait.gym.UserAccount;

public class UserAccountDAO {
	
	public List<UserAccount> findAll(){
		System.out.println("find all");
		List<UserAccount> userAccountList = new ArrayList<UserAccount>();
		Connection c = null;
		String sql = "SELECT * FROM GymProject.useraccount";
		try {
			c = ConnectionHelper.getConnection();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				userAccountList.add(processRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
		return userAccountList;
			
	}
	protected UserAccount processRow(ResultSet rs) throws SQLException {
		UserAccount userAccount= new UserAccount();
		
		userAccount.setUserAccountID(rs.getInt("userAccountID"));
		userAccount.setCustID(rs.getInt("custID"));
		userAccount.setAmountPaid(rs.getDouble("amountPaid"));
		userAccount.setDatePaid(rs.getDate("datePaid"));
		userAccount.setBonusCredit(rs.getBoolean("isBonus"));
		userAccount.setRemark(rs.getString("remark"));
		
		return userAccount;
	
	
	}
	
	
	public UserAccount create(UserAccount userAccount) {
		Connection c = null;
    	PreparedStatement ps = null;
    	try { 
    		c = ConnectionHelper.getConnection();
    		ps = c.prepareStatement("INSERT INTO useraccount "+ 
    		"(custID, amountPaid, datePaid, isBonus, remark)"+
    				" VALUES (?, ?, ?, ?, ?)",
    				new String[] {"UserAccountID"});
    		ps.setInt(1, userAccount.getCustID());
    		ps.setDouble(2, userAccount.getAmountPaid());
    		ps.setDate(3, new java.sql.Date(userAccount.getDatePaid().getTime()));
    		ps.setBoolean(4, userAccount.isBonusCredit());
    		ps.setString(5, userAccount.getRemark());
    		
    		ps.executeUpdate();
    		ResultSet rs = ps.getGeneratedKeys();
    		rs.next();
    		
    		int id = rs.getInt(1);
    		userAccount.setUserAccountID(id);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}finally {
    		ConnectionHelper.close(c);
    	}
    	return userAccount;
    		
    	}
	
	public UserAccount update(UserAccount userAccount) {
		Connection c = null;
		try {
			c= ConnectionHelper.getConnection();
			PreparedStatement ps= c.prepareStatement("UPDATE GymProject.userAccount SET custID=?, amountPaid=?, "
					+ "datePaid=?, isBonus=?, remark=? , WHERE userAccountID=?");
			
			ps.setInt(1, userAccount.getCustID());
			ps.setDouble(2, userAccount.getAmountPaid());
			ps.setDate(3, new java.sql.Date(userAccount.getDatePaid().getTime()));
			ps.setBoolean(4, userAccount.isBonusCredit());
			ps.setString(5, userAccount.getRemark());
			ps.setInt(6, userAccount.getUserAccountID());
			ps.executeUpdate();
					
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}finally {
			ConnectionHelper.close(c);
		}
		return userAccount;
	}
	
	public boolean remove(int id) {
		Connection c = null;
		try {
			c = ConnectionHelper.getConnection();
			PreparedStatement ps = c.prepareStatement("DELETE FROM GymProject.userAccount WHERE userAccountID=?");
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			return count == 1;
		}catch  (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		finally {
			ConnectionHelper.close(c);
		}
	}
	
	}
	


