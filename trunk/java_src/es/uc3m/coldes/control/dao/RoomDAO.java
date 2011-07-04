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
			
			String sqlAddRoom = "insert into room (roomname, roomdescription, owner, private, participationtype) " +
					"values (?,?,?,?,?)";
			try {
	
				conn = getConnection();
				PreparedStatement st = conn.prepareStatement(sqlAddRoom);
				st.setString(1, room.getName());
				st.setString(2, room.getDescription());
				st.setString(3, room.getOwner());
				st.setBoolean(4, room.getPrivateRoom());
				st.setInt(5, room.getParticipationType());
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
	
	public int deleteRoom(Room room) {
		int result = 0;
	
		logger.info("[RoomDAO-deleteRoom]: Removing room [" + room.getName() + "]...");
		
		String sqlAddRoom = "delete from room where id=?";
		try {

			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlAddRoom);
			st.setInt(1, room.getId());

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-deleteRoom]: The room can`t be deleted");
				result = -1;
			}
			

		} catch (SQLException e) {
			logger.error("[RoomDAO-deleteRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-deleteRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-deleteRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return result;
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
			logger.error("[RoomDAO-existRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-existRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-existRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public int registerUserRoom(User user, Room room) {
		return this.registerUserRoom(user.getUsername(), room, Constants.COLABORATOR_ROL);
	}
	
	private int registerUserRoom(String username, Room room, int rol) {
		int idRoom = -1;
		logger.info("[RoomDAO-addRoom]: Adding new user-room relation ...");
		String sqlInserUserRoom = "insert into roomuser (id_room, username, rol) " +
		"values (?,?,?)";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlInserUserRoom);
			st.setInt(1, room.getId());
			st.setString(2, username);
			st.setInt(3, rol);

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
		String sqlSelectRoom = "select id, roomname, roomdescription, owner, private, participationtype "
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
				room.setPrivateRoom(resultados.getBoolean("private"));
				room.setParticipationType(resultados.getInt("participationtype"));
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
		String sqlSelectUserSala = "select id, roomname, roomdescription, owner, private, participationtype " +
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
				room.setPrivateRoom(resultados.getBoolean("private"));
				room.setParticipationType(resultados.getInt("participationtype"));
				
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
	
	public List<Room> getColDesPublicRooms() {
		List<Room> results = new ArrayList<Room>();
		logger.info("[RoomDAO-getColDesPublicRooms]: Searching rooms of ColDes...");
		String sqlSelectUserSala = "select id, roomname, roomdescription, owner, private, participationtype " +
		"from room where private=0";
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
				room.setPrivateRoom(resultados.getBoolean("private"));
				room.setParticipationType(resultados.getInt("participationtype"));
				
				results.add(room);	
			}

		} catch (SQLException e) {
			logger.error("[RoomDAO-getColDesPublicRooms]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-getColDesPublicRooms]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-getColDesPublicRooms]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return results;
	}
	

	public List<String> enterInRoom(User user, Room room) {
		logger.info("[RoomDAO-enterInRoom]: Searching roomusers ...");
		String sqlSelectUserRoom = "select username from roomuser where id_room=? and online=1";
		List<String> resultsUsernames = new ArrayList<String>();
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlSelectUserRoom);
			st = conn.prepareStatement(sqlSelectUserRoom);
			st.setInt(1, room.getId());
			ResultSet resultados = st.executeQuery();
			while (resultados != null && resultados.next()) {
				resultsUsernames.add(resultados.getString("username"));
			}
			
			logger.info("[RoomDAO-enterInRoom]: Updating  user-room relation ...");
			String sqlUpdateUserRoom = "update roomuser set online=1 where username=? and id_room=?";
			st = conn.prepareStatement(sqlUpdateUserRoom);
			st.setString(1, user.getUsername());
			st.setInt(2, room.getId());
			st.executeUpdate();
			// Ejecutamos las query
			
			
		} catch (SQLException e) {
			logger.error("[RoomDAO-enterInRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-enterInRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-enterInRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return resultsUsernames;
	}

	public int deleteUserRoom(UserRoom userRoom) {
		int result = 0;
		logger.info("[RoomDAO-deleteUserRoom]: Removing user-room relation ...");
		String sqlDeleteUserRoom = "delete from roomuser where id_room=? and username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUserRoom);
			st.setInt(1, userRoom.getRoom().getId());
			st.setString(2, userRoom.getUserName());

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-deleteUserRoom]: The relation can't be deleted");
				result = -1;
			}
			
		} catch (SQLException e) {
			logger.error("[RoomDAO-deleteUserRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-deleteUserRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-deleteUserRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return result;
	}

	public int roomLogout(User user, Room room) {
		int result = 0;
		logger.info("[RoomDAO-roomLogout]: Logout from room ...");
		String sqlDeleteUserRoom = "update roomuser set online=0 where id_room=? and username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUserRoom);
			st.setInt(1, room.getId());
			st.setString(2, user.getUsername());

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-roomLogout]: The relation can't be deleted");
				result = -1;
			}
						
		} catch (SQLException e) {
			logger.error("[RoomDAO-roomLogout]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-roomLogout]: Closing DB connection");
					conn.close();
					
				} catch (SQLException e) {
					logger.error("[RoomDAO-roomLogout]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return result;
	}

	public List<UserRoom> getRoomUsers(Room room) {
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getUserRooms]: Searching users of room ["+room.getId()+"]..");
		String sqlSelectUserSala = "select id_room, username, rol " +
		"from roomuser where id_room=?";
		try {
			conn = getConnection();

			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserSala);
			stUserSala.setInt(1, room.getId());
			// Ejecutamos las query
			ResultSet resultados = stUserSala.executeQuery();
			while (resultados != null && resultados.next()) {
				Room roomAux = this.findRoomById(resultados.getInt("id_room"));
				if(roomAux != null){
					UserRoom newUserRoom = new UserRoom();
					newUserRoom.setRoom(roomAux);
					newUserRoom.setRoomName(roomAux.getName());
					newUserRoom.setOwnerUserName(roomAux.getOwner());
					newUserRoom.setUserName(resultados.getString("username"));
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

	public UserRoom sendRoomInvitation(String username, Room room, int rol) {
		UserRoom result = null;
		logger.info("[RoomDAO-sendRoomInvitation]: New room invitation...");
		String sqlInserRoomInvitation = "insert into roomuserinvitation (username, id_room, rol) " +
		"values (?,?,?)";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlInserRoomInvitation);
			st.setString(1, username);
			st.setInt(2, room.getId());
			st.setInt(3, rol);

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-sendRoomInvitation]: The invitation can't be create");
			}else{
				result = new UserRoom();
				result.setRoom(room);
				result.setRoomName(room.getName());
				result.setOwnerUserName(room.getOwner());
				result.setUserName(username);
				result.setRol(rol);
				switch (rol) {
					case Constants.OWNER_ROL:
						result.setRolDescription("Owner");
						break;
					case Constants.MODERATOR_ROL:
						result.setRolDescription("Moderator");
						break;
					case Constants.COLABORATOR_ROL:
						result.setRolDescription("Colaborator");
						break;
					case Constants.GUEST_ROL:
						result.setRolDescription("Guest");
						break;
				}
			}
			
		} catch (SQLException e) {
			logger.error("[RoomDAO-sendRoomInvitation]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-sendRoomInvitation]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-sendRoomInvitation]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

		return result;
	}
	
	public int removeUserInvitation(UserRoom userRoom) {
		int result = -1;
		logger.info("[RoomDAO-removeUserInvitation]: Remove user invitation...");
		String sqlDeleteRoomInvitation = "delete from roomuserinvitation where username=? and id_room=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteRoomInvitation);
			st.setString(1, userRoom.getUserName());
			st.setInt(2, userRoom.getRoom().getId());

			int i = st.executeUpdate();
			if (i < 0){
				logger.info("[RoomDAO-removeUserInvitation]: The invitation can't be deleted");
				result = -1;
			}else{
				result = 0;
			}
			
		} catch (SQLException e) {
			logger.error("[RoomDAO-removeUserInvitation]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-removeUserInvitation]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-removeUserInvitation]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return result;
	}

	public List<UserRoom> getAllUserRoomInvitation(String username) {
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getAllUserRoomInvitation]: Searching invitations of user ["+username+"]..");
		String sqlSelectUserInvitation = "select username, id_room, rol " +
		"from roomuserinvitation where username=?";
		try {
			conn = getConnection();

			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserInvitation);
			stUserSala.setString(1, username);
			// Ejecutamos las query
			ResultSet resultados = stUserSala.executeQuery();
			while (resultados != null && resultados.next()) {
				Room roomAux = this.findRoomById(resultados.getInt("id_room"));
				if(roomAux != null){
					UserRoom newUserRoom = new UserRoom();
					newUserRoom.setRoom(roomAux);
					newUserRoom.setRoomName(roomAux.getName());
					newUserRoom.setOwnerUserName(roomAux.getOwner());
					newUserRoom.setUserName(resultados.getString("username"));
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
			logger.error("[RoomDAO-getAllUserRoomInvitation]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-getAllUserRoomInvitation]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[RoomDAO-getAllUserRoomInvitation]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return results;
	}

	public int manageUserRoomRelation(UserRoom userRoom, boolean insert) {
		logger.info("[RoomDAO-manageUserRoomRelation]: Deleting room invitation...");
		if(this.removeUserInvitation(userRoom) < 0){
			logger.error("[RoomDAO-manageUserRoomRelation]: Error in room invitation delete...");
			return -1;
		}else{
			if(insert){
				logger.error("[RoomDAO-manageUserRoomRelation]: Insert new roomuser relation...");
				return this.registerUserRoom(userRoom.getUserName(), userRoom.getRoom(), userRoom.getRol());
			}else{
				return 0;
			}
		}
	}

	public boolean updateRoom(Room room) {
		logger.info("[RoomDAO-updateRoom]: Logout from room ...");
		String sqlDeleteUserRoom = "update room set roomname=?, roomdescription=?, owner=?, private=?, participationtype=? where id_room=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUserRoom);
			st.setString(1, room.getName());
			st.setString(2, room.getDescription());
			st.setString(3, room.getOwner());
			st.setBoolean(4, room.getPrivateRoom());
			st.setInt(5, room.getParticipationType());

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-updateRoom]: The room can't be updated");
				return false;
			}else{
				return true;
			}
						
		} catch (SQLException e) {
			logger.error("[RoomDAO-updateRoom]: Error in SQL sentence: " + e.getLocalizedMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-updateRoom]: Closing DB connection");
					conn.close();
					
				} catch (SQLException e) {
					logger.error("[RoomDAO-updateRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

	}



}
