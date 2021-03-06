package model;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String hostIpAddress;
	
	public User() {
		super();
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String password, String hostIpAddress) {
		super();
		this.username = username;
		this.password = password;
		this.hostIpAddress = hostIpAddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHost() {
		return hostIpAddress;
	}
	public void setHost(String host) {
		this.hostIpAddress = host;
	}
	
	
}
