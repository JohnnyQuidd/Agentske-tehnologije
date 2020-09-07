package node;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.Node;

@Singleton
@Startup
@LocalBean
public class AgentCenter {
	private String ipAddress;
	private Node master;
	private Node currentNode;
	
	public AgentCenter() {
		
	}

	public AgentCenter(String ipAddress) {
		super();
		this.ipAddress = ipAddress;
	}
	
	@PostConstruct
	public void init() {
		InetAddress inetAddress;
		String ipString;
		try {
			inetAddress = InetAddress.getLocalHost();
			ipString = inetAddress.getHostAddress();
			System.out.println("IP Address is: " + ipAddress);
			this.ipAddress = ipString;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.master = new Node("master", this.ipAddress);
		this.currentNode = new Node("master", this.ipAddress);
		
	}
	
	public Node getCurrentNode() {
		return this.currentNode;
	}
	
	
}
