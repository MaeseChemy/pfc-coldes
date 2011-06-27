package es.uc3m.coldes.control.server;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.client.InfoRoomServiceImpl;
import es.uc3m.coldes.control.client.InfoUserServiceImpl;
import es.uc3m.coldes.exceptions.SessionTimeoutException;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import flex.messaging.MessageBroker;
import flex.messaging.MessageDestination;
import flex.messaging.services.MessageService;


public class ColDesService implements Serializable{
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ColDesService.class.getName());
	
	private ColDesSession session;
	//Services
	private InfoUserService userService;
	private InfoRoomService roomService;

	public ColDesService(){
		this.session = new ColDesSession();
		this.userService = new InfoUserServiceImpl();
		this.roomService = new InfoRoomServiceImpl();
	}
	
	public void finalize() {
		if (this.session.isValid()) {
			this.session.invalidate();
		}

		this.session.finalize();
		this.userService.getUserDAO().finalize();
		try {
			super.finalize();
		} catch (Throwable e) {
			logger.error("Error in finalize: "+ e.getLocalizedMessage());
		}
	}
	
	private boolean checkIsLogIn() throws SessionTimeoutException {
		if (!this.session.isValid()) {
			logger.debug("The session is over");
			throw new SessionTimeoutException("Session timeout");
		}

		return true;
	}
	/***********/
	/** USERS **/
	/***********/
	public User doLogin(String user, String password){
		User loginUser = this.userService.doLogin(user, password);
		if (loginUser != null) {
			this.session.validate(loginUser);
			logger.info("Session init correct: " + user);
			return loginUser;
		} else {
			logger.info("Session init incorrect: " + user);
			return null;
		}
	}

	public Boolean doLogout(User user){
		this.session.invalidate();
		return this.userService.doLogout();
	}
	
	public String addUser(User user){
		return this.userService.addUser(user);
	}
	
	public Boolean updateUser(User user, boolean passChange) throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.updateUser(user, passChange);
	}
	
	public List<User> getAllUsers() throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.getAllUsers();
	}
	
	/***********/
	/** ROOMS **/
	/***********/
	public int addRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.addRoom(room);
	}
	
	public List<UserRoom> getUserRooms(User user) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getUserRooms(user);
	}
	
	public List<Room> getColDesRooms() throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getColDesRooms();
	}
	
	public int registerUserRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.registerUserRoom(user, room);
	}
	
	/**************/
	/** CHANNELS **/
	/**************/
	public String createDestination(String destinationStringValue) throws SessionTimeoutException{
		checkIsLogIn();
		logger.info("[ColDesManager-createChatDestination]: Creating new channel ["+destinationStringValue+"]");
		// Create a new Message desination dynamically 
		
		MessageBroker broker = MessageBroker.getMessageBroker(null);
		MessageService service = (MessageService) broker.getService("message-service");
		if(service.getDestination(destinationStringValue) == null){
			MessageDestination destination = (MessageDestination) service.createDestination(destinationStringValue);

			if (service.isStarted()) {
			    destination.start();
			}
		}

		return destinationStringValue;
	}
	
	/*******************/
	/** PLAY PROGRESS **/
	/*******************/
	public int playProcess(int velocidad, int numFrame) throws InterruptedException{
		//wait(velocidad);
		return numFrame;
	}
}
