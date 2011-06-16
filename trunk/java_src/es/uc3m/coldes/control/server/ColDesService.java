package es.uc3m.coldes.control.server;

import java.io.Serializable;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.client.InfoUserServiceImpl;
import es.uc3m.coldes.model.User;


public class ColDesService implements Serializable{
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ColDesService.class.getName());
	
	//Services
	private InfoUserService userService;

	public ColDesService(){
		this.userService = new InfoUserServiceImpl();
	}
	
	/***********/
	/** USERS **/
	/***********/
	public User doLogin(String user, String password){
		return this.userService.doLogin(user, password);
	}
	
	public String addUser(User user){
		return this.userService.addUser(user);
	}
}
