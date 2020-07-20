package com.ait.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ait.dao.CustomersDAO;
import com.ait.dao.UserAccountDAO;
import com.ait.gym.Customers;
import com.ait.gym.UserAccount;

@Path("/useraccount")
public class UserAccountResources {

	UserAccountDAO dao = new UserAccountDAO();
	CustomersDAO custDAO = new CustomersDAO();
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<UserAccount> findAll(){
		System.out.println("findAll");
		return dao.findAll();
	
}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	public UserAccount create(UserAccount useraccount) throws Exception {
		System.out.println("creating useraccount");
		UserAccount userAccount = dao.create(useraccount);
		Customers cust = custDAO.findById(userAccount.getCustID());
		double curBalance = cust.getcusBalance();
		cust.setcusBalance(curBalance + userAccount.getAmountPaid());
		
		//update customer balance
		custDAO.update(cust);
		
		return userAccount;
		
	}
	
	@PUT @Path("{custID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserAccount update(UserAccount useraccount) {
		System.out.println("Updating useraccount: " +useraccount.getCustID());
		dao.update(useraccount);
		return useraccount;
		
	}
	@DELETE @Path("{custID}")
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public void remove(@PathParam("custID") int id) {
		dao.remove(id);
	}
	
	
	
	
	
	
}

