package beans;

import java.util.HashMap;
import java.util.UUID;

import javax.ejb.Stateless;

import model.ACLMessage;

@Stateless
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
