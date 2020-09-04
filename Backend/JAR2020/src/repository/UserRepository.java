package repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import model.User;

@Singleton
@LocalBean
public class UserRepository {
	private List<User> listOfUsers = new ArrayList<>();
	
	public UserRepository() {
		if(listOfUsers == null || listOfUsers.isEmpty()) {
			listOfUsers.add(new User("admin", "admin"));
			listOfUsers.add(new User("guest", "guest"));
		}
	}
	
	public List<User> getAllRegisteredUsers() {
		return listOfUsers;
	}
	
	public User findUserByUsername(String username) {
		for(User u : listOfUsers) {
			if(u.getUsername().equals(username))
				return u;
		}
		return null;
	}
	
	public void registerNewUser(User user) {
		if(listOfUsers == null)
			listOfUsers = new ArrayList<User>();
		listOfUsers.add(user);
	}
}
