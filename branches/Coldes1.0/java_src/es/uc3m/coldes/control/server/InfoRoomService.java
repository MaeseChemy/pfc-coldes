package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;

public interface InfoRoomService {

	/* MANAGEMENT USER */
	public int addRoom(Room room);
	public int registerUserRoom(User user, Room room);
	public int deleteUserRoom(UserRoom userRoom);
	public List<String> enterInRoom(User user, Room room);
	
	/* SEARCH ROOMS */
	public List<UserRoom> getUserRooms(User user);
	public List<Room> getColDesRooms();
	

}
