package com.ait.resource;

import java.sql.SQLException;
import java.util.Date;
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

@Path("/memberships")
public class MembershipResource {

	MembershipDAO dao = new MembershipDAO();
	UserAccountDAO userAcctDAO = new UserAccountDAO();
	CustomersDAO custDAO = new CustomersDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Memberships> findAll() {
		System.out.println("findAll");
		return dao.findAll();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Memberships findById(@PathParam("membID") String id) throws SQLException {
		System.out.println("findById + membID");
		return dao.findById(Integer.parseInt(id));

	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_ATOM_XML })
	public Memberships create(Memberships membership) throws SQLException {
		System.out.println("creating membership");

		int custId = membership.getCustID();

		// get customer with id
		Customers customer = custDAO.findById(custId);

		// add membership
		membership.setPaid(true);
		membership.setDateJoined(new Date());
		membership.setamountPaid(200);

		membership = dao.create(membership);

		// add 200 membership payment to customer account
		UserAccount memberPayment = new UserAccount();
		memberPayment.setAmountPaid(200);
		memberPayment.setBonusCredit(false);
		memberPayment.setCustID(custId);
		memberPayment.setRemark("Membership fee");
		memberPayment.setDatePaid(new Date());
		userAcctDAO.create(memberPayment);

		// add 50 membership bonus to customer account
		UserAccount userAccount = new UserAccount();
		userAccount.setAmountPaid(50);
		userAccount.setBonusCredit(true);
		userAccount.setCustID(custId);
		userAccount.setRemark("Membership bonus");
		userAccount.setDatePaid(new Date());
		userAcctDAO.create(userAccount);

		// update customer balance with payments made
		Double balance = customer.getcusBalance() + 250;
		customer.setcusBalance(balance);
		customer.setCustMember(true);
		custDAO.update(customer);

		return membership;

	}

	@PUT
	@Path("{membID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Memberships update(Memberships membership) {
		System.out.println("Updating membership ");
		dao.update(membership);
		return membership;
	}

	@DELETE
	@Path("{membID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void remove(@PathParam("membID") int id) {
		dao.remove(id);
	}
}
