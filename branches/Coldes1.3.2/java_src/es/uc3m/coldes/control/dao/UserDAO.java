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

public class UserDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	private Connection conn = null;

	public UserDAO() {
		super();
	}

	public UserDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}
	
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
	

	public List<String> getColDesUsers(Room room) {
		logger.info("[UserDAO-getColDesUsers]: Searching all users...");

		List<String> usersResult = new ArrayList<String>();
		String sqlSelectUsuario = "select username "
			+ "from user where active=1 and username not in (select username from roomuser where id_room=?)";
		try {
			conn = getConnection();	
			PreparedStatement st = conn.prepareStatement(sqlSelectUsuario);
			st.setInt(1, room.getId());
			
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

	public boolean updateUser(User user, boolean passChange) {
		boolean update = false;

		logger.info("[UserDAO-addUser]: Updating user [" + user.getUsername() + "]...");
		
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

		return update;
	}

	/**************************/
	/**   SESSION FUNCTIONS  **/
	/**************************/
	
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
	
	public void invalidate(String username) {
		logger.info("[UserDAO-invalidate]: Invalidating session of user " + username);

		// Generamos evento de seguridad de salida
		//this.generateLogoutEvent(username);

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
