package agents;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.JMSBuilder;
import model.Performative;
import ws.WSEndpoint;

@Stateful
public class PongAgent extends Agent{
	
	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.REQUEST) {
			try {
				Context context = new InitialContext();
				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
				ws.echoTextMessage(this.aid.getName() + " recieved message from " + poruka.getSender().getName() + ": " + poruka.getContent());
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			ACLMessage response = new ACLMessage();
			response.setReceivers(new AID[] { poruka.getSender() });
			response.setPerformative(Performative.INFORM);
			response.setContent("Reply to message received from " + poruka.getSender().getName());
			response.setSender(aid);			
			JMSBuilder.sendACL(response);
		}

	}

}