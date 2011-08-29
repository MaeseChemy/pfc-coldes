package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

public interface InfoDesignService {

	public boolean saveDesignToCanvas(Room room, byte[] content);
	
	public byte[] getRoomSaveContent(Room room);

	public boolean removeDesingRoom(Room room);
	
	
	public boolean saveDesignToUser (Design design);

	public List<Design> getUserDesigns(User user);

	public boolean removeUserDesign(Design design);
}
