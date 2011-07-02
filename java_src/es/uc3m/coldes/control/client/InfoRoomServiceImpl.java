package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.RoomDAO;
import es.uc3m.coldes.control.server.InfoRoomService;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import es.uc3m.coldes.util.Constants;

public class InfoRoomServiceImpl implements InfoRoomService {

	static Logger logger = Logger.getLogger(InfoRoomServiceImpl.class.getName());

	private RoomDAO roomDAO;
	
	public InfoRoomServiceImpl(){
		this.roomDAO = new RoomDAO();
	}
	
	/* MANAGEMENT USER */
	public int addRoom(Room room) {
		int result = this.roomDAO.addRoom(room);
		return result;
	}

	public int registerUserRoom(User user, Room room) {
		int result = this.roomDAO.registerUserRoom(user, room);
		return result;
	}
	
	public int deleteUserRoom(UserRoom userRoom) {
		int result;
		if(userRoom.getRol() == Constants.OWNER_ROL){
			logger.info("[InfoRoom-deleteUserRoom]: The user ["+userRoom.getUserName()+"] is de owner of the room [" + userRoom.getRoom().getName() + "]...");
			result = this.roomDAO.deleteRoom(userRoom.getRoom());
		}else{
			result = this.roomDAO.deleteUserRoom(userRoom);
		}
		
		return result;
	}
	
	public List<String> enterInRoom(User user, Room room) {
		List<String> usersRoom = this.roomDAO.enterInRoom(user,room);
		return usersRoom;
	}
	
	public int roomLogout(User user, Room room){
		int result = this.roomDAO.roomLogout(user, room);
		return result;
	}
	
	public int manageUserRoomRelation(UserRoom userRoom, boolean insert) {
		int result = this.roomDAO.manageUserRoomRelation(userRoom, insert);
		return result;
	}
	
	/* SEARCH ROOMS */
	public List<UserRoom> getUserRooms(User user) {
		List<UserRoom> resultRooms = this.roomDAO.getUserRooms(user);
		return resultRooms;
	}

	public List<Room> getColDesRooms() {
		List<Room> resultRooms = this.roomDAO.getColDesRooms();
		return resultRooms;
	}
	
	public List<Room> getColDesPublicRooms() {
		List<Room> resultRooms = this.roomDAO.getColDesPublicRooms();
		return resultRooms;
	}

	public List<UserRoom> getRoomUsers(Room room) {
		List<UserRoom> resultRoomUsers = this.roomDAO.getRoomUsers(room);
		return resultRoomUsers;
	}

	/* INVITATIONS */
	public UserRoom sendRoomInvitation(String username, Room room, int rol){
		UserRoom result = this.roomDAO.sendRoomInvitation(username, room, rol);
		return result;
	}

	public List<UserRoom> getAllUserRoomInvitation(String username) {
		List<UserRoom> resultRoomUsers = this.roomDAO.getAllUserRoomInvitation(username);
		return resultRoomUsers;
	}

}
