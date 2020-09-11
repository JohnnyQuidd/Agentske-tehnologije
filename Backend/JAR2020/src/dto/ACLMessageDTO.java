package dto;

import java.io.Serializable;

public class ACLMessageDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String performative;
	private String receiver;
	private String content;
	private String sender;
	
	
	
	public ACLMessageDTO() {
		super();
	}

	public ACLMessageDTO(String performative, String receiver, String content, String sender) {
		super();
		this.performative = performative;
		this.receiver = receiver;
		this.content = content;
		this.sender = sender;
	}
	
	public String getPerformative() {
		return performative;
	}
	public void setPerformative(String performative) {
		this.performative = performative;
	}
	public String getReciever() {
		return receiver;
	}
	public void setReciever(String reciever) {
		this.receiver = reciever;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
	

}
