package es.uc3m.coldes.control.server;

import es.uc3m.coldes.model.Room;

public interface InfoPencilService {

	public boolean pencilBusy(Room room, String username, int userfunction);
	
	public boolean isUserPencilOwner(Room room, String username);
			
	public boolean addPencilRequest(Room room, String username, int userfunction);

	public String deleteUserPencilRequestRoom(String username, Room room);
	
}
