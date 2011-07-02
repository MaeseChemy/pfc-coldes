package es.uc3m.coldes.control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.RoomUserPencilRequest;
import es.uc3m.coldes.util.Policy;
import es.uc3m.coldes.util.PolicyFIFO;


public class PencilDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	private Connection conn = null;
	private Policy policy;
	public PencilDAO() {
		super();
		this.policy = new PolicyFIFO();
	}

	public PencilDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}

	public boolean pencilBusy(Room room) {
		logger.info("[PencilDAO-pencilBusy]: Searching if pencil is busy in room [" + room.getId() + "]");
		String sqlSelectPencilRoom = "select id_room, username, userrol, requesttime "
				+ "from roomuserpencil where id_room=?";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectPencilRoom);
			st.setInt(1,  room.getId());

			// Ejecutamos las query
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("[PencilDAO-pencilBusy]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[PencilDAO-pencilBusy]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[PencilDAO-pencilBusy]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public boolean isUserPencilOwner(Room room, String username) {
		logger.info("[PencilDAO-isUserPencilOwner]: Searching if the user ["+username+"] is the pencil the owner of room [" + room.getId() + "]");
		String sqlSelectPencilRoom = "select id_room, username, userrol, requesttime "
				+ "from roomuserpencil where id_room=? and username=? and pencilowner=1";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectPencilRoom);
			st.setInt(1,  room.getId());
			st.setString(2, username);

			// Ejecutamos las query
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.error("[PencilDAO-isUserPencilOwner]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[PencilDAO-isUserPencilOwner]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[PencilDAO-isUserPencilOwner]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	
	public boolean addPencilRequest(Room room, String username, int userrol, boolean owner) {
		logger.info("[PencilDAO-addPencilRequest]: Searching if pencil is busy in room [" + room.getId() + "]");
		String sqlSelectRoom = "insert into roomuserpencil (id_room, username, userrol, requesttime, pencilowner) "
				+ "values (?,?,?,?,?)";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectRoom);
			st.setInt(1, room.getId());
			st.setString(2, username);
			st.setInt(3, userrol);
			st.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
			st.setBoolean(5, owner);
			
			// Ejecutamos las query
			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-addPencilRequest]: The pencil request can`t be create");
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			logger.error("[PencilDAO-addPencilRequest]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[PencilDAO-addPencilRequest]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[PencilDAO-addPencilRequest]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}

	public String deleteUserRoomRequests(String username, int roomId) {
		String result = null;
		try {
			logger.info("[PencilDAO-pencilBusy]: Search rooms where pencil owner is the user [" + username + "]");
			String sqlPencilOwner = "select id_room, username, userrol, requesttime "
				+ "from roomuserpencil where username=? and id_room=? and pencilowner=1";
			conn = getConnection();
			
			PreparedStatement st = conn.prepareStatement(sqlPencilOwner);
			st.setString(1, username);
			st.setInt(2, roomId);
			st.executeQuery();

			logger.info("[PencilDAO-pencilBusy]: Deleting all request of user [" + username + "]");
			String sqlDelete = "delete from roomuserpencil where username=? and id_room=?";

			st = conn.prepareStatement(sqlDelete);
			st.setString(1, username);
			st.setInt(2, roomId);

			// Ejecutamos las query
			st.executeUpdate();
			
			//Gestionamos las salas que deja el usuario sin propietario del pincel

			result = this.manageRoomPencil(roomId);
		} catch (SQLException e) {
			logger.error("[PencilDAO-pencilBusy]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[PencilDAO-pencilBusy]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[PencilDAO-pencilBusy]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return result;
	}

	public String manageRoomPencil(int idRoom) {
		String result = null;
		try {
			logger.info("[PencilDAO-manageRoomPencil]: Search request pencil of room [" + idRoom + "]");
			String sqlPencilOwner = "select id_room, username, userrol, requesttime, pencilowner "
				+ "from roomuserpencil where id_room=? order by requesttime asc";
			conn = getConnection();
			
			PreparedStatement st = conn.prepareStatement(sqlPencilOwner);
			st.setInt(1, idRoom);
			List<RoomUserPencilRequest> pencilRequestUser = new ArrayList<RoomUserPencilRequest>();
			ResultSet resultados = st.executeQuery();
			while (resultados != null && resultados.next()) {
				RoomUserPencilRequest rupr = new RoomUserPencilRequest();
				rupr.setIdRoom(resultados.getInt("id_room"));
				rupr.setUsername(resultados.getString("username"));
				rupr.setUserrol(resultados.getInt("userrol"));
				rupr.setRequestTime(resultados.getDate("requesttime"));
				rupr.setPencilowner(resultados.getBoolean("pencilowner"));
				pencilRequestUser.add(rupr);
			}
			
			String nextUser = this.policy.getNextUser(pencilRequestUser);
			if(nextUser != null){
				sqlPencilOwner = "update roomuserpencil set pencilowner=1 where id_room=?";
				st = conn.prepareStatement(sqlPencilOwner);
				st.setInt(1, idRoom);
				st.executeUpdate();
				result=nextUser;
			}
		} catch (SQLException e) {
			logger.error("[PencilDAO-pencilBusy]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[PencilDAO-pencilBusy]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[PencilDAO-pencilBusy]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return result;
	}
	
	



	
}
