package beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import model.ACLMessage;
import model.AID;
import model.Agent;
import ws.WSEndpoint;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue") })
public class BroadcastMessagesBean implements MessageListener{
	@EJB
	WSEndpoint ws;
	
	@EJB
	DBBean database;
	
	@Override
	public void onMessage(Message jmsMessage) {
		try {
			ACLMessage message = (ACLMessage) ((ObjectMessage) jmsMessage).getObject();
			AID[] receivers = message.getReceivers();
			ws.echoTextMessage(message.getSender().getName() + ": " + message.getContent());
			
			for(AID aid : receivers) {
				Agent agent = database.getAgentsRunning().get(aid.getName());
				agent.handleMessage(message);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
