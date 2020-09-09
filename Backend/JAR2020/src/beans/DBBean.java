package beans;

import java.util.HashMap;
import java.util.UUID;

import javax.ejb.Singleton;

import model.ACLMessage;
import model.AID;
import model.AgentType;

@Singleton
public class DBBean {
	private HashMap<UUID, ACLMessage> aclMessages = new HashMap<>();
	
	//agent
	private HashMap<String, AgentType> agentTypes = new HashMap<>();
	private HashMap<String, AID> agentsRunning = new HashMap<>();
	
	public DBBean() {
		AgentType type1 = new AgentType("Ping", "1270.0.1");
		AgentType type2 = new AgentType("Pong", "1270.0.1");
		AgentType type3 = new AgentType("CNPContractor", "1270.0.1");
		AgentType type4 = new AgentType("CNPManager", "1270.0.1");
		
		agentTypes.put(type1.getName(), type1);
		agentTypes.put(type2.getName(), type2);
		agentTypes.put(type3.getName(), type3);
		agentTypes.put(type4.getName(), type4);
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
