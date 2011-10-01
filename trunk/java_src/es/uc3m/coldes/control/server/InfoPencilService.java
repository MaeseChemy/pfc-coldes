package es.uc3m.coldes.control.server;

import es.uc3m.coldes.model.Room;

/**
 * Interfaz que define la nomenclatura de los distintos
 * métodos del Service de pinceles.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public interface InfoPencilService {
	
	/**
	 * Función que verifica si el pincel esta siendo usado en una sala.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean pencilBusy(Room room, String username, int userfunction);
	/**
	 * Función que indica si un usuario dado es el dueño del pincel en una determinada
	 * sala.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean isUserPencilOwner(Room room, String username);
	/**
	 * Función mediante la cual un usuario realiza una petición para poder usar el pincel.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 */
	public boolean addPencilRequest(Room room, String username, int userfunction);
	/**
	 * Función mediante la cual se elimina una petición de un pincel de una salA.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return String que contiene el nombre de ususario del siguiente propietario del pincel,
	 * o null en caso de que no haya más peticiones.
	 */
	public String deleteUserPencilRequestRoom(String username, Room room);
	
}
