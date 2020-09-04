package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Message  implements Serializable{
	private static final long serialVersionUID = 1L;
	private UUID uuid;
	private String subject;
	private String senderUsername;
	private List<String> revceiversUsernames;
	private LocalDateTime dateTime;
	private String content;
	private int messageType;
	
	public Message() {
		super();
	}

	public Message(UUID uuid, String subject, String senderUsername, List<String> revceiversUsernames,
			LocalDateTime dateTime, String content, int messageType) {
		super();
		this.uuid = uuid;
		this.subject = subject;
		this.senderUsername = senderUsername;
		this.revceiversUsernames = revceiversUsernames;
		this.dateTime = dateTime;
		this.content = content;
		this.messageType = messageType;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public List<String> getRevceiversUsernames() {
		return revceiversUsernames;
	}

	public void setRevceiversUsernames(List<String> revceiversUsernames) {
		this.revceiversUsernames = revceiversUsernames;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
