package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dto.UserDTO;
import model.User;
import repository.UserRepository;
import ws.UserLoginEndPoint;
import ws.UserLogoutEndPoint;

@Stateless
@Path("/users")
@Local
public class UserBean {
	private Map<String, Session> sessions = new HashMap<>();
	
	@EJB
	private UserRepository userRepo;
	@EJB
	private UserLoginEndPoint userSocket;
	@EJB
	private UserLogoutEndPoint userLogoutSocket;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response loginUser(UserDTO userDTO, @Context HttpServletRequest request, Session session) {
		User user = userRepo.findUserByUsername(userDTO.getUsername());
		
		if(user != null) {
			if(userDTO.getPassword().equals(user.getPassword())) {
				request.getSession().setAttribute("username", user.getUsername());
				sessions.put(user.getUsername(), session);
				userSocket.notifyNewLogin(user.getUsername());
				return Response.status(200).entity(user.getUsername()).build();
			}
			return Response.status(403).entity("Unauthorized").build();
		}
		
		return Response.status(404).entity("User not found").build();
	}
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response registerUser(UserDTO userDTO) {
		User present = userRepo.findUserByUsername(userDTO.getUsername());
		if(present == null) {
			User user = new User(userDTO.getUsername(), userDTO.getPassword());
			userRepo.registerNewUser(user);
			return Response.status(201).entity("User registered").build();
		}
			return Response.status(405).entity("Username is already taken").build();
	}
	
	@GET
	@Path("/registered")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllRegisteredUsers(){
		return userRepo.getAllRegisteredUsers();
	}
	
	@GET
	@Path("/loggedIn")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllLoggedInUsers(){
		List<User> users = new ArrayList<User>();
		for(String u: sessions.keySet()) {
			users.add(userRepo.findUserByUsername(u));
		}		
		return users;
	}
	
	@DELETE
	@Path("/loggedIn/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response logoutUser(@PathParam("user") String username, @Context HttpServletRequest request) {
		for(String u : sessions.keySet()) {
			if(u.equals(username)) {
				sessions.remove(u);
				userLogoutSocket.notifyNewLogout(username);
				request.getSession().invalidate();
				return Response.status(200).entity("Logged out").build();
			}
		}
		return Response.status(404).entity("User is not logged in").build();
	}
}
