package beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.AID;
import model.Agent;
import model.AgentType;
import node.AgentCenter;
import ws.WSEndpoint;

@Stateless
@Path("/agents")
@LocalBean
public class AgentsBean {
	
	@EJB
	DBBean database;
	

	@EJB
	WSEndpoint ws;
	
	@EJB
	AgentCenter agentCenter;

	
	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "OK";
	}
	
	
	@GET
	@Path("/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAgentTypes() {		
		Collection<AgentType> agentTypes = database.getAgentTypes().values();
		if(agentTypes.isEmpty()) {
			return Response.status(404).entity("No agent types found").build();
		}

		
		return Response.status(200).entity(agentTypes).build();
	}
	
	
	@GET
	@Path("/running")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRunningAgents() {
		Collection<Agent> runningAgents = database.getAgentsRunning().values();
		Collection<AID> aids = new ArrayList<>();
		
		for(Agent agent : runningAgents)
			aids.add(agent.getAid());
		
		return Response.status(200).entity(aids).build();
	}
	
	@PUT
	@Path("/running/{type}/{name}")
	public Response startAgent(@PathParam("type") String type,@PathParam("name") String name){
		//check if agent with that name already exists
		for(Agent a : database.getAgentsRunning().values()) {
			if(a.getAid().getName().equals(name)) {
				return Response.status(403).entity("Agent with that name already exists").build();
			}
		}
		//check if that agent type exists
		AgentType agentType = null;
		for(AgentType at : database.getAgentTypes().values()) {
			if(at.getName().equals(type)) {
				agentType = at;
			}
		}
		if(agentType == null) {
			return Response.status(404).entity("Agent Type not found!").build();
		}
		
		AID newAID = new AID(name, agentCenter.getCurrentNode(), agentType);
		
		Object obj;
		try {
			obj = Class.forName("agents." + agentType.getName() + "Agent").newInstance();
			if (obj instanceof Agent) {
				((Agent) obj).setAid(newAID);
				Agent newAgent = (Agent) obj;
				database.getAgentsRunning().put(name, newAgent);
				
				ws.echoTextMessage("New agent started: " + newAgent.getAid().getName());
				return Response.status(201).entity("Agent " + name +" created: ").build();
			} else {
				System.out.println("Type " + type + " cannot be added!");
				
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return Response.status(500).entity("Agent cannot be added").build();
	}
	
	@DELETE
	@Path("/running/{aid}")
	public Response stopAgent(@PathParam("aid") String aid) {
		for(Agent agent : database.getAgentsRunning().values()) {
			if(agent.getAid().getName().equals(aid)) {
				database.getAgentsRunning().remove(agent.getAid().getName());
				ws.echoTextMessage("Agent " + aid + " stopped");
				return Response.status(203).entity("Agent stopped").build();
			}
		}
		return Response.status(404).entity("Agent not found").build();
	}
	
	
	
	
	

}
