package beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.ACLMessageDTO;
import model.ACLMessage;
import model.AID;
import model.Agent;
import model.Performative;

@Stateless
@Path("/messages")
@LocalBean
public class MessageBean {
	@EJB
	DBBean database;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:jboss/exported/jms/queue/mojQueue")
	Queue queue;
	
	public MessageBean() {}
	
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response postACLMessage(ACLMessageDTO mssg) {
		ACLMessage aclMessage = new ACLMessage();
		Agent reciever = database.getAgentsRunning().get(mssg.getReceiver());
		Agent s = database.getAgentsRunning().get(mssg.getSender());
		Performative performative = Performative.valueOf(mssg.getPerformative());
		aclMessage.setContent(mssg.getContent());
		AID recievers[] = {reciever.getAid()};
		aclMessage.setReceivers(recievers);
		aclMessage.setSender(s.getAid());
		aclMessage.setPerformative(performative);
		
		try {
			QueueConnection connection = (QueueConnection) connectionFactory.createConnection("guest", "guest.guest.1");
			QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			QueueSender sender = session.createSender(queue);
			ObjectMessage object = session.createObjectMessage(aclMessage);
			sender.send(object);
			
			return Response.status(201).entity("Message sent").build();
		} catch (JMSException e) {
			System.out.println("Failed to send JMS message to queue");
			e.printStackTrace();
		}
		return Response.status(500).entity("Unable to send message to queue").build();
	}
}
