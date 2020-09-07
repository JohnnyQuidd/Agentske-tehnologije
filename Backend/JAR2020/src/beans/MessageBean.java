package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.ACLMessage;
import model.AgentType;
import model.Performative;

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
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerformatives() {
		List<String> performatives = new ArrayList<String>();
		for(Enum<Performative> p : Performative.values()) {
			performatives.add(p.toString());
		}
		
		return Response.status(200).entity(performatives).build();
	}
	
	@POST
	public Response postACLMessage(ACLMessage aclMessage) {
		return Response.status(200).entity("OK").build();
	}
}
