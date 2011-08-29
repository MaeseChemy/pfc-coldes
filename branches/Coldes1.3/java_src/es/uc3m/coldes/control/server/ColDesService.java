package es.uc3m.coldes.control.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.client.InfoDesignServiceImpl;
import es.uc3m.coldes.control.client.InfoPencilServiceImpl;
import es.uc3m.coldes.control.client.InfoRoomServiceImpl;
import es.uc3m.coldes.control.client.InfoUserServiceImpl;
import es.uc3m.coldes.exceptions.SessionTimeoutException;
import es.uc3m.coldes.model.Design;
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
	private InfoPencilService pencilService;
	private InfoDesignService designService;

	public ColDesService(){
		this.session = new ColDesSession();
		this.userService = new InfoUserServiceImpl();
		this.roomService = new InfoRoomServiceImpl();
		this.pencilService = new InfoPencilServiceImpl();
		this.designService = new InfoDesignServiceImpl();
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
	
	public boolean updateRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.updateRoom(room);
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
	
	public List<Room> getColDesPublicRooms() throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getColDesPublicRooms();
	}
	
	public int registerUserRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.registerUserRoom(user, room);
	}
	
	public int deleteUserRoom(UserRoom userRoom) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.deleteUserRoom(userRoom);
	}
	
	public boolean updateUserRoom(UserRoom userRoom) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.updateUserRoom(userRoom);
	}
	
	public List<String> enterInRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.enterInRoom(user,room);
	}
	
	public int roomLogout(User user, Room room, boolean totalLogout) throws SessionTimeoutException{
		if(!totalLogout)
			checkIsLogIn();
		boolean oldOwner = this.pencilService.isUserPencilOwner(room, user.getUsername());
		int result = this.roomService.roomLogout(user,room);
		if(result >= 0){
			
			//Actualizamos las peticiones de pinceles del usuario en la sala
			String nextUser = this.pencilService.deleteUserPencilRequestRoom(user.getUsername(), room);
			if(oldOwner){
				this.notifyUserToRoom(user.getUsername(), room, "exit", nextUser);
			}else{
				this.notifyUserToRoom(user.getUsername(), room, "exit", null);
			}
			
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
	
	/*************/
	/** PENCILS **/
	/*************/
	public boolean pencilBusy(Room room, String username, int userrol)throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.pencilService.pencilBusy(room, username, userrol);
		if(!result){
			this.notifyUserToRoom(username,room,"pencilRequest",username);
		}
		return result;
	}
	
	public boolean addPencilRequest(Room room, String username, int userrol)throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.pencilService.addPencilRequest(room, username, userrol);
		if(result){
			if(this.pencilService.isUserPencilOwner(room, username)){
				this.notifyUserToRoom(username,room,"pencilRequest",username);
			}else{
				this.notifyUserToRoom(username,room,"pencilRequest",null);
			}
		}
		return result;
	}
	
	public String removePencilRequest(Room room, String username, int userrol)throws SessionTimeoutException{
		checkIsLogIn();
		String nextUser = this.pencilService.deleteUserPencilRequestRoom(username, room);
		this.notifyUserToRoom(username, room, "pencilLeft", nextUser);
		return nextUser;
	}
	
	/*************/
	/** DESIGNS **/
	/*************/
	public boolean saveDesignToCanvas(Room room, byte[] content) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.saveDesignToCanvas(room, content);
		return result;
	}
	
	public byte[] getRoomSaveContent(Room room)throws SessionTimeoutException{
		checkIsLogIn();
		byte[] designContent = this.designService.getRoomSaveContent(room);
		return designContent;
	}
	
	public boolean removeDesingRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.removeDesingRoom(room);
		return result;
	}
	
	public boolean saveDesignToUser(Design design) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.saveDesignToUser(design);
		return result;
	}
	
	public List<Design> getUserDesigns(User user){
		List<Design> userDesigns = this.designService.getUserDesigns(user);
		return userDesigns;
	}
	
	public boolean removeUserDesign(Design design) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.removeUserDesign(design);
		return result;
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
	
	public void notifyUserToRoom(String username, Room room, String action, String nextPencilOwner) throws SessionTimeoutException{
		logger.info("[ColDesManager-enterInRoom]: User " +action+ " the room...");
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
        String clientID = UUIDUtils.createUUID(false);
        AsyncMessage msg = new AsyncMessage();
        msg.setDestination("updateUsersRooms");
        msg.setClientId(clientID);
        msg.setMessageId(UUIDUtils.createUUID(false));
        msg.setTimestamp(System.currentTimeMillis());
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("user", username);
        body.put("room", room);
        body.put("action", action);
        
        //Next pencil Owner
        body.put("nextPencilOwner", nextPencilOwner);
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
