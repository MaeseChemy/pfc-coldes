package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.control.server.InfoUserService;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

public class InfoUserServiceImpl implements InfoUserService{
	
	static Logger logger = Logger.getLogger(InfoUserServiceImpl.class.getName());

	private UserDAO userDAO;
	
	public InfoUserServiceImpl(){
		this.userDAO = new UserDAO();
	}
	
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	/* ACCESS */
	public User doLogin(String user, String password) {
		User userLoged = this.userDAO.doLogin(user, password);
		return userLoged;
	}
	public Boolean doLogout() {
		return true;
	}
	
	/* MANAGEMENT USER */
	public String addUser(User user){
		String username = this.userDAO.addUser(user);
		return username;
	}
	public Boolean updateUser(User user, boolean passChange){
		boolean updateResult = this.userDAO.updateUser(user, passChange);
		return updateResult;
	}
	
	/* SEARCH */
	public List<User> getAllUsers() {
		List<User> users = this.userDAO.getAllUsers();
		return users;
	}

	public List<String> getColDesUsers(Room room) {
		List<String> users = this.userDAO.getColDesUsers(room);
		return users;
	}


}
