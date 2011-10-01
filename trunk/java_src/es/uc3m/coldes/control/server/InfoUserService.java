package es.uc3m.coldes.control.server;

import java.util.List;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

/**
 * Interfaz que define la nomenclatura de los distintos
 * métodos del Service de usuarios.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public interface InfoUserService {
	
	/**
	 * Metodo encargando de de volver el UserDAO.
	 * @return UserDAO
	 */
	public UserDAO getUserDAO();
	
	/* METODOS DE CONTROL DE ACCESO DE USUARIOS */
	/**
	 * Función encargada de realizar la autenticación del usuario dentro del sistema. 
	 * 
	 * @param user Nombre del usuario que intenta acceder al sistema.
	 * @param password Password del usuario que intenta acceder al sistema.
	 * @return Devuelve el User del usuario que se ha autenticado en caso correcto,
	 * y null en caso incorrecto.
	 */
	public User doLogin(String user, String password);
	/**
	 * Función encargada de finalizar la sesión del usuario autenticado en el
	 * sistema.
	 * Para ello invalida la Sessión, y finaliza 
	 * las acciones pendientes del usuario en el sistema.
	 * 
	 * @return Booleano que indica el resultado del logout, true si todo va bien
	 * false en caso de error.
	 */
	public Boolean doLogout();
	
	/* METODOS DE GESTION DE USUARIOS */
	/**
	 * Añade un nuevo usuario al sistema.
	 * 
	 * @param user Nuevo usuario.
	 * @return Nombre del usuario añadido en caso de que todo vaya correcto,
	 * null en caso contrario.
	 */
	public String addUser(User user);
	/**
	 * Actualiza los datos de un usuario.
	 * 
	 * @param user User con las modificaciones que se quieren realizar.
	 * @param passChange Booleano que indica si la password del usuario ha
	 * sido modificada.
	 * @return Boolean que indica el resultado de la actualización, true si todo
	 * va bien false en caso contrario.
	 */
	public Boolean updateUser(User user, boolean passChange);
	
	/* METODOS DE BUSQUEDA DE USUARIOS */
	/**
	 * Función que obtiene todos los usuarios del sistema.
	 * 
	 * @return Lista con todos los usuarios del sistema.
	 */
	public List<User> getAllUsers();
	/**
	 * Función que devuelve todos los usuarios que estan en una determinada sala
	 * en el momento actual.
	 * 
	 * @param room Sala de la cual se quieren obtener los usuarios conectados.
	 * @return Lista con el nombre de los usuarios conectados en el momento actual
	 * a la sala.
	 */
	public List<String> getColDesUsers(Room room);
	
}
