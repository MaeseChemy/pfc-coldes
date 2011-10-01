package es.uc3m.coldes.control.client;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.PencilDAO;
import es.uc3m.coldes.control.server.InfoPencilService;
import es.uc3m.coldes.model.Room;

/**
 * Clase encargada de implementar la funcionalidades
 * del Service de pinceles.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class InfoPencilServiceImpl implements InfoPencilService {

	static Logger logger = Logger.getLogger(InfoPencilServiceImpl.class.getName());
	
	/**
	 * DAO enargado de la parte de Pinceles del sistema.
	 */
	private PencilDAO pencilDAO;
	
	/**
	 * Constructor por defecto que se encarga de inicializar 
	 * el DAO que se encarga de la parte de base de datos de pinceles.
	 */
	public InfoPencilServiceImpl(){
		this.pencilDAO = new PencilDAO();
	}
	
	/**
	 * Función que verifica si el pincel esta siendo usado en una sala, en caso
	 * de que el pincel no tenga un dueño en el preciso momento, el usuario
	 * que ha entrado se convierte en el actual dueño del pincel.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean pencilBusy(Room room, String username, int userfunction) {
		boolean result = this.pencilDAO.pencilBusy(room);
		if(!result){
			this.pencilDAO.addPencilRequest(room, username, userfunction, true);
		}
		return result;
	}
	/**
	 * Función que indica si un usuario dado es el dueño del pincel en una determinada
	 * sala.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean isUserPencilOwner(Room room, String username){
		boolean result = this.pencilDAO.isUserPencilOwner(room, username);
		return result;
	}
	/**
	 * Función mediante la cual un usuario realiza una petición para poder usar el pincel.
	 * Primero se verifica si el pincel de la sala tiene ya un propietario, de no ser así,
	 * la nueva petición de pincel se convierte automaticamente en la dueña del pincel.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean addPencilRequest(Room room, String username, int userfunction) {
		boolean result = this.pencilDAO.pencilBusy(room);
		if(!result){
			return this.pencilDAO.addPencilRequest(room, username, userfunction, true);
		}else{
			return this.pencilDAO.addPencilRequest(room, username, userfunction, false);
		}
	}
	/**
	 * Función mediante la cual se elimina una petición de un pincel de una salA.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return String que contiene el nombre de ususario del siguiente propietario del pincel,
	 * o null en caso de que no haya más peticiones.
	 */
	public String deleteUserPencilRequestRoom(String username, Room room) {
		String nextUser = this.pencilDAO.deleteUserRoomRequests(username, room.getId());
		return nextUser;
	}
	
	
}
