package dto;

public class ACLMessageDTO {
	private String performative;
	private String reciever;
	private String content;
	private String sender;
	
	
	
	public ACLMessageDTO() {
		super();
	}

	public ACLMessageDTO(String performative, String reciever, String content, String sender) {
		super();
		this.performative = performative;
		this.reciever = reciever;
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
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
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
