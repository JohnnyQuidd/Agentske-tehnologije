package model;

import java.io.Serializable;

public class Node implements Serializable{
	public static String LOOKUP = "java:app/JAR2020/Node!model.Node";
	private static final long serialVersionUID = 1L;
	private String alias;
	private String address;
	
	public Node() {
		super();
	}
	public Node(String alias, String address) {
		super();
		this.alias = alias;
		this.address = address;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
