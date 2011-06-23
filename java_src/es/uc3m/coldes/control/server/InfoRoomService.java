package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;

public interface InfoRoomService {

	/* MANAGEMENT USER */
	public int addRoom(Room room);

	/* SEARCH ROOMS */
	public List<UserRoom> getUserRooms(User user);
}
