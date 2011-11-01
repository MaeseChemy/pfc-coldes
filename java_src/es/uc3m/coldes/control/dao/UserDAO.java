package es.uc3m.coldes.control.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.security.SHA1;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.util.Constants;

/**
 * Mediante este DAO se obtienen las operaciones fundamentales
 * para trabajar con los distintos usuarios del sistema.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class UserDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());
	
	/**
	 * Conexión a la base de datos.
	 */
	private Connection conn = null;
	
	/**
	 * Constructor por defecto que invoca a la clase padre BBDD.
	 */
	public UserDAO() {
		super();
	}
	/**
	 * Constructor que forma el DAO a partir de unas propiedades
	 * mediante el constructor de la clase padre BBDD.
	 * 
	 * @param coldesignProperties Propiedades
	 */
	public UserDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}
	
	/**
	 * Verifica si el nombre de usuario y la password usados para intentar acceder
	 * a la aplicación son correctos. Esta función diferencia mayusculas de 
	 * minusculas a la hora de hacer la comprobación.
	 * Para verificar la password del usuario se le aplica una función hash, que
	 * se compara con lo almacenado en la base de datos.
	 * 
	 * @param username Nombre de usuario.
	 * @param password Password del usuario.
	 * @return Usuario asociado al username y a la password o null en caso
	 * de no existir.
	 */
	public User doLogin(String username, String password) {
		logger.info("[UserDAO-getUser]: Searching user [" + username + "]...");
		SHA1 sha = new SHA1();
		String sqlSelectUsuario = "select name, surname1, surname2, email, username, password, admin, designer, active "
			+ "from user where active=1 and username=? and password=?";
		try {
			conn = getConnection();
			String passSHA1 = sha.getHash(password);
			
			PreparedStatement st = conn.prepareStatement(sqlSelectUsuario);
			st.setString(1, username);
			st.setString(2, passSHA1);
	
			// Ejecutamos las query
			ResultSet results = st.executeQuery();
			if (results != null && results.next()) {
				User user = new User();
				user.setName(results.getString("name"));
				user.setSurname1(results.getString("surname1"));
				user.setSurname2(results.getString("surname2"));
				user.setEmail(results.getString("email"));
				user.setUsername(results.getString("username"));
				user.setPassword(results.getString("password"));
				user.setAdmin(results.getBoolean("admin"));
				user.setDesigner(results.getBoolean("designer"));
				user.setActive(results.getBoolean("active"));
				return user;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.error("[UserDAO-getUser]: Error in SQL sentence: " + e.getLocalizedMessage());
			return null;
		} catch (NoSuchAlgorithmException e) {
			logger.error("[UserDAO-getUser]: Error generating password hash: " + e.getLocalizedMessage());
			return null;
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-getUser]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-getUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Añade un nuevo usuario al sistema. Todos los datos del usuario se almacenan
	 * en claro en la base de datos salvo la contraseña, a la cual se le aplica
	 * una función hash y se almacena el resultado de esta operación.
	 * 
	 * @param user Nuevo usuario a registrar.
	 * @return Valor del nombre de usuario nuevo registrado.
	 */
	public String addUser(User user) {
		String username = null;
		if(!this.existUser(user.getUsername())){
			logger.info("[UserDAO-addUser]: Adding new user [" + user.getUsername() + "]...");
			
			String sqlAddUsuario = "insert into user (name, surname1, surname2, email, username, password, admin, designer, active) " +
					"values (?,?,?,?,?,?,?,?,?)";
			SHA1 sha = new SHA1();
			
			try {
				String passSHA1 = sha.getHash(user.getPassword());
				
				conn = getConnection();
				PreparedStatement st = conn.prepareStatement(sqlAddUsuario);
				st.setString(1, user.getName());
				st.setString(2, user.getSurname1());
				st.setString(3, user.getSurname2());
				st.setString(4, user.getEmail());
				st.setString(5, user.getUsername());
				st.setString(6, passSHA1);
				st.setBoolean(7, user.isAdmin());
				st.setBoolean(8, user.isDesigner());
				st.setBoolean(9, user.isActive());
				
				st.executeUpdate();
				
				String sqlSelectUsuario = "select username from user where username=?";
				st.close();
				st = conn.prepareStatement(sqlSelectUsuario);
				st.setString(1, user.getUsername());
				ResultSet results = st.executeQuery();
				while (results.next()) {
					username = results.getString("username");
				}
				
			} catch (SQLException e) {
				logger.error("[UserDAO-addUser]: Error in SQL sentence: " + e.getLocalizedMessage());
			} catch (NoSuchAlgorithmException e) {
				logger.error("[UserDAO-addUser]: Error generating password hash: " + e.getLocalizedMessage());
			} catch (Exception e) {
				logger.error("[UserDAO-addUser]: Error" + e.getLocalizedMessage());
			} finally {
				if (conn != null) {
					try {
						logger.info("[UserDAO-addUser]: Closing DB connection...");
						conn.close();
					} catch (SQLException e) {
						logger.error("[UserDAO-addUser]: Error closing DB connection: " + e.getLocalizedMessage());
					}
				}
			}
		}else{
			logger.info("[UserDAO-addUsuario]: The user [" + user.getUsername() + "] exist");
			username = "";
		}

		return username;
	}
	
	/**
	 * Verifica si un usuario dado existe mediante el nombre de usuario, campo que
	 * es usado como clave en la tabla de usuarios, por lo que no puede haber dos 
	 * usuarios con el mismo nombre de usuario.
	 * 
	 * @param username Nombre de usuario a verificar.
	 * @return Boolean que indica si el nombre de usuario existe o no.
	 */
	private boolean existUser(String username){
		logger.info("[UserDAO-existUser]: Searchin user [" + username + "]...");
		String sqlSelectUsuario = "select name, surname1, surname2, email, username, password, admin, designer, active "
				+ "from user where username=?";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectUsuario);
			st.setString(1, username);

			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("[UserDAO-existUser]: Error in SQL sentence: "
					+ e.getLocalizedMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-existUser]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-existUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}

	/**
	 * Obtiene todos los usuarios registrados en el sistema.
	 * 
	 * @return Lista con los datos de todos los usuarios del sistema.
	 */
	public List<User> getAllUsers() {
		logger.info("[UserDAO-getAllUsers]: Searching all users...");

		List<User> usersResult = new ArrayList<User>();
		String sqlSelectUsuario = "select name, surname1, surname2, email, username, password, admin, designer, active "
			+ "from user";
		try {
			conn = getConnection();	
			PreparedStatement st = conn.prepareStatement(sqlSelectUsuario);

			// Ejecutamos las query
			ResultSet results = st.executeQuery();
			while (results.next()) {
				User user = new User();
				user.setName(results.getString("name"));
				user.setSurname1(results.getString("surname1"));
				user.setSurname2(results.getString("surname2"));
				user.setEmail(results.getString("email"));
				user.setUsername(results.getString("username"));
				user.setPassword(results.getString("password"));
				user.setAdmin(results.getBoolean("admin"));
				user.setDesigner(results.getBoolean("designer"));
				user.setActive(results.getBoolean("active"));
				usersResult.add(user);
			}
			return usersResult;
		} catch (SQLException e) {
			logger.error("[UserDAO-getAllUsers]: Error in SQL sentence: " + e.getLocalizedMessage());
			return null;
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-getAllUsers]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-getAllUsers]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Obtiene los usuarios del sistema que no se encuentran registrados en 
	 * una sala dada.
	 * Este método es usado para saber cuales son los usuarios a los cuales
	 * se les puede mandar una invitación puesto que no están registrados
	 * en la sala.
	 * 
	 * @param room Sala mediante la cual se obtienen los usuarios del sistema
	 * que no están registrados en ella.
	 * @return Lista con el nombre de usuario de los distintos usuarios que no
	 * están registrados en la sala.
	 */
	public List<String> getColDesUsers(Room room) {
		logger.info("[UserDAO-getColDesUsers]: Searching all users...");

		List<String> usersResult = new ArrayList<String>();
		String sqlSelectUsuario = "select username "
			+ "from user where active=1 and username not in (select username from roomuser where id_room=?)" +
					" and username not in (select username from roomuserinvitation where id_room=?)";
		try {
			conn = getConnection();	
			PreparedStatement st = conn.prepareStatement(sqlSelectUsuario);
			st.setInt(1, room.getId());
			st.setInt(2, room.getId());
			
			// Ejecutamos las query
			ResultSet results = st.executeQuery();
			while (results.next()) {
				usersResult.add(results.getString("username"));
			}
			return usersResult;
		} catch (SQLException e) {
			logger.error("[UserDAO-getColDesUsers]: Error in SQL sentence: " + e.getLocalizedMessage());
			return null;
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-getColDesUsers]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-getColDesUsers]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Actualiza los datos personales de un usuario, a excepción del nombre de usuario.
	 * 
	 * @param user Usuario con las modificaciónes.
	 * @param passChange Boolean que índica si el usuario ademas ha modificado la
	 * vieja contraseña.
	 * @return Boolean que indica si la actualización fue correcta o no.
	 */
	public boolean updateUser(User user, boolean passChange) {
		boolean update = false;

		logger.info("[UserDAO-updateUser]: Updating user [" + user.getUsername() + "]...");
		
		String sqlUpdateUsuario = "update user set name=?, surname1=?, surname2=?, email=?, password=?, admin=?, designer=?, active=? "
			+ "where username=?";

		SHA1 sha = new SHA1();
		
		try {
			String passSHA1 = sha.getHash(user.getPassword());
			
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlUpdateUsuario);
			st.setString(1, user.getName());
			st.setString(2, user.getSurname1());
			st.setString(3, user.getSurname2());
			st.setString(4, user.getEmail());
			if(passChange){
				st.setString(5, passSHA1);
			}else{
				st.setString(5, user.getPassword());
			}
			st.setBoolean(6, user.isAdmin());
			st.setBoolean(7, user.isDesigner());
			st.setBoolean(8, user.isActive());
			st.setString(9, user.getUsername());
			
			int result = st.executeUpdate();

			if(result == 1){
				update = true;
			}
			
		} catch (SQLException e) {
			logger.error("[UserDAO-updateUser]: Error in SQL sentence: " + e.getLocalizedMessage());
		} catch (NoSuchAlgorithmException e) {
			logger.error("[UserDAO-updateUser]: Error generating password hash: " + e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("[UserDAO-updateUser]: Error" + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-updateUser]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-updateUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return update;
	}
	
	public boolean deleteUser(User user) {
		boolean delete = false;

		logger.info("[UserDAO-deleteUser]: Deleting user [" + user.getUsername() + "]...");
		
		String sqlDeleteUsuario = "delete from user where username=?";
		
		try {
			
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUsuario);
			st.setString(1, user.getUsername());
			
			int result = st.executeUpdate();

			if(result == 1){
				delete = true;
			}
			
		} catch (SQLException e) {
			logger.error("[UserDAO-deleteUser]: Error in SQL sentence: " + e.getLocalizedMessage());
		} catch (Exception e) {
			logger.error("[UserDAO-deleteUser]: Error" + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-deleteUser]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-deleteUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return delete;
	}
	
	
	/**************************/
	/**   SESSION FUNCTIONS  **/
	/**************************/
	/**
	 * Función que valida la sesión de un usuario, actualizando el id de sesion,
	 * la fecha de última conexión y la de última operación.
	 * 
	 * @param username Nombre de usuario del usuario del cual se valida la sesión.
	 * @param sessionID Nuevo id de sesión para el usuario.
	 */
	public void validate(String username, String sessionID) {
		logger.info("[UserDAO-validate]: Validating session of user " + username);
		// Generamos la query
		String sql = "update user set sessionId=?, lastLogin=?, lastOperation=? where username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, sessionID);
			st.setTimestamp(2, new Timestamp(new java.util.Date().getTime()));
			st.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
			st.setString(4, username);
			int actualizados = st.executeUpdate();
			if (actualizados < 1) {
				logger.error("[UserDAO-validate]: Failed to validate the user " + username);
			}
		} catch (SQLException e) {
			logger.error("[UserDAO-validate]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-validate]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-validate]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Invalida la sesión de un usuario dado poniendo a null el valor actual
	 * de su id de sesión.
	 * 
	 * @param username Nombre de usuario del usuario del cual se intenta
	 * invalidar la sesión.
	 */
	public void invalidate(String username) {
		logger.info("[UserDAO-invalidate]: Invalidating session of user " + username);

		// Generamos la query
		String sql = "update user set sessionId=null where username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			int actualizados = st.executeUpdate();
			if (actualizados < 1) {
				logger.error("[UserDAO-validate]: Failed to invalidate the user " + username);
			}
			
		} catch (SQLException e) {
			logger.error("[UserDAO-validate]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-invalidate]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-invalidate]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Función encargada de verificar si la sesión de un usuario es válida en el 
	 * momento actual.
	 * 
	 * @param username Nombre de usuario del usuario sobre el cual se intenta
	 * verificar la sesion.
	 * @param sessionLength Tiempo máximo definido para la duración de una 
	 * sesión.
	 * @return Boolean que índica si la sesión del usuario ha caducado o no.
	 */
	public boolean checkUser(String username, int sessionLength) {

		return this.checkUser(username, null, sessionLength);
	}
	public boolean checkUser(String username, String sessionId, int sessionLength) {

		logger.info("[UserDAO-checkUser]: Checking validity of the session of user "+ username);

		String sql = "select sessionId, lastOperation from user where username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, username);
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				String dbSessionId = resultados.getString("sessionId");
				java.util.Date lastOperation = resultados
						.getTimestamp("lastOperation");

				if (dbSessionId == null || dbSessionId.trim().equals("")) {
					logger.info("[UserDAO-checkUser]: There is no user session for " + username);
					resultados.close();
					st.close();
					return false;
				}


				if (!dbSessionId.trim().equals(sessionId.trim())) {
					logger.info("[UserDAO-checkUser]: The session ID specified for the user "+username+" ID does not match the Login Register");
					resultados.close();
					st.close();
					return false;
				}

				long antes = lastOperation.getTime();
				long ahora = new java.util.Date().getTime();
				long diferencia = ahora - antes;

				if (sessionLength == Constants.UNDEFINED || diferencia < sessionLength * 60 * 1000) {
					resultados.close();
					st.close();
					sql = "update user set lastOperation=? where username=?";
					st = conn.prepareStatement(sql);
					st.setTimestamp(1, new Timestamp(ahora));
					st.setString(2, username);
					st.executeUpdate();
					st.close();
					return true;
				} else {
					logger.info("[UserDAO-checkUser]: The session of user"+ username +" has expired due to inactivity");
					resultados.close();
					st.close();
					//sql = "update user set sessionId=null where username=?";
					//st = conn.prepareStatement(sql);
					//st.setString(1, username);
					//st.executeUpdate();
					//st.close();
					this.invalidate(username);
					return false;
				}
			} else {
				logger.error("[UserDAO-checkUser]: The user don't exist [" + username +"]");
				resultados.close();
				st.close();
				return false;
			}
		} catch (SQLException e) {
			logger.error("[UserDAO-checkUser]: Error in SQL sentence: " + e.getLocalizedMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[UserDAO-checkUser]: Closing DB connection...");
					conn.close();
				} catch (SQLException e) {
					logger.error("[UserDAO-checkUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	/**
	 * Función encargada de finalizar la conexión del DAO con la base de datos.
	 */
	public void finalize() {

		try {
			if (conn != null) {
				logger.info("[UserDAO-finalize]: Closing DB connection...");
				conn.close();
			}
			super.finalize();
		} catch (SQLException e) {
			logger.error("[UserDAO-finalize]: Error closing DB connection: " + e.getLocalizedMessage());

		} catch (Throwable e) {
			logger.error("[UserDAO-finalize]: Error closing DB connection: " + e.getLocalizedMessage());

		}
	}
	

}
