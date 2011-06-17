package es.uc3m.coldes.control.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.security.SHA1;
import es.uc3m.coldes.model.User;

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

}
