package beans;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import model.ACLMessage;

@Stateless
@Path("/messages")
@LocalBean
public class MessageBean {
	@EJB
	DBBean database;
	
	@GET
	public Collection<ACLMessage> getAllMessages() {
		return database.getAclMessages().values();
	}
	
	@POST
	public Response postACLMessage(ACLMessage aclMessage) {
		return Response.status(200).entity("OK").build();
	}
}
