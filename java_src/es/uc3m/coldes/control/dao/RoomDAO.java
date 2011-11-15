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

import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import es.uc3m.coldes.util.Constants;

/**
 * Mediante este DAO se obtienen las operaciones fundamentales
 * para trabajar con las distintas salas del sistema.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class RoomDAO extends BBDD{

	static Logger logger = Logger.getLogger(UserDAO.class.getName());

	/**
	 * Conexión a la base de datos.
	 */
	private Connection conn = null;
	
	/**
	 * Constructor por defecto que invoca a la clase padre BBDD.
	 */
	public RoomDAO() {
		super();
	}
	/**
	 * Constructor que forma el DAO a partir de unas propiedades
	 * mediante el constructor de la clase padre BBDD.
	 * 
	 * @param coldesignProperties Propiedades
	 */
	public RoomDAO(Properties coldesignProperties) {
		super(coldesignProperties);
	}
	
	/**
	 * Añade una nueva sala al sistema, y crea la realacion usuario sala
	 * para el creador de la sala, propietario de la misma.
	 * 
	 * @param room Nueva sala del sistema.
	 * @return Int que reprensenta el id de la sala creada, o -1 en
	 * caso de cualquier error a la hora de crear la sala.
	 */
	public int addRoom(Room room) {
		int idRoom = -1;
		if(!this.existRoom(room.getName(), room.getOwner())){
			logger.info("[RoomDAO-addRoom]: Adding new room [" + room.getName() + "]...");
			
			String sqlAddRoom = "insert into room (roomname, roomdescription, owner, private, participationtype, status, creationDate, modificationDate) " +
					"values (?,?,?,?,?,?,?,?)";
			try {
	
				conn = getConnection();
				PreparedStatement st = conn.prepareStatement(sqlAddRoom);
				st.setString(1, room.getName());
				st.setString(2, room.getDescription());
				st.setString(3, room.getOwner());
				st.setBoolean(4, room.getPrivateRoom());
				st.setInt(5, room.getParticipationType());
				st.setInt(6, Constants.ROOM_OPEN);
				st.setTimestamp(7, new Timestamp(new Date().getTime()));
				st.setTimestamp(8, new Timestamp(new Date().getTime()));
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
					String sqlInserUserRoom = "insert into roomuser (id_room, username, userfunction) " +
					"values (?,?,?)";
					conn = getConnection();
					PreparedStatement st1 = conn.prepareStatement(sqlInserUserRoom);
					st1.setInt(1, room.getId());
					st1.setString(2, room.getOwner());
					st1.setInt(3, Constants.OWNER_FUNCTION);
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
	
	/**
	 * Borra una sala del sistema. El borrado de la sala conlleva también
	 * el borrado de todos los elementos que hacen referencia a esta sala.
	 * El borrado se controla en base de datos de tal forma de que al borrar
	 * una sala se borra o actualiza en cascada todos aquellos elementos que
	 * tengan el id de la sala como clave foranea.
	 * 
	 * @param room Sala a borrar
	 * @return Devuelve 0 en caso de que la operación se realice con éxito
	 * y -1 en caso contrario.
	 */
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
	
	/**
	 * Verifica si para un usuario existe alguna sala suya que tenga un
	 * nombre especifico.
	 * 
	 * @param name Nombre de la sala.
	 * @param owner Nombre de usuario del propietario.
	 * @return Boolean que indica el resultado de la verificación.
	 */
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
	
	/**
	 * Funcion mediante la cual se registra a un usuario en una sala como
	 * colaborador. Esta función se usa cuando un usuario se registra directamente
	 * en una sala.
	 * 
	 * @param user Usuario que se registra en la sala.
	 * @param room Sala a la cual se registra el usuario.
	 * @return Integer que representa el resultado del registro, -1 en caso de error
	 * y 0 en caso de que todo fuese correctamente.
	 */
	public int registerUserRoom(User user, Room room) {
		return this.registerUserRoom(user.getUsername(), room, Constants.COLABORATOR_FUNCTION);
	}
	
	/**
	 * Funcion mediante la cual se registra a un usuario en una sala con una
	 * función especifica. Este método se usa cuando algún usuario del sistema
	 * es invitado a participar en una sala.
	 * 
	 * @param username Usuario que se registra en la sala.
	 * @param room Sala a la cual se registra el usuario.
	 * @param userfunction Función que el nuevo usuario registrado en la sala
	 * desempeñara en la misma.
	 * @return Integer que representa el resultado del registro, -1 en caso de error
	 * y 0 en caso de que todo fuese correctamente.
	 */
	private int registerUserRoom(String username, Room room, int userfunction) {
		int idRoom = -1;
		logger.info("[RoomDAO-addRoom]: Adding new user-room relation ...");
		String sqlInserUserRoom = "insert into roomuser (id_room, username, userfunction) " +
		"values (?,?,?)";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlInserUserRoom);
			st.setInt(1, room.getId());
			st.setString(2, username);
			st.setInt(3, userfunction);

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
	
	/**
	 * Obtiene todas las salas en las cuales esta participando un usuario dado
	 * sea cual sea la función que este desempeña dentro de la misma.
	 * 
	 * @param user Usuario del cual se quieren saber las distintas salas
	 * en las que participa.
	 * @return Listado con el conjunto de relaciones usuario-sala en las cuales
	 * esta participando el usuario que se pasa como parametro.
	 */
	public List<UserRoom> getUserRooms(User user){
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getUserRooms]: Searching rooms of user ["+user.getUsername()+"]..");
		String sqlSelectUserSala = "select id_room, username, userfunction " +
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
					newUserRoom.setUserfunction(resultados.getInt("userfunction"));
					int tipoUserFunction = resultados.getInt("userfunction");
					switch (tipoUserFunction) {
						case Constants.OWNER_FUNCTION:
							newUserRoom.setUserfunctionDescription("Owner");
							break;
						case Constants.MODERATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Moderator");
							break;
						case Constants.COLABORATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Colaborator");
							break;
						case Constants.GUEST_FUNCTION:
							newUserRoom.setUserfunctionDescription("Guest");
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
	
	/**
	 * Busca una sala del sistema a partir de su id.
	 * 
	 * @param id Id de la sala que se busca.
	 * @return Sala a la cual pertenece el id pasado como parametro.
	 */
	private Room findRoomById(int id){
		Room room = null;
		logger.info("[RoomDAO-findRoomById]: Searching room by id...");
		String sqlSelectRoom = "select id, roomname, roomdescription, owner, private, participationtype, status, creationDate, modificationDate "
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
				room.setStatus(resultados.getInt("status"));
				room.setCreationDate(resultados.getTimestamp("creationDate"));
				room.setModificationDate(resultados.getTimestamp("modificationDate"));
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
	
	/**
	 * Obtiene todas las salas que estan registradas en el sistema.
	 * 
	 * @return Lista con todas las salas del sistema.
	 */
	public List<Room> getColDesRooms() {
		List<Room> results = new ArrayList<Room>();
		logger.info("[RoomDAO-getColDesRooms]: Searching rooms of ColDes...");
		String sqlSelectUserSala = "select id, roomname, roomdescription, owner, private, participationtype, status, creationDate, modificationDate " +
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
				room.setStatus(resultados.getInt("status"));
				room.setCreationDate(resultados.getTimestamp("creationDate"));
				room.setModificationDate(resultados.getTimestamp("modificationDate"));
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
	
	/**
	 * Obtiene todas las salas del sistema que son de caracter publico.
	 * Una sala de caracter publico es visible para todos los usuarios,
	 * mientras las salas privadas son visibles unicamente para las personas
	 * que participan en dichas salas.
	 * 
	 * @return Lista de salas del sistema que son publicas.
	 */
	public List<Room> getColDesPublicRooms(User user) {
		List<Room> results = new ArrayList<Room>();
		logger.info("[RoomDAO-getColDesPublicRooms]: Searching rooms of ColDes...");
		String sqlSelectUserSala = "select id, roomname, roomdescription, owner, private, participationtype, status, creationDate, modificationDate " +
		"from room where private=0 and id not in (select id_room from roomuser where username=?)";
		try {
			conn = getConnection();
			
			PreparedStatement stUserSala = conn.prepareStatement(sqlSelectUserSala);
			stUserSala.setString(1, user.getUsername());
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
				room.setStatus(resultados.getInt("status"));
				room.setCreationDate(resultados.getTimestamp("creationDate"));
				room.setModificationDate(resultados.getTimestamp("modificationDate"));
				
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
	
	/**
	 * Metodo al cual se recurre para saber que usuarios se encuentran en el momento
	 * actual participando dentro de la sala cuando un determinado usuario entra en
	 * la sala.
	 * Adicionalmente actualiza la relación entre el usuario y la sala, marcando el flag
	 * online a 1, para indicar que el usuario esta participando en este momento en la sala.
	 * 
	 * @param user Usuario que entra en la sala.
	 * @param room Sala a la cual entra el usuario.
	 * @return Lista con los distintos nombres de usuario de los distintos usuarios que
	 * se encuentran actualmente participando dentro de la sala.
	 */
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
	
	/**
	 * Borra una relación entre un usuario y una sala.
	 * 
	 * @param userRoom Relación que se va a borrar.
	 * @return Integer que representa el resultado de la operación, 0 en caso
	 * de todo correcto, -1 en caso de error.
	 */
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
	
	/**
	 * Función mediante la cual se controla la salida de un usuario de
	 * una determinada sala.
	 * Actualiza el flag online de la relación usuario-sala a 0.
	 * 
	 * @param user Usuario que sale de la sala.
	 * @param room Sala de la cual sale el usuario.
	 * @return Integer que representa el resultado de la operación, 0 en caso
	 * de todo correcto, -1 en caso de error.
	 */
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
	
	/**
	 * Obtiene todas las relaciones usuario-sala de una determinada sala.
	 * 
	 * @param room Sala de la cual se quieren saber las relaciones.
	 * @return Listado con las distintas relaciones de la sala con los usuarios
	 * del sistema.
	 */
	public List<UserRoom> getRoomUsers(Room room) {
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getUserRooms]: Searching users of room ["+room.getId()+"]..");
		String sqlSelectUserSala = "select id_room, username, userfunction " +
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
					newUserRoom.setUserfunction(resultados.getInt("userfunction"));
					int tipoUserFunction = resultados.getInt("userfunction");
					switch (tipoUserFunction) {
						case Constants.OWNER_FUNCTION:
							newUserRoom.setUserfunctionDescription("Owner");
							break;
						case Constants.MODERATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Moderator");
							break;
						case Constants.COLABORATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Colaborator");
							break;
						case Constants.GUEST_FUNCTION:
							newUserRoom.setUserfunctionDescription("Guest");
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
	
	/**
	 * Envia una invitación a un usuario para participar en una determinada sala con
	 * una función establecida.
	 * 
	 * @param username Nombre de usuario del usuario al que se quiere invitar.
	 * @param room Sala a la cual se quiere invitar al usuario.
	 * @param userfunction Función que desempeñara el usuario dentro de la sala.
	 * @return Relación usuario-sala o null en caso de error.
	 */
	public UserRoom sendRoomInvitation(String username, Room room, int userfunction) {
		UserRoom result = null;
		logger.info("[RoomDAO-sendRoomInvitation]: New room invitation...");
		String sqlInserRoomInvitation = "insert into roomuserinvitation (username, id_room, userfunction) " +
		"values (?,?,?)";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlInserRoomInvitation);
			st.setString(1, username);
			st.setInt(2, room.getId());
			st.setInt(3, userfunction);

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-sendRoomInvitation]: The invitation can't be create");
			}else{
				result = new UserRoom();
				result.setRoom(room);
				result.setRoomName(room.getName());
				result.setOwnerUserName(room.getOwner());
				result.setUserName(username);
				result.setUserfunction(userfunction);
				switch (userfunction) {
					case Constants.OWNER_FUNCTION:
						result.setUserfunctionDescription("Owner");
						break;
					case Constants.MODERATOR_FUNCTION:
						result.setUserfunctionDescription("Moderator");
						break;
					case Constants.COLABORATOR_FUNCTION:
						result.setUserfunctionDescription("Colaborator");
						break;
					case Constants.GUEST_FUNCTION:
						result.setUserfunctionDescription("Guest");
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
	
	/**
	 * Borra una invitación de un usuario.
	 * 
	 * @param userRoom Relacion usuario-sala asociada a la invitación del usuario
	 * que desea borrar.
	 * @return Integer que representa el resultado de la operación, 0 en caso
	 * de todo correcto, -1 en caso de error.
	 */
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
	
	/**
	 * Obtiene todas las invitaciones de un usuario determinado.
	 * 
	 * @param username Nombre de usuario del usuario del cual se quieren obtener
	 * las invitaciones.
	 * @return Listado con las distintas relaciones usuario-sala a las cuales hacen
	 * referencia las distintas invitaciones de un usuario.
	 */
	public List<UserRoom> getAllUserRoomInvitation(String username) {
		List<UserRoom> results = new ArrayList<UserRoom>();
		logger.info("[RoomDAO-getAllUserRoomInvitation]: Searching invitations of user ["+username+"]..");
		String sqlSelectUserInvitation = "select username, id_room, userfunction " +
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
					newUserRoom.setUserfunction(resultados.getInt("userfunction"));
					int tipoUserFunction = resultados.getInt("userfunction");
					switch (tipoUserFunction) {
						case Constants.OWNER_FUNCTION:
							newUserRoom.setUserfunctionDescription("Owner");
							break;
						case Constants.MODERATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Moderator");
							break;
						case Constants.COLABORATOR_FUNCTION:
							newUserRoom.setUserfunctionDescription("Colaborator");
							break;
						case Constants.GUEST_FUNCTION:
							newUserRoom.setUserfunctionDescription("Guest");
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
	
	/**
	 * Función mediante la cual se gestionan las distintas acciones que un usuario
	 * puede realizar con las invitaciones que recibe.
	 * Primero borra la invitación que se esta manejando, y posteriormente, si el 
	 * usuario ha aceptado la invitación, se realiza el registro del usuario a la
	 * sala.
	 * 
	 * @param userRoom Relación usuario-sala a la que hace referencia la invitación.
	 * @param insert Indica si el usuario ha aceptado o no la invitación.
	 * @return Integer que representa el resultado de la operación, 0 en caso
	 * de todo correcto, -1 en caso de error.
	 */
	public int manageUserRoomRelation(UserRoom userRoom, boolean insert) {
		logger.info("[RoomDAO-manageUserRoomRelation]: Deleting room invitation...");
		if(this.removeUserInvitation(userRoom) < 0){
			logger.error("[RoomDAO-manageUserRoomRelation]: Error in room invitation delete...");
			return -1;
		}else{
			if(insert){
				logger.error("[RoomDAO-manageUserRoomRelation]: Insert new roomuser relation...");
				return this.registerUserRoom(userRoom.getUserName(), userRoom.getRoom(), userRoom.getUserfunction());
			}else{
				return 0;
			}
		}
	}
	
	/**
	 * Acutaliza los datos de una determinada sala.
	 * 
	 * @param room Sala que se actualiza con los cambios de la misma.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean updateRoom(Room room) {
		logger.info("[RoomDAO-updateRoom]: Update room ...");
		String sqlDeleteUserRoom = "update room set roomname=?, roomdescription=?, owner=?, private=?, status=?, participationtype=?, modificationDate=? where id=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUserRoom);
			st.setString(1, room.getName());
			st.setString(2, room.getDescription());
			st.setString(3, room.getOwner());
			st.setBoolean(4, room.getPrivateRoom());
			st.setInt(5, room.getStatus());
			st.setInt(6, room.getParticipationType());
			st.setTimestamp(7, new Timestamp(new Date().getTime()));
			st.setInt(8, room.getId());
			
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
	
	/**
	 * Actualiza una relación usuario-sala determinada.
	 * 
	 * @param userRoom Relación usuario-sala con las nuevas
	 * modifaciones.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean updateUserRoom(UserRoom userRoom){
		logger.info("[RoomDAO-updateUserRoom]: Update room-user ...");
		String sqlDeleteUserRoom = "update roomuser set userfunction=? where id_room=? and username=?";
		try {
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlDeleteUserRoom);
			st.setInt(1, userRoom.getUserfunction());
			st.setInt(2, userRoom.getRoom().getId());
			st.setString(3, userRoom.getUserName());

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-updateUserRoom]: The room-user can't be updated");
				return false;
			}else{
				return true;
			}
						
		} catch (SQLException e) {
			logger.error("[RoomDAO-updateUserRoom]: Error in SQL sentence: " + e.getLocalizedMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-updateUserRoom]: Closing DB connection");
					conn.close();
					
				} catch (SQLException e) {
					logger.error("[RoomDAO-updateUserRoom]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}

	}
	
	/**
	 * Cambia el propietario de una determinada sala. Para ello actualiza la función que 
	 * desempeñaba el nuevo propietario a propietario y cambia la funcion del propietario
	 * a moderador.
	 * 
	 * @param idRoom Id de la sala que cambia de dueño.
	 * @param newOwner Nombre de usuario del nuevo propietario de la sala.
	 * @param oldOwner Nombre de usuario del viejo propietario de la sala.
	 * @return Boolean que indica el resultado de la operación.
	 */
	public boolean changeUserOwner(int idRoom, String newOwner, String oldOwner) {
		logger.info("[RoomDAO-changeUserOwner]: Change user owner of user room relation ...");
		try {
			String sqlChangeOwnerRoom = "update roomuser set userfunction=? where id_room=? and username=?";
			conn = getConnection();
			PreparedStatement st = conn.prepareStatement(sqlChangeOwnerRoom);
			st.setInt(1, Constants.OWNER_FUNCTION);
			st.setInt(2, idRoom);
			st.setString(3, newOwner);

			int i = st.executeUpdate();
			if (i != 1){
				logger.info("[RoomDAO-changeUserOwner]: (new Owner) The room-user can't be updated");
				return false;
			}else{
				st = conn.prepareStatement(sqlChangeOwnerRoom);
				st.setInt(1, Constants.MODERATOR_FUNCTION);
				st.setInt(2, idRoom);
				st.setString(3, oldOwner);
				i = st.executeUpdate();
				if (i != 1){
					logger.info("[RoomDAO-changeUserOwner]: (old Owner) The room-user can't be updated");
					return false;
				}else{
					return true;
				}
			}
			
						
		} catch (SQLException e) {
			logger.error("[RoomDAO-changeUserOwner]: Error in SQL sentence: " + e.getLocalizedMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					logger.info("[RoomDAO-changeUserOwner]: Closing DB connection");
					conn.close();
					
				} catch (SQLException e) {
					logger.error("[RoomDAO-changeUserOwner]: Error closing DB connection: " + e.getLocalizedMessage());
				}
			}
		}
		
	}

}
