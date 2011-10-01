package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.exceptions.SessionTimeoutException;
import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

/**
 * Interfaz que define la nomenclatura de los distintos
 * métodos del Service de diseños.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public interface InfoDesignService {
	
	/* METODOS DE DISEÑOS PARA SALAS */
	/**
	 * Función encargada de asociar un diseño a una sala.
	 * 
	 * @param room Sala a la cual se quiere asociar el diseño.
	 * @param content Contenido del lienzo en forma de array de bytes que
	 * representa el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean saveDesignToCanvas(Room room, byte[] content);
	/**
	 * Obtiene el diseño asociado a una sala.
	 * 
	 * @param room Sala de la cual se quiere obtener el diseño asociado.
	 * @return Array de bytes que representa el diseño asociado a la sala,
	 * o null en caso de no tener ningún diseño asociado.
	 * @throws SessionTimeoutException
	 */
	public byte[] getRoomSaveContent(Room room);
	/**
	 * Función encargada de borrar un diseño de una sala.
	 * 
	 * @param room Sala a la cual se quiere borrar el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean removeDesingRoom(Room room);
	
	/* METODOS DE DISEÑOS PARA USUARIOS */
	/**
	 * Guarda para un usuario un diseño.
	 * 
	 * @param design Diseño que se desea guardar.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean saveDesignToUser (Design design);
	/**
	 * Funcion que devuelve los distintos diseños asociados a un usuario.
	 * 
	 * @param user User del cual se quieren obtener los diseños.
	 * @return Lista con los diseños que el usuario se ha asociado.
	 */
	public List<Design> getUserDesigns(User user);
	/**
	 * Borra un diseño asociado a un usuario.
	 * 
	 * @param design Diseño que se desea borrar.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean removeUserDesign(Design design);
}
