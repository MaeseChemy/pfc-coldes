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

/**
 * Mediante este DAO se obtienen las operaciones fundamentales
 * para trabajar con los distintos diseños del sistema.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class DesignDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());
	
	/**
	 * Conexión a la base de datos.
	 */
	private Connection conn = null;
	
	/**
	 * Constructor por defecto que invoca a la clase padre BBDD.
	 */
	public DesignDAO() {
		super();
	}
	/**
	 * Constructor que forma el DAO a partir de unas propiedades
	 * mediante el constructor de la clase padre BBDD.
	 * 
	 * @param coldesignProperties Propiedades
	 */
	public DesignDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}
	
	/**
	 * Asigna el contenido del lienzo de una sala. Cada sala adminte por el
	 * momento un único diseño a lo largo del tiempo, por ello, se busca si
	 * la sala tiene algún diseño asociado, en caso afirmativo, se actualiza
	 * el valor del contenido, y en caso contrario se inserta un nuevo registro.
	 * 
	 * MEJORA: Que una sala pueda tener guardados varios diseños, por ello
	 * se deja esta implementación aunque a priori pueda resultar redundante.
	 * 
	 * @param room Sala a la que se quiere asociar el contenido.
	 * @param content Contenido del lienzo.
	 * @return Boolean que indica si la operación se realizo correctamente 
	 * o no.
	 */
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
	
	/**
	 * Desvincula el diseño perteneciente a una sala. Para ello borra el vinculo
	 * que se crea al asociar un diseño a una sala.
	 * 
	 * @param room Sala de la cual se quiere desvincular el actual diseño de
	 * la misma.
	 * @return Boolean que indica el resultado de la operación.
	 */
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
	
	/**
	 * Obtiene el diseño asociado a una sala dada.
	 * 
	 * @param room Sala de la que se quiere obtener el diseño actual.
	 * @return Array de bytes que representa el contenido del lienzo
	 * de la sala.
	 */
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
	
	/**
	 * Asocia un diseño a un usuario que ha decidido guardarlo. El
	 * propio objecto Design contiene el nombre de usuario.
	 * 
	 * @param design Diseño que se asocia.
	 * @return Boolean que indica el resultado de la operación.
	 */
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
	
	/**
	 * Obtiene todos los diseños que ha guardado un usuario dado.
	 * 
	 * @param user Usuario del que se quieren obtener los diseños.
	 * @return Lista de diseños del usuario.
	 */
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
	
	/**
	 * Desvincula un diseño de un usuario dado. Para ello borra el vinculo
	 * que se crea entre el diseño y el usuario en el momento de su
	 * vinculación.
	 * 
	 * @param design Diseño que se quiere borrar.
	 * @return Boolean que indica el resultado de la operacion.
	 */
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
