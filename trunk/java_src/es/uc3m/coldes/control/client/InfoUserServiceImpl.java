package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.control.server.InfoUserService;
import es.uc3m.coldes.model.User;

public class InfoUserServiceImpl implements InfoUserService{
	
	static Logger logger = Logger.getLogger(InfoUserServiceImpl.class.getName());

	private UserDAO userDAO;
	
	public InfoUserServiceImpl(){
		this.userDAO = new UserDAO();
	}

	/* ACCESS */
	public User doLogin(String user, String password) {
		User userLoged = this.userDAO.doLogin(user, password);
		return userLoged;
	}
	public Boolean doLogout() {
		//TODO: Implementar
		return null;
	}
	
	/* ADD */
	public String addUser(User user){
		String username = this.userDAO.addUser(user);
		return username;
	}
	
	/* SEARCH */
	public List<User> getAllUsers() {
		//TODO: Implementar
		return null;
	}
}
