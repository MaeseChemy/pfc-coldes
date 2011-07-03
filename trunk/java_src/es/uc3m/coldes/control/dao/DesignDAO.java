package es.uc3m.coldes.control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;

public class DesignDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	private Connection conn = null;
	public DesignDAO() {
		super();
	}

	public DesignDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}

	public boolean saveDesignToCanvas(Room room, byte[] content) {
		logger.info("[DesignDAO-saveDesignToCanvas]: Saving content canvas to room [" + room.getId() + "]");
		String sql;
		if(getRoomSaveContent(room) != null){
			sql = "update roomdesign set designcontent=? where id_room=?";
		}else{
			sql = "insert into roomdesign (designcontent,id_room) values (?,?)";
		}
			
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setBytes(1, content);
			st.setInt(2, room.getId());
			
			// Ejecutamos las query
			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[DesignDAO-saveDesignToCanvas]: Can't save the content...");
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			logger.error("[DesignDAO-saveDesignToCanvas]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-saveDesignToCanvas]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-saveDesignToCanvas]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public boolean removeDesingRoom(Room room) {
		logger.info("[DesignDAO-removeDesingRoom]: Removing content canvas to room [" + room.getId() + "]");
		String sql;
		sql = "delete from roomdesign where id_room=?";
			
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, room.getId());
			
			// Ejecutamos las query
			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[DesignDAO-removeDesingRoom]: Can't remove the content...");
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			logger.error("[DesignDAO-removeDesingRoom]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-removeDesingRoom]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-removeDesingRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public byte[] getRoomSaveContent(Room room) {
		logger.info("[DesignDAO-getRoomSaveContent]: Searching if pencil is busy in room [" + room.getId() + "]");
		String sqlSelectPencilRoom = "select designcontent "
				+ "from roomdesign where id_room=?";
		byte[] designContent = null;
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectPencilRoom);
			st.setInt(1,  room.getId());

			// Ejecutamos las query
			ResultSet resultados = st.executeQuery();
			if (resultados != null && resultados.next()) {
				designContent = resultados.getBytes("designcontent");
			}
		} catch (SQLException e) {
			logger.error("[DesignDAO-getRoomSaveContent]: Error in SQL sentence: " + e.getLocalizedMessage());	
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-getRoomSaveContent]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-getRoomSaveContent]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return designContent;
	}

	public boolean saveDesignToUser(Design design) {
		logger.info("[DesignDAO-saveDesignToUser]: Saving content canvas to user [" + design.getUsername() + "]");
		String sql;

		sql = "insert into userdesign (username, designname, designcontent) values (?,?,?)";
			
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, design.getUsername());
			st.setString(2, design.getDesignname());
			st.setBytes(3, design.getDesigncontent());
			
			// Ejecutamos las query
			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[DesignDAO-saveDesignToUser]: Can't save the content...");
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			logger.error("[DesignDAO-saveDesignToUser]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-saveDesignToUser]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-saveDesignToUser]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
	public List<Design> getUserDesigns(User user){
		List<Design> results = new ArrayList<Design>();
		logger.info("[DesignDAO-getUserDesigns]: Searching rooms of user ["+user.getUsername()+"]..");
		String sqlSelectUserDesign = "select username, designname, designcontent " +
		"from userdesign where username=?";
		try {
			conn = getConnection();

			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserDesign);
			stUserSala.setString(1, user.getUsername());
			// Ejecutamos las query
			ResultSet resultados = stUserSala.executeQuery();
			while (resultados != null && resultados.next()) {
				Design design = new Design();
				design.setUsername(resultados.getString("username"));
				design.setDesignname(resultados.getString("designname"));
				design.setDesigncontent(resultados.getBytes("designcontent"));
				results.add(design);
			}

		} catch (SQLException e) {
			logger.error("[DesignDAO-getUserDesigns]: Error in SQL sentence: " + e.getLocalizedMessage());
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-getUserDesigns]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-getUserDesigns]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		return results;
	}

	public boolean removeUserDesign(Design design) {
		logger.info("[DesignDAO-removeUserDesign]: Saving content canvas to user [" + design.getUsername() + "]");
		String sql;

		sql = "delete from userdesign where username=? and designname=?";
			
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, design.getUsername());
			st.setString(2, design.getDesignname());
			
			// Ejecutamos las query
			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[DesignDAO-removeUserDesign]: Can't remove the design...");
				return false;
			}else{
				return true;
			}
			
		} catch (SQLException e) {
			logger.error("[DesignDAO-removeUserDesign]: Error in SQL sentence: " + e.getLocalizedMessage());	
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[DesignDAO-removeUserDesign]: Closing DB connection");
					conn.close();
				} catch (SQLException e) {
					logger.error("[DesignDAO-removeUserDesign]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
	}
	
}
