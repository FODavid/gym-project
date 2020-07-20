package com.ait.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ait.dao.CustomersDAO;
import com.ait.dao.LoginDAO;
import com.ait.gym.AuthUser;
import com.ait.gym.Customers;
import com.ait.gym.LoginUser;
import com.ait.gym.User;

@Path("/auth")
public class AuthenticateResource {

	LoginDAO _dao = new LoginDAO();
	CustomersDAO _custDao = new CustomersDAO();

	@GET
	@Path("/hello")
	public String getText() {
		return "hello world";
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public AuthUser authenticate(User loginUser) throws SQLException {
		System.out.println(
				"authenticate user \n Email:" + loginUser.getEmail() + "\n Password:" + loginUser.getPassword());
		LoginUser user = _dao.authenticateUser(loginUser.getEmail(), loginUser.getPassword());
		Customers cust = null;
		if (user != null) {
			if (user.getUserType().equals("customer"))
				cust = _custDao.findByLoginId(user.getLoginID());
		}

		return new AuthUser(user, cust);
	}

}