package model;

import javax.ejb.Local;

@Local
public interface AgentInterface {
	public void handleMessage(ACLMessage message);
}
