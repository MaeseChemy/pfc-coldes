package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.RoomDAO;
import es.uc3m.coldes.control.server.InfoRoomService;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import es.uc3m.coldes.util.Constants;

/**
 * Clase encargada de implementar la funcionalidades
 * del Service de salas.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class InfoRoomServiceImpl implements InfoRoomService {

	static Logger logger = Logger.getLogger(InfoRoomServiceImpl.class.getName());
	
	/**
	 * DAO enargado de la parte de Salas del sistema.
	 */
	private RoomDAO roomDAO;
	
	/**
	 * Constructor por defecto que se encarga de inicializar 
	 * el DAO que se encarga de la parte de base de datos de salas.
	 */
	public InfoRoomServiceImpl(){
		this.roomDAO = new RoomDAO();
	}
	
	/* MANAGEMENT ROOM */
	/**
	 * Añade una nueva sala al sistema.
	 *
	 * @param room Sala creada por el usuario.
	 * @return Id que hace relación a la nueva sala insertada en el
	 * sistema.
	 */
	public int addRoom(Room room) {
		int result = this.roomDAO.addRoom(room);
		return result;
	}
	/**
	 * Actualiza los datos referentes a una sala del sistema.
	 * 
	 * @param room Sala de la cual se quieren actualizar los datos.
	 * @return Boolean que indica el resultado de la función, true en caso
	 * correcto false en caso contrario.
	 */
	public boolean updateRoom(Room room) {
		boolean result = this.roomDAO.updateRoom(room);
		return result;
	}
	/**
	 * Asocia un usuario a una sala.
	 * 
	 * @param user User que se asocia a la sala.
	 * @param room Sala a la cual se asocia el usuario.
	 * @return Int que indica el resultado de la operación.
	 */
	public int registerUserRoom(User user, Room room) {
		int result = this.roomDAO.registerUserRoom(user, room);
		return result;
	}
	/**
	 * Actualiza una realacion usuario sala. En caso de que el usuario 
	 * sea el propietario de la sala se borra también la sala.
	 * 
	 * @param userRoom Relación entre usuario y sala.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public int deleteUserRoom(UserRoom userRoom) {
		int result;
		if(userRoom.getUserfunction() == Constants.OWNER_FUNCTION){
			logger.info("[InfoRoom-deleteUserRoom]: The user ["+userRoom.getUserName()+"] is de owner of the room [" + userRoom.getRoom().getName() + "]...");
			result = this.roomDAO.deleteRoom(userRoom.getRoom());
		}else{
			result = this.roomDAO.deleteUserRoom(userRoom);
		}
		
		return result;
	}
	/**
	 * Actualiza una realacion usuario sala.
	 * 
	 * @param userRoom Relación entre usuario y sala.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean updateUserRoom(UserRoom userRoom) {
		if(userRoom.getUserfunction() == Constants.OWNER_FUNCTION){			
			logger.info("[InfoRoom-updateUserRoom]: The user ["+userRoom.getUserName()+"] is the new owner of the room [" + userRoom.getRoom().getName() + "]...");
			//Update room
			Room aux = userRoom.getRoom();
			aux.setOwner(userRoom.getUserName());
			this.roomDAO.updateRoom(aux);
			if(this.roomDAO.updateRoom(aux)){
				//Update user relation
				return this.roomDAO.changeUserOwner(userRoom.getRoom().getId(), userRoom.getUserName(), userRoom.getOwnerUserName());	
			}else{
				return false;
			}
		}else{
			return this.roomDAO.updateUserRoom(userRoom);

		}
	}
	/**
	 * Función mediante la cual se notifica al sistema la entrada de un usuario a una
	 * sala.
	 * 
	 * @param user User que entra en la sala.
	 * @param room Sala a la que entra el usuario.
	 * @return Lista con el nombre de usuario de los usuarios ya conectados en la sala.
	 */
	public List<String> enterInRoom(User user, Room room) {
		List<String> usersRoom = this.roomDAO.enterInRoom(user,room);
		return usersRoom;
	}
	/**
	 * Función que notifica al sistema cuando un usuario sale de la salas.
	 * 
	 * @param user User que sale de la sala.
	 * @param room Sala de la que sale el usuario.
	 * @param totalLogout Indica si el logout es unicamente de la sala o también del sistema.
	 * @return Int que indica el resultado de la operación.
	 */
	public int roomLogout(User user, Room room){
		int result = this.roomDAO.roomLogout(user, room);
		return result;
	}
	/**
	 * Función encargada de gestionar las relaciones de los usuarios con las distintas
	 * salas del sistema.
	 * 
	 * @param userRoom Nueva relación usuario sala en la que se indica ademas la función
	 * del usuario en la sala.
	 * @param insert Boolean que indica si es una inserción o una actualización.
	 * @return Int que indica el resultado de la operación.
	 */
	public int manageUserRoomRelation(UserRoom userRoom, boolean insert) {
		int result = this.roomDAO.manageUserRoomRelation(userRoom, insert);
		return result;
	}
	
	/* SEARCH ROOMS */
	/**
	 * Obtiene la lista de salas en las cuales esta participando un usuario, así como
	 * la función que desempeña en cada una de las salas.
	 * 
	 * @param user User del cual se quieren obtener salas y funciones.
	 * @return Lista con las distintas salas del sistema en las cuales el usuario
	 * se encuentra participando junto con la funcion que desempeña en cada una
	 * de ellas.
	 */
	public List<UserRoom> getUserRooms(User user) {
		List<UserRoom> resultRooms = this.roomDAO.getUserRooms(user);
		return resultRooms;
	}
	/**
	 * Obtiene las distintas salas del sistema sin excepción.
	 * 
	 * @return Lista que contiene todas las salas del sistema.
	 */
	public List<Room> getColDesRooms() {
		List<Room> resultRooms = this.roomDAO.getColDesRooms();
		return resultRooms;
	}
	/**
	 * Obtiene las salas publicas del sistema.
	 * 
	 * @return Lista que contiene las salas publicas de ColDes.
	 */
	public List<Room> getColDesPublicRooms(User user) {
		List<Room> resultRooms = this.roomDAO.getColDesPublicRooms(user);
		return resultRooms;
	}
	/**
	 * Obtiene las distintas relaciones que tiene una sala con los usuarios del sistema.
	 * 
	 * @param room Sala de la cual se quiere saber sus relaciones con los usuarios.
	 * @return Lista con las distintas relaciones de la sala.
	 */
	public List<UserRoom> getRoomUsers(Room room) {
		List<UserRoom> resultRoomUsers = this.roomDAO.getRoomUsers(room);
		return resultRoomUsers;
	}

	/* INVITATIONS */
	/**
	 * Función mediante la cual se le envia una invitación a sala.
	 * 
	 * @param username Nombre de usuario destinatario de la invitación.
	 * @param room Sala a la cual se desea invitar al usuario.
	 * @param userfunction Función que desempeñara el usuario en la sala.
	 * @return Int que indica el resultado de la operación.
	 */
	public UserRoom sendRoomInvitation(String username, Room room, int userfunction){
		UserRoom result = this.roomDAO.sendRoomInvitation(username, room, userfunction);
		return result;
	}
	/**
	 * Función mediante la cual se obtienen todas las invitaciones que hay en el sistema
	 * de un usuario.
	 * 
	 * @param username Nombre de usuario del cual se quieren obtener las invitaciones.
	 * @return Lista que contiene las relaciones futuras con las salas a las cual el
	 * usuario ha sido invitado.
	 */
	public List<UserRoom> getAllUserRoomInvitation(String username) {
		List<UserRoom> resultRoomUsers = this.roomDAO.getAllUserRoomInvitation(username);
		return resultRoomUsers;
	}

}
