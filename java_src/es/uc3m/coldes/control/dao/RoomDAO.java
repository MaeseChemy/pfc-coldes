package es.uc3m.coldes.control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import es.uc3m.coldes.util.Constants;

public class RoomDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	private Connection conn = null;

	public RoomDAO() {
		super();
	}

	public RoomDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}
	
	public int addRoom(Room room) {
		int idRoom = -1;
		if(!this.existRoom(room.getName(), room.getOwner())){
			logger.info("[RoomDAO-addRoom]: Adding new room [" + room.getName() + "]...");
			
			String sqlAddRoom = "insert into room (roomname, roomdescription, owner) " +
					"values (?,?,?)";
			try {
	
				conn = getConnection();
				PreparedStatement st = conn.prepareStatement(sqlAddRoom);
				st.setString(1, room.getName());
				st.setString(2, room.getDescription());
				st.setString(3, room.getOwner());
	
				int i = st.executeUpdate();
				if (i != 1){
					logger.info("[RoomDAO-addRoom]: The room can`t be create");
				}
				
				// Leemos el User recien creado para ver su ID
				String sqlSelectUsuario = "select id from room where roomname=? and owner=?";
				st.close();
				st = conn.prepareStatement(sqlSelectUsuario);
				st.setString(1, room.getName());
				st.setString(2, room.getOwner());
				ResultSet resultados = st.executeQuery();
				while (resultados.next()) {
					idRoom = resultados.getInt("id");
					room.setId(idRoom);
					String sqlInserUserRoom = "insert into roomuser (id_room, username, rol) " +
					"values (?,?,?)";
					conn = getConnection();
					PreparedStatement st1 = conn.prepareStatement(sqlInserUserRoom);
					st1.setInt(1, room.getId());
					st1.setString(2, room.getOwner());
					st1.setInt(3, Constants.OWNER_ROL);
					i = st1.executeUpdate();
					if (i != 1){
						logger.info("[RoomDAO-addRoom]: Error creating user-room relation");
					}
				}

			} catch (SQLException e) {
				logger.error("[RoomDAO-addRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
			} finally {
				if (conn != null) {
					try {
						logger.info("[RoomDAO-addRoom]: Closing DB connection");
						conn.close();
					} catch (SQLException e) {
						logger.error("[RoomDAO-addRoom]: Error closing DB connection: " + e.getLocalizedMessage());
					}
				}
			}
		}else{
			logger.info("[RoomDAO-addRoom]: The room [" + room.getName() + "] already exist...");
			idRoom = -2;
		}
		return idRoom;
	}
	
	private boolean existRoom(String name, String owner) {
		logger.info("[RoomDAO-existRoom]: Searching room [" + name + "] of user [" + owner + "]...");
		String sqlSelectRoom = "select id, roomname, roomdescription, owner "
				+ "from room where roomname=? and owner=?";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectRoom);
			st.setString(1, name);
			st.setString(2, owner);

			// Ejecutamos las query
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("[RoomDAO-addRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-addRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-addRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public int registerUserRoom(User user, Room room) {
		int idRoom = -1;
		logger.info("[RoomDAO-addRoom]: Adding new user-room relation ...");
		String sqlInserUserRoom = "insert into roomuser (id_room, username, rol) " +
		"values (?,?,?)";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlInserUserRoom);
			st.setInt(1, room.getId());
			st.setString(2, user.getUsername());
			st.setInt(3, Constants.COLABORATOR_ROL);

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-addRoom]: The relation can't be create");
				idRoom = -2;
			}else{
				idRoom = 0;
			}
			
		} catch (SQLException e) {
			logger.error("[RoomDAO-addRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-addRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-addRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return idRoom;
	}
	
	public List<UserRoom> getUserRooms(User user){
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getUserRooms]: Searching rooms of user ["+user.getUsername()+"]..");
		String sqlSelectUserSala = "select id_room, username, rol " +
		"from roomuser where username=?";
		try {
			conn = getConnection();

			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserSala);
			stUserSala.setString(1, user.getUsername());
			// Ejecutamos las query
			ResultSet resultados = stUserSala.executeQuery();
			while (resultados != null && resultados.next()) {
				Room room = this.findRoomById(resultados.getInt("id_room"));
				if(room != null){
					UserRoom newUserRoom = new UserRoom();
					newUserRoom.setRoom(room);
					newUserRoom.setRoomName(room.getName());
					newUserRoom.setOwnerUserName(room.getOwner());
					newUserRoom.setUserName(user.getUsername());
					newUserRoom.setRol(resultados.getInt("rol"));
					int tipoRol = resultados.getInt("rol");
					switch (tipoRol) {
						case Constants.OWNER_ROL:
							newUserRoom.setRolDescription("Owner");
							break;
						case Constants.MODERATOR_ROL:
							newUserRoom.setRolDescription("Moderator");
							break;
						case Constants.COLABORATOR_ROL:
							newUserRoom.setRolDescription("Colaborator");
							break;
						case Constants.GUEST_ROL:
							newUserRoom.setRolDescription("Guest");
							break;
					}
					results.add(newUserRoom);
				}
			
			}

		} catch (SQLException e) {
			logger.error("[RoomDAO-getUserRooms]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-getUserRooms]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-getUserRooms]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return results;
	}

	private Room findRoomById(int id){
		Room room = null;
		logger.info("[RoomDAO-findRoomById]: Searching room by id...");
		String sqlSelectRoom = "select id, roomname, roomdescription, owner "
				+ "from room where id=?";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectRoom);
			st.setInt(1, id);

			// Ejecutamos las query
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				room = new Room();
				room.setId(resultados.getInt("id"));
				room.setName(resultados.getString("roomname"));
				room.setDescription(resultados.getString("roomdescription"));
				room.setOwner(resultados.getString("owner"));
			}
		} catch (SQLException e) {
			logger.error("[RoomDAO-findRoomById]: Error in SQL sentence: " + e.getLocalizedMessage());		
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-findRoomById]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-findRoomById]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return room;
	}

	public List<Room> getColDesRooms() {
		List<Room> results = new ArrayList<Room>();
		logger.info("[RoomDAO-getColDesRooms]: Searching rooms of ColDes...");
		String sqlSelectUserSala = "select id, roomname, roomdescription, owner " +
		"from room";
		try {
			conn = getConnection();

			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserSala);
			// Ejecutamos las query
			ResultSet resultados = stUserSala.executeQuery();
			while (resultados != null && resultados.next()) {
				Room room = new Room();

				room.setId(resultados.getInt("id"));
				room.setName(resultados.getString("roomname"));
				room.setDescription(resultados.getString("roomdescription"));
				room.setOwner(resultados.getString("owner"));	
				
				results.add(room);	
			}

		} catch (SQLException e) {
			logger.error("[RoomDAO-getColDesRooms]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-getColDesRooms]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-getColDesRooms]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return results;
	}



}
