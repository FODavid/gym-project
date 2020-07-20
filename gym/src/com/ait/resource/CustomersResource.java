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

import com.ait.dao.*;
import com.ait.gym.*;

@Path("/customers")
public class CustomersResource {

	CustomersDAO dao = new CustomersDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Customers> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public Customers create(Customers customer) {
		System.out.println("creating customer");
		return dao.create(customer);
	}

	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Customers findById(@PathParam("custID") String id) throws Exception {
		System.out.println("findById + custID");
		return dao.findById(Integer.parseInt(id));
	}

	@PUT
	@Path("{custID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Customers update(Customers customer) {
		System.out.println("Updating customer: " + customer.getCustFirst());
		dao.update(customer);
		return customer;
	}

	@DELETE
	@Path("{custID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("custID") int id) {
		dao.remove(id);
	}
}