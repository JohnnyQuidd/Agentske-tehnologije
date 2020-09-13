package beans;

import java.util.HashMap;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.ACLMessage;
import model.Agent;
import model.AgentType;

@Singleton
public class DBBean {
	public static String LOOKUP = "java:app/JAR2020/DBBean!beans.DBBean";
	
	private HashMap<UUID, ACLMessage> aclMessages = new HashMap<>();
	
	//agent
	private HashMap<String, AgentType> agentTypes;
	private HashMap<String, Agent> agentsRunning;
	
	public DBBean() {
		
		//System.out.println("INSTANCIRANA BAZA");
		
		this.agentTypes = new HashMap<String,AgentType>();
		this.agentsRunning = new HashMap<String,Agent>();
		
		AgentType type1 = new AgentType("PingAgent", "1270.0.1");
		AgentType type2 = new AgentType("PongAgent", "1270.0.1");
		AgentType type3 = new AgentType("CNPContractorAgent", "1270.0.1");
		AgentType type4 = new AgentType("CNPManagerAgent", "1270.0.1");
		
		agentTypes.put(type1.getName(), type1);
		agentTypes.put(type2.getName(), type2);
		agentTypes.put(type3.getName(), type3);
		agentTypes.put(type4.getName(), type4);
	}
	
	

	public HashMap<String, Agent> getAgentsRunning() {
		return agentsRunning;
	}



	public void setAgentsRunning(HashMap<String, Agent> agentsRunning) {
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
