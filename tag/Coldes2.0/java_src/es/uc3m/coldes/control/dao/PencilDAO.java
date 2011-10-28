package es.uc3m.coldes.control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.RoomUserPencilRequest;
import es.uc3m.coldes.util.Policy;
import es.uc3m.coldes.util.PolicyFIFO;

/**
 * Mediante este DAO se obtienen las operaciones fundamentales
 * para trabajar con los pinceles de las salas y sus
 * peticiones.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class PencilDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());
	
	/**
	 * Conexión a la base de datos.
	 */
	private Connection conn = null;
	/**
	 * Politica mediante la cual se adminsitran las distintas
	 * peticiones de pinceles en el sistema
	 */
	private Policy policy;
	
	/**
	 * Constructor por defecto que invoca a la clase padre BBDD.
	 */
	public PencilDAO() {
		super();
		//Asignamos una politica FIFO por defecto
		this.policy = new PolicyFIFO();
	}
	
	/**
	 * Constructor que forma el DAO a partir de unas propiedades
	 * mediante el constructor de la clase padre BBDD.
	 * 
	 * @param coldesignProperties Propiedades
	 */
	public PencilDAO(Properties coldesignProperties) {
		super(coldesignProperties);
		//Asignamos una politica FIFO por defecto
		this.policy = new PolicyFIFO();
	}
	
	/**
	 * Obtiene si el pincel de una sala dada esta siendo usado o no.
	 * 
	 * @param room Sala de la cual se quiere obtener si el pincel
	 * esta siendo usado o no.
	 * @return Boolean que indica si se esta usando (true) o no (false).
	 */
	public boolean pencilBusy(Room room) {
		logger.info("[PencilDAO-pencilBusy]: Searching if pencil is busy in room [" + room.getId() + "]");
		String sqlSelectPencilRoom = "select id_room, username, userfunction, requesttime "
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
	
	/**
	 * Verifica si un usuario es el dueño del pincel de una determinada
	 * sala.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del usuario sobre el cual se
	 * quiere verificar que sea o no el propietario del pincel.
	 * @return Boolean que indica si el usuario es o no propietario 
	 * del pincel.
	 */
	public boolean isUserPencilOwner(Room room, String username) {
		logger.info("[PencilDAO-isUserPencilOwner]: Searching if the user ["+username+"] is the pencil the owner of room [" + room.getId() + "]");
		String sqlSelectPencilRoom = "select id_room, username, userfunction, requesttime "
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
	
	/**
	 * Función mediante la cual se añade una nueva petición de pincel de la sala
	 * al sistema.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del usuario que realiza la petición.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @param owner Indica si la petición es propietaria o no del pincel. 
	 * Este parametro se usa para cuando el pincel no esta siendo usado
	 * por ningun usuario.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean addPencilRequest(Room room, String username, int userfunction, boolean owner) {
		logger.info("[PencilDAO-addPencilRequest]: Searching if pencil is busy in room [" + room.getId() + "]");
		String sqlSelectRoom = "insert into roomuserpencil (id_room, username, userfunction, requesttime, pencilowner) "
				+ "values (?,?,?,?,?)";
		try {
			conn = getConnection();

			PreparedStatement st = conn.prepareStatement(sqlSelectRoom);
			st.setInt(1, room.getId());
			st.setString(2, username);
			st.setInt(3, userfunction);
			st.setTimestamp(4, new Timestamp(new Date().getTime()));
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
	/**
	 * Función que borra las peticiones de un usuario de una sala. Este
	 * metodo se invocara cuando un usuario salga del sistema o de una
	 * determinada sala unicamente.
	 * En caso de salir del sistema, se llamara a este metodo con los
	 * ids de las distintas salas en las que el usuario se encontraba
	 * participando.
	 * 
	 * @param username Nombre de usuario del usuario.
	 * @param roomId Id de la sala de la que se quieren borrar
	 * las peticiones de pincel.
	 * @return String que contiene el nombre de usuario del siguiente
	 * usuario propietario del pincel de la sala, o null en caso de
	 * no haber un siguiente.
	 */
	public String deleteUserRoomRequests(String username, int roomId) {
		String result = null;
		try {
			logger.info("[PencilDAO-pencilBusy]: Search rooms where pencil owner is the user [" + username + "]");
			String sqlPencilOwner = "select id_room, username, userfunction, requesttime "
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
	
	/**
	 * Se manejan las distintas peticiones de una sala dada para obtener el siguiente
	 * propietario del pincel y actualizar los datos de las peticiones actuales del
	 * sistema.
	 * 
	 * @param idRoom Id de la sala de la que se van a manejar las peticiones.
	 * @return
	 */
	public String manageRoomPencil(int idRoom) {
		String result = null;
		try {
			logger.info("[PencilDAO-manageRoomPencil]: Search request pencil of room [" + idRoom + "]");
			String sqlPencilOwner = "select id_room, username, userfunction, requesttime, pencilowner "
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
				rupr.setUserfunction(resultados.getInt("userfunction"));
				rupr.setRequestTime(resultados.getDate("requesttime"));
				rupr.setPencilowner(resultados.getBoolean("pencilowner"));
				pencilRequestUser.add(rupr);
			}
			
			//De todas las peticiones, obtenemos el nombre del siguiente propietario
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
