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

@Path("/services")
public class ServicesResource {

	ServicesDAO dao = new ServicesDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Services> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public Services create(Services service) {
		System.out.println("creating service");
		return dao.create(service);
	}

	@PUT
	@Path("{serviceID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Services update(Services service) {
		System.out.println("Updating staff: " + service.getServiceName());
		dao.update(service);
		return service;
	}

	@DELETE
	@Path("{serviceID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("serviceID") int id) {
		dao.remove(id);
	}
}