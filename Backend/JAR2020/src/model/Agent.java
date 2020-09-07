package model;

import javax.ejb.Singleton;

@Singleton
public abstract class Agent implements AgentInterface {
	protected AID aid;
	
	

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}
	
}
