package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.DesignDAO;
import es.uc3m.coldes.control.dao.RoomDAO;
import es.uc3m.coldes.control.server.InfoDesignService;
import es.uc3m.coldes.exceptions.SessionTimeoutException;
import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

/**
 * Clase encargada de implementar la funcionalidades
 * del Service de diseños.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class InfoDesignServiceImpl implements InfoDesignService {

	static Logger logger = Logger.getLogger(InfoDesignServiceImpl.class.getName());
	
	/**
	 * DAO enargado de la parte de Diseños del sistema.
	 */
	private DesignDAO designDAO;
	/**
	 * DAO enargado de la parte de Sañas del sistema.
	 */
	private RoomDAO roomDAO;
	
	/**
	 * Constructor por defecto que se encarga de inicializar 
	 * los DAO que se encargan de la parte de base de datos de
	 * diseños y salas.
	 */
	public InfoDesignServiceImpl(){
		this.designDAO = new DesignDAO();
		this.roomDAO = new RoomDAO();
	}
	
	/* METODOS DE DISEÑOS PARA SALAS */
	/**
	 * Función encargada de asociar un diseño a una sala.
	 * Primero se asocia el diseño a la sala y posteriormente
	 * se actualizan los datos de la sala.
	 * 
	 * @param room Sala a la cual se quiere asociar el diseño.
	 * @param content Contenido del lienzo en forma de array de bytes que
	 * representa el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean saveDesignToCanvas(Room room, byte[] content){
		boolean result = this.designDAO.saveDesignToCanvas(room, content);
		if(result){
			result = this.roomDAO.updateRoom(room);
		}
		return result;
	}
	/**
	 * Obtiene el diseño asociado a una sala.
	 * 
	 * @param room Sala de la cual se quiere obtener el diseño asociado.
	 * @return Array de bytes que representa el diseño asociado a la sala,
	 * o null en caso de no tener ningún diseño asociado.
	 * @throws SessionTimeoutException
	 */
	public byte[] getRoomSaveContent(Room room) {
		byte[] designContent = this.designDAO.getRoomSaveContent(room);
		return designContent;
	}
	/**
	 * Función encargada de borrar un diseño de una sala.
	 * 
	 * @param room Sala a la cual se quiere borrar el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean removeDesingRoom(Room room){
		boolean result = this.designDAO.removeDesingRoom(room);
		return result;
	}
	
	/* METODOS DE DISEÑOS PARA USUARIOS */
	/**
	 * Guarda para un usuario un diseño.
	 * 
	 * @param design Diseño que se desea guardar.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean saveDesignToUser(Design design) {
		boolean result = this.designDAO.saveDesignToUser(design);
		return result;
	}
	/**
	 * Funcion que devuelve los distintos diseños asociados a un usuario.
	 * 
	 * @param user User del cual se quieren obtener los diseños.
	 * @return Lista con los diseños que el usuario se ha asociado.
	 */
	public List<Design> getUserDesigns(User user){
		List<Design> userDesigns = this.designDAO.getUserDesigns(user);
		return userDesigns;
	}
	/**
	 * Borra un diseño asociado a un usuario.
	 * 
	 * @param design Diseño que se desea borrar.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean removeUserDesign(Design design) {
		boolean result = this.designDAO.removeUserDesign(design);
		return result;
	}
	
}
