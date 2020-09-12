package agents;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.JMSBuilder;
import model.Performative;
import ws.WSEndpoint;

public class CNPManagerAgent extends Agent{

	@Override
	public void handleMessage(ACLMessage message) {
		switch (message.getPerformative()) {
		case CALL_FOR_PROPOSAL:
			callForProposal(message);
			break;
		case REJECT:
			rejectProposal(message);
			break;
		case ACCEPT:
			acceptProposal(message);
			break;
		default:
			System.out.println("Unexpected message!");
			try {
				Context context = new InitialContext();
				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
				ws.echoTextMessage("Unexpected performative for CNP agents.");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void callForProposal(ACLMessage msg) {
		ACLMessage reply = new ACLMessage();
		reply.setSender(aid);
//		if (Math.random() < 0.5) {
//			reply.setPerformative(Performative.REFUSE);
//			reply.setReceivers(new AID[] { msg.getSender() });
//			try {
//				Context context = new InitialContext();
//				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
//				ws.echoTextMessage(aid.getName() + " - is refusing to comunicate.");
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}
//		} 
		
		//else {
			reply.setPerformative(Performative.PROPOSE);
			reply.setReceivers(new AID[] { msg.getSender() });
			try {
				Context context = new InitialContext();
				WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
				ws.echoTextMessage(aid.getName() +  " -  is accepting to comunicate.");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		//}

		JMSBuilder.sendACL(reply);

	}
	
	private void rejectProposal(ACLMessage msg) {
		try {
			Context context = new InitialContext();
			WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
			ws.echoTextMessage(aid.getName() + " - rejected by contractor.");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private void acceptProposal(ACLMessage msg) {
		ACLMessage reply = new ACLMessage();
		reply.setSender(aid);
		reply.setReceivers(new AID[] { msg.getSender() });

		try {

			if (Math.random() > 0.5) {
				reply.setPerformative(Performative.FAILED);
				reply.setContent("Work failed.");
				try {
					Context context = new InitialContext();
					WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
					ws.echoTextMessage(aid.getName() + " - work failed, sending information to contractor.");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			} else {
				reply.setPerformative(Performative.INFORM);
				reply.setContent("Work succeeded.");
				try {
					Context context = new InitialContext();
					WSEndpoint ws = (WSEndpoint) context.lookup(WSEndpoint.LOOKUP);
					ws.echoTextMessage(aid.getName() + " - work succeeded, sending information to contractor.");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			JMSBuilder.sendACL(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}