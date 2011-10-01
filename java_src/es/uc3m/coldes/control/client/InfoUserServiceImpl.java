package es.uc3m.coldes.control.client;

import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.control.server.InfoUserService;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

/**
 * Clase encargada de implementar la funcionalidades
 * del Service de usuarios.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class InfoUserServiceImpl implements InfoUserService{
	
	static Logger logger = Logger.getLogger(InfoUserServiceImpl.class.getName());

	/**
	 * DAO enargado de la parte de Usuario del sistema.
	 */
	private UserDAO userDAO;
	
	/**
	 * Constructor por defecto que se encarga de inicializar 
	 * el DAO que se encarga de la parte de base de datos de usuarios.
	 */
	public InfoUserServiceImpl(){
		this.userDAO = new UserDAO();
	}
	
	/**
	 * Metodo encargando de de volver el UserDAO.
	 * @return UserDAO
	 */
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
	
	/* METODOS DE CONTROL DE ACCESO DE USUARIOS */
	/**
	 * Función encargada de realizar la autenticación del usuario dentro del sistema. 
	 * 
	 * @param user Nombre del usuario que intenta acceder al sistema.
	 * @param password Password del usuario que intenta acceder al sistema.
	 * @return Devuelve el User del usuario que se ha autenticado en caso correcto,
	 * y null en caso incorrecto.
	 */
	public User doLogin(String user, String password) {
		User userLoged = this.userDAO.doLogin(user, password);
		return userLoged;
	}
	/**
	 * Función encargada de finalizar la sesión del usuario autenticado en el
	 * sistema.
	 * Para ello invalida la Sessión, y finaliza 
	 * las acciones pendientes del usuario en el sistema.
	 * 
	 * @return Booleano que indica el resultado del logout, true si todo va bien
	 * false en caso de error.
	 */
	public Boolean doLogout() {
		return true;
	}
	
	/* METODOS DE GESTION DE USUARIOS */
	/**
	 * Añade un nuevo usuario al sistema.
	 * 
	 * @param user Nuevo usuario.
	 * @return Nombre del usuario añadido en caso de que todo vaya correcto,
	 * null en caso contrario.
	 */
	public String addUser(User user) {
		String username = this.userDAO.addUser(user);
		return username;
	}	
	/**
	 * Actualiza los datos de un usuario.
	 * 
	 * @param user User con las modificaciones que se quieren realizar.
	 * @param passChange Booleano que indica si la password del usuario ha
	 * sido modificada.
	 * @return Boolean que indica el resultado de la actualización, true si todo
	 * va bien false en caso contrario.
	 */
	public Boolean updateUser(User user, boolean passChange) {
		boolean updateResult = this.userDAO.updateUser(user, passChange);
		return updateResult;
	}
	
	/* METODOS DE BUSQUEDA DE USUARIOS */
	/**
	 * Función que obtiene todos los usuarios del sistema.
	 * 
	 * @return Lista con todos los usuarios del sistema.
	 */
	public List<User> getAllUsers() {
		List<User> users = this.userDAO.getAllUsers();
		return users;
	}
	/**
	 * Función que devuelve todos los usuarios que estan en una determinada sala
	 * en el momento actual.
	 * 
	 * @param room Sala de la cual se quieren obtener los usuarios conectados.
	 * @return Lista con el nombre de los usuarios conectados en el momento actual
	 * a la sala.
	 */
	public List<String> getColDesUsers(Room room) {
		List<String> users = this.userDAO.getColDesUsers(room);
		return users;
	}

}
