package agents;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.AgentType;
import model.JMSBuilder;
import model.Node;
import model.Performative;
import ws.WSEndpoint;

@Stateful
public class PingAgent extends Agent{
	
	@Override
	public void handleMessage(ACLMessage message) {
		if (message.getPerformative() == Performative.REQUEST) {
			
			try {
				Context context = new InitialContext();
				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
				ws.echoTextMessage(this.aid.getName() + " recieved message from " + message.getSender().getName() + ": " + message.getContent());
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			AID receiver = new AID();
			receiver.setName(message.getSender().getName());
			System.out.println("Request to send message " + message.getContent() + " to Pong.");
			AgentType type = new AgentType(PongAgent.class.getSimpleName(), PongAgent.class.getPackage().getName());
			receiver.setType(type);
			Node host = lookupHost();
			if (host == null) {
				System.out.println("Error: Cannot locate host");
				return;
			}
			receiver.setHost(host);
			ACLMessage msg = new ACLMessage();
			msg.setPerformative(Performative.REQUEST);
			msg.setReceivers(new AID[] { receiver });
			msg.setSender(this.getAid());
			msg.setContent(message.getContent());
			JMSBuilder.sendACL(msg);
		} else if (message.getPerformative() == Performative.INFORM) {
			try {
				Context context = new InitialContext();
				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
				ws.echoTextMessage(this.aid.getName() + " recieved message from " + message.getSender().getName() + ": " + message.getContent());
			} catch (NamingException e) {
				e.printStackTrace();
			}			System.out.println("Reply received from  " + message.getSender());
			System.out.println("Reply content: " + message.getContent());
		} 
		
	}
	
	private Node lookupHost() {
		try {
			Context ctx = new InitialContext();
			return (Node) ctx.lookup(Node.LOOKUP);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}