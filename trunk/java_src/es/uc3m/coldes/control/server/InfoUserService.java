package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.model.User;

public interface InfoUserService {

	/* ACCESS */
	public User doLogin(String user, String password);
	public Boolean doLogout();
	
	/* ADD */
	public String addUser(User user);
	
	/* SEARCH */
	public List<User> getAllUsers();

}
