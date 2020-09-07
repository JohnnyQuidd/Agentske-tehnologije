package beans;

import java.util.HashMap;
import java.util.UUID;

import javax.ejb.Stateless;

import model.ACLMessage;
import model.AID;
import model.Agent;
import model.AgentType;

@Stateless
public class DBBean {
	private HashMap<UUID, ACLMessage> aclMessages = new HashMap<>();
	
	//agent
	private HashMap<String, AgentType> agentTypes = new HashMap<>();
	private HashMap<String, AID> agentsRunning = new HashMap<>();
	
	public DBBean() {
		super();
	}
	
	

	public HashMap<String, AID> getAgentsRunning() {
		return agentsRunning;
	}



	public void setAgentsRunning(HashMap<String, AID> agentsRunning) {
		this.agentsRunning = agentsRunning;
	}



	public HashMap<String, AgentType> getAgentTypes() {
		return agentTypes;
	}



	public void setAgentTypes(HashMap<String, AgentType> agentTypes) {
		this.agentTypes = agentTypes;
	}



	public HashMap<UUID, ACLMessage> getAclMessages() {
		return aclMessages;
	}

	public void setAclMessages(HashMap<UUID, ACLMessage> aclMessages) {
		this.aclMessages = aclMessages;
	}
	
	
}
