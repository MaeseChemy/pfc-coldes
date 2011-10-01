package es.uc3m.coldes.control.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.dao.UserDAO;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.util.Constants;

/**
 * Modela una sesion de ColDes
 * ACONSEJABLE: es una buena práctica usar como claves de atributos, siempre que 
 * sea posible, el nombre de la clase, obtenido mediante 
 * <code><i>NombreClase.class.getName()</i></code>
 * Si existen varios objetos en sesión de la misma clase, entonces se debe 
 * intentar emplear una metodologia similar.
 * 
 *  @author Jose Miguel Blanco García
 *  
 */
public class ColDesSession {
	static Logger logger = Logger.getLogger(ColDesSession.class.getName());
	
	/**
	 * Mapa de atributos de sesion.
	 */
	private HashMap<String, Object> attributes;
	
	/**
	 * Flag que indica si la sesión está validada (true) o invalidada (false).
	 */
	private boolean validated;
	
	/**
	 * String que modela el ID de sesión único para este usuario.
	 */
	private String sessionID;
	
	/**
	 * Generador de ids de sesiones.
	 */
	private UuidGenerator uuidGenerator;
	
	/**
	 * Tiempo de validez de la sesión dentro del sistema.
	 */
	private int colDesSessionLength;
	
	/**
	 * Propiedades del sistema cargadas del fichero de propiedades de
	 * la aplicación.
	 */
	private Properties colDesProperties;
	
	/**
	 * DAO encargado de aportar a la sesión los datos necesarios de
	 * los usuarios del sistema.
	 */
	private UserDAO userDAO;
	
	/**
	 * Constructor por defecto que crea un nuevo objeto de sesion, inicialmente invalidada.
	 */
	public ColDesSession() {
		this.colDesProperties = new Properties();
		try {
			logger.info("Loading ColDes properties...");
			//InputStream in = ClassLoader.getSystemResourceAsStream("coldes.properties");
			InputStream in = this.getClass().getClassLoader().getResource("coldes.properties").openStream();

			this.colDesProperties.load(in);
			in.close();
		} catch (FileNotFoundException e) {
			logger.error("Properties file not found: " + 
					e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("Error openin properties file: " + 
					e.getLocalizedMessage());
		}
		String duracion = this.colDesProperties.getProperty("session.length");
		if (duracion != null && !duracion.trim().equals("")) {
			this.colDesSessionLength = Integer.parseInt(duracion);
		} else {
			// Si no está definido el atributo, la sesión tendrá duración ilimitada
			this.colDesSessionLength = Constants.UNDEFINED;
		}
		
		this.attributes = null;
		this.validated = false;
		this.uuidGenerator = new UuidGenerator();
		this.userDAO = new UserDAO(this.colDesProperties);
	}
	
	/**
	 * Lee un atributo de sesion si esta validada.
	 * 
	 * @param key Clave del atributo a leer
	 * @return el valor asociado a la clave indicada como argumento. Devolverá
	 * null si la clave no se corresponde con ningún valor existente o si
	 * la sesión está invalidada
	 */
	public Object getAttribute(String key) {
		if (this.isValid()) {
			return this.attributes.get(key);
		} else {
			return null;
		}
	}

	/**
	 * Guarda un nuevo atributo en sesion, o lo modifica si ya existia, siempre
	 * que la sesion este validada. Si la sesion esta invalidada, ignora la petición.
	 * 
	 * @param key Clave del atributo a guardar
	 * @param value Valor del atributo a guardar
	 */
	public void setAttribute(String key, Object value) {
		if (this.isValid()) {
			this.attributes.put(key, value);
		}
	}
	
	/**
	 * Invalida una sesion existente, sea cual sea el motivo.
	 */
	public void invalidate() {
		User user = (User) this.attributes.get(User.class.getName());
		this.attributes = null;
		this.validated = false;
		this.sessionID = null;
		
		if (user != null) {
			this.userDAO.invalidate(user.getUsername());
		} else {
			logger.error("The username can't be invalidate");
		}
	}
	
	/**
	 * Valida una sesion existente como consecuencia de un inicio de sesion,
	 * asociada al user que se recibe como argumento. Este objeto, conteniendo
	 * toda la informacion relativa al user, es almacenada como atributo de
	 * sesion.
	 * 
	 * @param user Referencia al user que inicia la sesión
	 */
	public void validate(User user) {
		this.attributes = new HashMap<String, Object>();
		this.attributes.put(User.class.getName(), user);
		this.validated = true;
		this.sessionID = this.uuidGenerator.generateSanitizedId();
		this.userDAO.validate(user.getUsername(), this.sessionID);
	}

	/**
	 * Devuelve el valor de la sesión.
	 * 
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}
	
	/**
	 * Comprueba si la sesión del usurio existe y, en ese caso, que no haya caducado
	 * Para comprobar la caducidad usa la propiedad "coldesSessionLength", que indica el
	 * tiempo en minutos que puede estar la sesión inactiva antes de que caduque.
	 * Para que una sesión sea válida, deben cumplirse las siguientes tres condiciones a la vez:
	 * <ul>
	 * <li>El atributo sessionID no es null</li>
	 * <li>El valor del atributo sessionID es igual al valor almacenado en BBDD para este user</li>
	 * <li>El tiempo transcurrido desde el último movimiento hasta el momento actual es menor al 
	 * indicado por la propiedad samSessionLength</li>
	 * </ul>
	 * Si se cumplen todas las condiciones, se actualizará en BBDD la fecha y hora del último movimiento
	 * usando la fecha y hora actual, y se devolverá true.
	 * Si alguna de las condiciones falla, se eliminará el valor sessionID almancenado en BBDD y la fecha
	 * del último movimiento, si es que existen, y se devolverá false.
	 * 
	 * @return <code>true</code> si la sesión es válida y <code>false</code> en caso contrario
	 */
	public boolean isValid() {
		User user = (User) this.attributes.get(User.class.getName());
		if (this.validated && this.sessionID != null && !this.sessionID.trim().equals("") && user != null) {
			return this.userDAO.checkUser(user.getUsername(), this.sessionID, this.colDesSessionLength);
		} else {
			logger.info("La sesion no es valida porque esta marcada como invalidada, o no existe ID de sesion y/o user registrado");
			return false;
		}
	}
	
	/**
	 * Finaliza el DAO userDAO para asegurarnos de que se cierran las conexiones con base de datos de forma correcta.
	 */
	public void finalize() {
		logger.info("Destruyendo objeto");
		this.userDAO.finalize();
		try {
			super.finalize();
		} catch (Throwable e) {
			logger.error("Error al destruir el objeto: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Consulta una propiedad del archivo de propiedades, si la sesión es válida, y si la encuentra la devuelve.
	 * 
	 * @param key Clave a consultar
	 * @return El valor asociado a esa clave, si existe y si la sesión es válida; <code>null</code> en caso contrario
	 */
	public String getPropertyFromFile(String key) {
		if (this.isValid()) {
			return this.colDesProperties.getProperty(key);
		} else {
			return null;
		}
	}
}
