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
import com.ait.dao.UserClassesDAO;
import com.ait.gym.Customers;
import com.ait.gym.UserClasses;

@Path("/userclass")
public class UserClassesResource {

	UserClassesDAO dao = new UserClassesDAO();
	CustomersDAO custDAO = new CustomersDAO();
	
	@GET
	@Path("{custId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public List<UserClasses> findCustomerStartedClass(@PathParam("custId") int custId) throws Exception{
		System.out.println("findAllByCustId");
		return dao.findCustomerStartedClass(custId);
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML})
	public UserClasses create(UserClasses userclasses) throws Exception {
		System.out.println("creating userclasses");
		UserClasses userClass = dao.create(userclasses);
		
		Customers cust = custDAO.findById(userclasses.getCustID());
		double curBalance = cust.getcusBalance();
		cust.setcusBalance(curBalance - userclasses.getExpectedAmount());
		
		//update customer balance
		custDAO.update(cust);
		return userClass;
	}
	
	@PUT @Path("{custID}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public UserClasses update(UserClasses userclasses) {
		System.out.println("Updating userclasses: " + userclasses.getCustID());
		dao.update(userclasses);
		return userclasses;
	}

	@DELETE @Path("{custID}")
	@Produces ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	public void remove(@PathParam ("custID") int id) {
		dao.remove(id);
	}
	
	
	
	
}

