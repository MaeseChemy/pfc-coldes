package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.RoomDAO;
import es.uc3m.coldes.control.server.InfoRoomService;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;

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
	
	/* SEARCH ROOMS */
	public List<UserRoom> getUserRooms(User user) {
		List<UserRoom> resultRooms = this.roomDAO.getUserRooms(user);
		return resultRooms;
	}

	public List<Room> getColDesRooms() {
		List<Room> resultRooms = this.roomDAO.getColDesRooms();
		return resultRooms;
	}



}
