package es.uc3m.coldes.control.server;

import java.io.Serializable;
import java.util.HashMap;
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
import flex.messaging.messages.AsyncMessage;
import flex.messaging.services.MessageService;
import flex.messaging.util.UUIDUtils;


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
	
	public List<String> getColDesUsers(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.getColDesUsers(room);
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
	
	public int manageUserRoomRelation(UserRoom userRoom, boolean insert)throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.manageUserRoomRelation(userRoom, insert);
	}
	
	public List<Room> getColDesRooms() throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getColDesRooms();
	}
	
	public int registerUserRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.registerUserRoom(user, room);
	}
	
	public int deleteUserRoom(UserRoom userRoom) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.deleteUserRoom(userRoom);
	}
	
	public List<String> enterInRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.enterInRoom(user,room);
	}
	
	public int roomLogout(User user, Room room, boolean totalLogout) throws SessionTimeoutException{
		if(!totalLogout)
			checkIsLogIn();
		int result = this.roomService.roomLogout(user,room);
		if(result >= 0){
			this.notifyUserToRoom(user, room, "exit");
		}
		return result;
	}
	
	public List<UserRoom> getRoomUsers(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getRoomUsers(room);
	}
	
	public int sendRoomInvitation(String username, Room room, int rol) throws SessionTimeoutException{
		checkIsLogIn();
		UserRoom userroom = this.roomService.sendRoomInvitation(username, room, rol);
		if(userroom != null){
			notifyInvitationToUser(userroom);
			return 0;
		}
		return -1;
	}
	
	public List<UserRoom> getAllUserRoomInvitation(String username) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getAllUserRoomInvitation(username);
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
	
	public void notifyUserToRoom(User user, Room room, String action) throws SessionTimeoutException{
		logger.info("[ColDesManager-enterInRoom]: User " +action+ " the room...");
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
        String clientID = UUIDUtils.createUUID(false);
        AsyncMessage msg = new AsyncMessage();
        msg.setDestination("updateUsersRooms");
        msg.setClientId(clientID);
        msg.setMessageId(UUIDUtils.createUUID(false));
        msg.setTimestamp(System.currentTimeMillis());
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("user", user.getUsername());
        body.put("room", room);
        body.put("action", action);
        msg.setBody(body);
        logger.info("[ColDesManager-enterInRoom]: Sending message" + body);
        msgBroker.routeMessageToService(msg, null);
		logger.info("[ColDesManager-enterInRoom]: Sended");
	}
	
	public void notifyInvitationToUser(UserRoom userroom) throws SessionTimeoutException{
		logger.info("[ColDesManager-enterInRoom]: Send invitation to user " +userroom.getUserName()+ " of room ["+userroom.getRoom().getId()+"]...");
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
        String clientID = UUIDUtils.createUUID(false);
        AsyncMessage msg = new AsyncMessage();
        msg.setDestination("message");
        msg.setClientId(clientID);
        msg.setMessageId(UUIDUtils.createUUID(false));
        msg.setTimestamp(System.currentTimeMillis());
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("username", userroom.getUserName());
        body.put("userroom", userroom);

        msg.setBody(body);
        logger.info("[ColDesManager-enterInRoom]: Sending message" + body);
        msgBroker.routeMessageToService(msg, null);
		logger.info("[ColDesManager-enterInRoom]: Sended");
	}
	
	/*******************/
	/** PLAY PROGRESS **/
	/*******************/
	public int playProcess(int velocidad, int numFrame) throws InterruptedException{
		try {
			Thread.currentThread();
			Thread.sleep(velocidad);
		} catch (InterruptedException e) {
			logger.error("Se produjo un error la intentar dormir el proceso");
		}
		return numFrame;
	}
}
