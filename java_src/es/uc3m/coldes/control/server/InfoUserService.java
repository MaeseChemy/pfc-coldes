package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.model.User;

public interface InfoUserService {

	public UserDAO getUserDAO();
	/* ACCESS */
	public User doLogin(String user, String password);
	public Boolean doLogout();
	
	/* MANAGEMENT USER */
	public String addUser(User user);
	public Boolean updateUser(User user, boolean passChange);
	
	/* SEARCH */
	public List<User> getAllUsers();
	
	

}
