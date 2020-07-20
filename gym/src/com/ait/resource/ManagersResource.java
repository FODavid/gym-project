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

@Path("/managers")
public class ManagersResource {

	ManagersDAO dao = new ManagersDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Managers> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public Managers create(Managers manager) {
		System.out.println("creating manager");
		return dao.create(manager);
	}

	@PUT
	@Path("{managerID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Managers update(Managers manager) {
		System.out.println("Updating manager: " + manager.getManagerFirst());
		dao.update(manager);
		return manager;
	}

	@DELETE
	@Path("{managerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("managerID") int id) {
		dao.remove(id);
	}
}