package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.DesignDAO;
import es.uc3m.coldes.control.server.InfoDesignService;
import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;


public class InfoDesignServiceImpl implements InfoDesignService {

	static Logger logger = Logger.getLogger(InfoDesignServiceImpl.class.getName());

	private DesignDAO designDAO;
	
	public InfoDesignServiceImpl(){
		this.designDAO = new DesignDAO();
	}
	
	public boolean saveDesignToCanvas(Room room, byte[] content){
		boolean result = this.designDAO.saveDesignToCanvas(room, content);
		return result;
	}
	
	public byte[] getRoomSaveContent(Room room) {
		byte[] designContent = this.designDAO.getRoomSaveContent(room);
		return designContent;
	}
	
	public boolean removeDesingRoom(Room room){
		boolean result = this.designDAO.removeDesingRoom(room);
		return result;
	}

	public boolean saveDesignToUser(Design design) {
		boolean result = this.designDAO.saveDesignToUser(design);
		return result;
	}
	
	public List<Design> getUserDesigns(User user){
		List<Design> userDesigns = this.designDAO.getUserDesigns(user);
		return userDesigns;
	}

	public boolean removeUserDesign(Design design) {
		boolean result = this.designDAO.removeUserDesign(design);
		return result;
	}
	
}
