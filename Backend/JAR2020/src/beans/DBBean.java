package beans;

import java.util.HashMap;
import java.util.UUID;

import model.ACLMessage;

public class DBBean {
	private HashMap<UUID, ACLMessage> aclMessages = new HashMap<>();
	
	public DBBean() {
		super();
	}

	public HashMap<UUID, ACLMessage> getAclMessages() {
		return aclMessages;
	}

	public void setAclMessages(HashMap<UUID, ACLMessage> aclMessages) {
		this.aclMessages = aclMessages;
	}
	
	
}
