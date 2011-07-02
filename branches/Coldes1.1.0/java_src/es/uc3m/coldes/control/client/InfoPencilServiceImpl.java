package es.uc3m.coldes.control.client;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.PencilDAO;
import es.uc3m.coldes.control.server.InfoPencilService;
import es.uc3m.coldes.model.Room;


public class InfoPencilServiceImpl implements InfoPencilService {

	static Logger logger = Logger.getLogger(InfoPencilServiceImpl.class.getName());

	private PencilDAO pencilDAO;
	
	public InfoPencilServiceImpl(){
		this.pencilDAO = new PencilDAO();
	}

	public boolean pencilBusy(Room room, String username, int userrol) {
		boolean result = this.pencilDAO.pencilBusy(room);
		if(!result){
			this.pencilDAO.addPencilRequest(room, username, userrol, true);
		}
		return result;
	}

	public boolean isUserPencilOwner(Room room, String username){
		boolean result = this.pencilDAO.isUserPencilOwner(room, username);
		return result;
	}
	
	public boolean addPencilRequest(Room room, String username, int userrol) {
		boolean result = this.pencilDAO.pencilBusy(room);
		if(!result){
			return this.pencilDAO.addPencilRequest(room, username, userrol, true);
		}else{
			return this.pencilDAO.addPencilRequest(room, username, userrol, false);
		}
	}

	
	public String deleteUserPencilRequestRoom(String username, Room room) {
		String nextUser = this.pencilDAO.deleteUserRoomRequests(username, room.getId());
		return nextUser;
	}
	
	
}
