package es.uc3m.coldes.control.server;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.client.InfoRoomServiceImpl;
import es.uc3m.coldes.control.client.InfoUserServiceImpl;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import flex.messaging.MessageBroker;
import flex.messaging.MessageDestination;
import flex.messaging.services.MessageService;


public class ColDesService implements Serializable{
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ColDesService.class.getName());
	
	//Services
	private InfoUserService userService;
	private InfoRoomService roomService;

	public ColDesService(){
		this.userService = new InfoUserServiceImpl();
		this.roomService = new InfoRoomServiceImpl();
	}
	
	/***********/
	/** USERS **/
	/***********/
	public User doLogin(String user, String password){
		return this.userService.doLogin(user, password);
	}
	
	public Boolean doLogout(User user){
		return this.userService.doLogout();
	}
	
	public String addUser(User user){
		return this.userService.addUser(user);
	}
	
	public Boolean updateUser(User user, boolean passChange){
		return this.userService.updateUser(user, passChange);
	}
	
	public List<User> getAllUsers(){
		return this.userService.getAllUsers();
	}
	
	/***********/
	/** ROOMS **/
	/***********/
	public int addRoom(Room room){
		return this.roomService.addRoom(room);
	}
	
	public List<UserRoom> getUserRooms(User user){
		return this.roomService.getUserRooms(user);
	}
	
	public String createChatDestination(String destinationStringValue){
		logger.info("[ColDesManager-createChatDestination]: Creando nuevo canal de comunicacion");
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
}
