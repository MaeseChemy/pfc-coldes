package es.uc3m.coldes.control.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import es.uc3m.coldes.control.client.InfoDesignServiceImpl;
import es.uc3m.coldes.control.client.InfoPencilServiceImpl;
import es.uc3m.coldes.control.client.InfoRoomServiceImpl;
import es.uc3m.coldes.control.client.InfoUserServiceImpl;
import es.uc3m.coldes.exceptions.SessionTimeoutException;
import es.uc3m.coldes.model.Design;
import es.uc3m.coldes.model.Room;
import es.uc3m.coldes.model.User;
import es.uc3m.coldes.model.UserRoom;
import flex.messaging.MessageBroker;
import flex.messaging.MessageDestination;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.services.MessageService;
import flex.messaging.util.UUIDUtils;

/**
 * Clase que ofrece todos los servicios relacionados con los distintos elementos
 * de la aplicación: Usuarios, Salas, Peticiones de pinceles y Diseños.
 * 
 * @author Jose Miguel Blanco García
 *
 */
public class ColDesService implements Serializable{
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(ColDesService.class.getName());
	
	/**
	 * Variable que gestiona las sesiones del sistema.
	 */
	private ColDesSession session;
	
	//SERVICIOS
	/**
	 * Service mediante el cual se permite invocar todas las funcionalidades
	 * relacionadas con los usuarios.
	 */
	private InfoUserService userService;
	
	/**
	 * Service mediante el cual se permite invocar todas las funcionalidades
	 * relacionadas con las salas.
	 */
	private InfoRoomService roomService;
	
	/**
	 * Service mediante el cual se permite invocar todas las funcionalidades 
	 * relacionadas con las peticiones de pincel.
	 */
	private InfoPencilService pencilService;
	
	/**
	 * Service mediante el cual se permite invocar todas las funcionalidades
	 * relacionadas con los diseños del sistema, tanto de usuarios como de
	 * salas.
	 */
	private InfoDesignService designService;
	
	/**
	 * Constructor por defecto encargado de inicializar la sesion y los
	 * distintos service del sistema.
	 */
	public ColDesService(){
		this.session = new ColDesSession();
		this.userService = new InfoUserServiceImpl();
		this.roomService = new InfoRoomServiceImpl();
		this.pencilService = new InfoPencilServiceImpl();
		this.designService = new InfoDesignServiceImpl();
	}
	
	/**
	 * Función encargada de finalizar la sesión del usuario.
	 */
	public void finalize() {
		if (this.session.isValid()) {
			this.session.invalidate();
		}

		this.session.finalize();
		this.userService.getUserDAO().finalize();
		try {
			super.finalize();
		} catch (Throwable e) {
			logger.error("Error in finalize: "+ e.getLocalizedMessage());
		}
	}
	
	/**
	 * Función que verifica si el usuario aún tiene una sesión valida dentro
	 * del sistema.
	 * 
	 * @return Boolean que indica si la sesión es correcta.
	 * @throws SessionTimeoutException
	 */
	private boolean checkIsLogIn() throws SessionTimeoutException {
		if (!this.session.isValid()) {
			logger.debug("The session is over");
			throw new SessionTimeoutException("Session timeout");
		}

		return true;
	}
	
	/**************************************************************************************/
	/**                             SERVICIOS DE USUARIOS                                 */
	/**************************************************************************************/

	/**
	 * Función encargada de realizar la autenticación del usuario dentro del sistema. 
	 * Para ello recurre al service de usuarios.
	 * 
	 * @param user Nombre del usuario que intenta acceder al sistema.
	 * @param password Password del usuario que intenta acceder al sistema.
	 * @return Devuelve el User del usuario que se ha autenticado en caso correcto,
	 * y null en caso incorrecto.
	 */
	public User doLogin(String user, String password){
		User loginUser = this.userService.doLogin(user, password);
		if (loginUser != null) {
			this.session.validate(loginUser);
			logger.info("Session init correct: " + user);
			return loginUser;
		} else {
			logger.info("Session init incorrect: " + user);
			return null;
		}
	}

	/**
	 * Función encargada de finalizar la sesión del usuario autenticado en el
	 * sistema.
	 * Para ello invalida la Sessión, y usa el Service de usuarios para finalizar 
	 * las acciones pendientes del usuario en el sistema.
	 * 
	 * @param user User del usuario que sale del sistema.
	 * @return Booleano que indica el resultado del logout, true si todo va bien
	 * false en caso de error.
	 */
	public Boolean doLogout(User user){
		this.session.invalidate();
		return this.userService.doLogout();
	}
	
	/**
	 * Añade un nuevo usuario al sistema mediante el Service de usuarios.
	 * 
	 * @param user Nuevo usuario.
	 * @return Nombre del usuario añadido en caso de que todo vaya correcto,
	 * null en caso contrario.
	 */
	public String addUser(User user){
		return this.userService.addUser(user);
	}
	
	/**
	 * Actualiza los datos de un usuario mediante el Service de usuarios.
	 * 
	 * @param user User con las modificaciones que se quieren realizar.
	 * @param passChange Booleano que indica si la password del usuario ha
	 * sido modificada.
	 * @return Boolean que indica el resultado de la actualización, true si todo
	 * va bien false en caso contrario.
	 * 
	 * @throws SessionTimeoutException
	 */
	public Boolean updateUser(User user, boolean passChange) throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.updateUser(user, passChange);
	}
	
	/**
	 * Función que obtiene todos los usuarios del sistema mediante el Service
	 * de usuarios.
	 * 
	 * @return Lista con todos los usuarios del sistema.
	 * @throws SessionTimeoutException
	 */
	public List<User> getAllUsers() throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.getAllUsers();
	}
	
	/**
	 * Función que devuelve todos los usuarios que estan en una determinada sala
	 * en el momento actual.
	 * 
	 * @param room Sala de la cual se quieren obtener los usuarios conectados.
	 * @return Lista con el nombre de los usuarios conectados en el momento actual
	 * a la sala.
	 * @throws SessionTimeoutException
	 */
	public List<String> getColDesUsers(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.userService.getColDesUsers(room);
	}
	
	/**************************************************************************************/
	/**                             SERVICIOS DE SALAS                                    */
	/**************************************************************************************/
	
	/**
	 * Añade una nueva sala al sistema mediante el Service de salas.
	 *
	 * @param room Sala creada por el usuario.
	 * @return Id que hace relación a la nueva sala insertada en el
	 * sistema.
	 * @throws SessionTimeoutException
	 */
	public int addRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.addRoom(room);
	}
	
	/**
	 * Actualiza los datos referentes a una sala mediante el Service de
	 * salas.
	 * 
	 * @param room Sala de la cual se quieren actualizar los datos.
	 * @return Boolean que indica el resultado de la función, true en caso
	 * correcto false en caso contrario.
	 * @throws SessionTimeoutException
	 */
	public boolean updateRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.updateRoom(room);
	}
	
	/**
	 * Obtiene la lista de salas en las cuales esta participando un usuario, así como
	 * la función que desempeña en cada una de las salas.
	 * 
	 * @param user User del cual se quieren obtener salas y funciones.
	 * @return Lista con las distintas salas del sistema en las cuales el usuario
	 * se encuentra participando junto con la funcion que desempeña en cada una
	 * de ellas.
	 * @throws SessionTimeoutException
	 */
	public List<UserRoom> getUserRooms(User user) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getUserRooms(user);
	}
	
	/**
	 * Función encargada de gestionar las relaciones de los usuarios con las distintas
	 * salas del sistema mediante el Service de salas.
	 * 
	 * @param userRoom Nueva relación usuario sala en la que se indica ademas la función
	 * del usuario en la sala.
	 * @param insert Boolean que indica si es una inserción o una actualización.
	 * @return Int que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public int manageUserRoomRelation(UserRoom userRoom, boolean insert)throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.manageUserRoomRelation(userRoom, insert);
	}
	
	/**
	 * Obtiene las distintas salas del sistema sin excepción mediante el Service de
	 * salas.
	 * 
	 * @return Lista que contiene todas las salas del sistema.
	 * @throws SessionTimeoutException
	 */
	public List<Room> getColDesRooms() throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getColDesRooms();
	}
	
	/**
	 * Obtiene las salas publicas del sistema mediante el Service de salas.
	 * 
	 * @return Lista que contiene las salas publicas de ColDes.
	 * @throws SessionTimeoutException
	 */
	public List<Room> getColDesPublicRooms() throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getColDesPublicRooms();
	}
	
	/**
	 * Asocia un usuario a una sala mediante el Service de salas.
	 * 
	 * @param user User que se asocia a la sala.
	 * @param room Sala a la cual se asocia el usuario.
	 * @return Int que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public int registerUserRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.registerUserRoom(user, room);
	}
	
	/**
	 * Borra un usuario de una sala mediante el Service de salas.
	 * 
	 * @param userRoom Relación entre usuario y sala.
	 * @return Int que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public int deleteUserRoom(UserRoom userRoom) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.deleteUserRoom(userRoom);
	}
	
	/**
	 * Actualiza una realacion usuario sala mediante el Service de salas.
	 * 
	 * @param userRoom Relación entre usuario y sala.
	 * @return Boolean que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public boolean updateUserRoom(UserRoom userRoom) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.updateUserRoom(userRoom);
	}
	
	/**
	 * Función mediante la cual se notifica al sistema la entrada de un usuario a una
	 * sala mediante el Service de salas.
	 * 
	 * @param user User que entra en la sala.
	 * @param room Sala a la que entra el usuario.
	 * @return Lista con el nombre de usuario de los usuarios ya conectados en la sala.
	 * @throws SessionTimeoutException
	 */
	public List<String> enterInRoom(User user, Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.enterInRoom(user,room);
	}
	
	/**
	 * Función que notifica al sistema cuando un usuario sale de la sala mediante el Service
	 * de salas.
	 * 
	 * @param user User que sale de la sala.
	 * @param room Sala de la que sale el usuario.
	 * @param totalLogout Indica si el logout es unicamente de la sala o también del sistema.
	 * @return Int que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public int roomLogout(User user, Room room, boolean totalLogout) throws SessionTimeoutException{
		if(!totalLogout)
			checkIsLogIn();
		boolean oldOwner = this.pencilService.isUserPencilOwner(room, user.getUsername());
		int result = this.roomService.roomLogout(user,room);
		if(result >= 0){
			
			//Actualizamos las peticiones de pinceles del usuario en la sala
			String nextUser = this.pencilService.deleteUserPencilRequestRoom(user.getUsername(), room);
			//Notificamos la salida de la sala por parte del usuario al resto de usuarios de la sala
			//y también notificamos el nuevo propietario del pincel de la sala.
			if(oldOwner){
				this.notifyUserToRoom(user.getUsername(), room, "exit", nextUser);
			}else{
				this.notifyUserToRoom(user.getUsername(), room, "exit", null);
			}
			
		}
		return result;
	}
	
	/**
	 * Obtiene las distintas relaciones que tiene una sala con los usuarios del sistema
	 * mediante el Service de salas.
	 * 
	 * @param room Sala de la cual se quiere saber sus relaciones con los usuarios.
	 * @return Lista con las distintas relaciones de la sala.
	 * @throws SessionTimeoutException
	 */
	public List<UserRoom> getRoomUsers(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getRoomUsers(room);
	}
	
	/**
	 * Función mediante la cual se le envia una invitación a sala mediante el Service 
	 * de salas.
	 * 
	 * @param username Nombre de usuario destinatario de la invitación.
	 * @param room Sala a la cual se desea invitar al usuario.
	 * @param userfunction Función que desempeñara el usuario en la sala.
	 * @return Int que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public int sendRoomInvitation(String username, Room room, int userfunction) throws SessionTimeoutException{
		checkIsLogIn();
		UserRoom userroom = this.roomService.sendRoomInvitation(username, room, userfunction);
		if(userroom != null){
			notifyInvitationToUser(userroom);
			return 0;
		}
		return -1;
	}
	
	/**
	 * Función mediante la cual se obtienen todas las invitaciones que hay en el sistema
	 * de un usuario mediante el Service de salas.
	 * 
	 * @param username Nombre de usuario del cual se quieren obtener las invitaciones.
	 * @return Lista que contiene las relaciones futuras con las salas a las cual el
	 * usuario ha sido invitado.
	 * @throws SessionTimeoutException
	 */
	public List<UserRoom> getAllUserRoomInvitation(String username) throws SessionTimeoutException{
		checkIsLogIn();
		return this.roomService.getAllUserRoomInvitation(username);
	}
	
	/**************************************************************************************/
	/**                             SERVICIOS DE PINCEL                                   */
	/**************************************************************************************/
	
	/**
	 * Función que verifica si el pincel esta siendo usado en una sala mediante el Service de
	 * pinceles.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 * @throws SessionTimeoutException
	 */
	public boolean pencilBusy(Room room, String username, int userfunction)throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.pencilService.pencilBusy(room, username, userfunction);
		if(!result){
			this.notifyUserToRoom(username,room,"pencilRequest",username);
		}
		return result;
	}
	
	/**
	 * Función mediante la cual un usuario realiza una petición para poder usar el pincel mediante 
	 * el Service de pinceles.
	 * Adicionalmente se manda un mensaje a todos los usuarios de la sala para notificar la petición
	 * del pincel.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return Boolean que indica si el pincel esta siendo usado o no.
	 * @throws SessionTimeoutException
	 */
	public boolean addPencilRequest(Room room, String username, int userfunction)throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.pencilService.addPencilRequest(room, username, userfunction);
		if(result){
			if(this.pencilService.isUserPencilOwner(room, username)){
				this.notifyUserToRoom(username,room,"pencilRequest",username);
			}else{
				this.notifyUserToRoom(username,room,"pencilRequest",null);
			}
		}
		return result;
	}
	
	/**
	 * Función mediante la cual se elimina una petición de un pincel de una sala mediante
	 * el Service de pinceles.
	 * 
	 * @param room Sala a la que pertenece el pincel.
	 * @param username Nombre de usuario del User que desea saber si el pincel esta ocupado.
	 * @param userfunction Función que desempeña el usuario en la sala.
	 * @return String que contiene el nombre de ususario del siguiente propietario del pincel,
	 * o null en caso de que no haya más peticiones.
	 * @throws SessionTimeoutException
	 */
	public String removePencilRequest(Room room, String username, int userfunction)throws SessionTimeoutException{
		checkIsLogIn();
		String nextUser = this.pencilService.deleteUserPencilRequestRoom(username, room);
		this.notifyUserToRoom(username, room, "pencilLeft", nextUser);
		return nextUser;
	}
	
	/**************************************************************************************/
	/**                             SERVICIOS DE DISEÑOS                                  */
	/**************************************************************************************/
	/**
	 * Función encargada de asociar un diseño a una sala mediante el Service de
	 * diseños.
	 * 
	 * @param room Sala a la cual se quiere asociar el diseño.
	 * @param content Contenido del lienzo en forma de array de bytes que
	 * representa el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public boolean saveDesignToCanvas(Room room, byte[] content) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.saveDesignToCanvas(room, content);
		return result;
	}
	
	/**
	 * Obtiene el diseño asociado a una sala mediante el Service de diseños.
	 * 
	 * @param room Sala de la cual se quiere obtener el diseño asociado.
	 * @return Array de bytes que representa el diseño asociado a la sala,
	 * o null en caso de no tener ningún diseño asociado.
	 * @throws SessionTimeoutException
	 */
	public byte[] getRoomSaveContent(Room room)throws SessionTimeoutException{
		checkIsLogIn();
		byte[] designContent = this.designService.getRoomSaveContent(room);
		return designContent;
	}
	
	/**
	 * Función encargada de borrar un diseño de una sala mediante el Service de
	 * diseños.
	 * 
	 * @param room Sala a la cual se quiere borrar el diseño.
	 * @return Boolean que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public boolean removeDesingRoom(Room room) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.removeDesingRoom(room);
		return result;
	}
	
	/**
	 * Guarda para un usuario un diseño mediante el Service de diseños.
	 * 
	 * @param design Diseño que se desea guardar.
	 * @return Boolean que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public boolean saveDesignToUser(Design design) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.saveDesignToUser(design);
		return result;
	}
	
	/**
	 * Funcion que devuelve los distintos diseños asociados a un usuario mediante
	 * el Service de diseños.
	 * 
	 * @param user User del cual se quieren obtener los diseños.
	 * @return Lista con los diseños que el usuario se ha asociado.
	 * @throws SessionTimeoutException 
	 */
	public List<Design> getUserDesigns(User user) throws SessionTimeoutException{
		checkIsLogIn();
		List<Design> userDesigns = this.designService.getUserDesigns(user);
		return userDesigns;
	}
	
	/**
	 * Borra un diseño asociado a un usuario mediante el Service de diseños.
	 * 
	 * @param design Diseño que se desea borrar.
	 * @return Boolean que indica el resultado de la operación.
	 * @throws SessionTimeoutException
	 */
	public boolean removeUserDesign(Design design) throws SessionTimeoutException{
		checkIsLogIn();
		boolean result = this.designService.removeUserDesign(design);
		return result;
	}
	
	/**************************************************************************************/
	/**                             GESTIÓN DE CANALES                                    */
	/**************************************************************************************/
	/**
	 * Crea canales que usara la aplicación para sincronizar a los distintos usuarios mediante
	 * los chats y lienzos de la sala.
	 * @deprecated
	 * 
	 * @param destinationStringValue Nombre del destino a crear.
	 * @return String que contiene el nombre del destino.
	 * @throws SessionTimeoutExceqtion
	 */
	public String createDestination(String destinationStringValue) throws SessionTimeoutException{
		checkIsLogIn();
		logger.info("[ColDesManager-createChatDestination]: Creating new channel ["+destinationStringValue+"]");
		// Create a new Message desination dynamically 
		
		MessageBroker broker = MessageBroker.getMessageBroker(null);
		MessageService service = (MessageService) broker.getService("message-service");
		MessageDestination destination;
		if(service.getDestination(destinationStringValue) == null){
			logger.info("[ColDesManager-createChatDestination]: El canal no ha sido creado, lo creamos...");
			destination = (MessageDestination) service.createDestination(destinationStringValue);
		}else{
			logger.info("[ColDesManager-createChatDestination]: El canal ya se habia creado, lo obtenemos...");
			destination = (MessageDestination) service.getDestination(destinationStringValue);
		}
		if (service.isStarted()) {
			logger.info("[ColDesManager-createChatDestination]: El servicio esta inicializado...");
		    destination.start();
		}else{
			destination.stop();
			destination.start();
			logger.info("[ColDesManager-createChatDestination]: El servicio no esta inicializado...");
		}

		return destinationStringValue;
	}
	
	/**
	 * Función mediante la cual se notifica de distintas acciones en una sala al resto de usuarios
	 * de la sala en cuestión.
	 * 
	 * @param username Nombre de usuario del User que realiza la acción.
	 * @param room Sala en la que el usuario realiza la acción.
	 * @param action Acción que realiza el usuario.
	 * @param nextPencilOwner Siguente dueño del pincel si fuese necesario.
	 * @throws SessionTimeoutException
	 */
	public void notifyUserToRoom(String username, Room room, String action, String nextPencilOwner) throws SessionTimeoutException{
		logger.info("[ColDesManager-enterInRoom]: User " +action+ " the room...");
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
        String clientID = UUIDUtils.createUUID(false);
        AsyncMessage msg = new AsyncMessage();
        msg.setDestination("updateUsersRooms");
        msg.setClientId(clientID);
        msg.setMessageId(UUIDUtils.createUUID(false));
        msg.setTimestamp(System.currentTimeMillis());
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("user", username);
        body.put("room", room);
        body.put("action", action);
        
        //Next pencil Owner
        body.put("nextPencilOwner", nextPencilOwner);
        msg.setBody(body);
        logger.info("[ColDesManager-enterInRoom]: Sending message" + body);
        msgBroker.routeMessageToService(msg, null);
		logger.info("[ColDesManager-enterInRoom]: Sended");
	}
	
	/**
	 * Función mediante la cual se notifica al usuario en tiempo real de la invitación
	 * a una sala.
	 * 
	 * @param userroom Relación usuario sala a la que hace referencia la 
	 * invitación.
	 * @throws SessionTimeoutException
	 */
	public void notifyInvitationToUser(UserRoom userroom) throws SessionTimeoutException{
		logger.info("[ColDesManager-enterInRoom]: Send invitation to user " +userroom.getUserName()+ " of room ["+userroom.getRoom().getId()+"]...");
		MessageBroker msgBroker = MessageBroker.getMessageBroker(null);
        String clientID = UUIDUtils.createUUID(false);
        AsyncMessage msg = new AsyncMessage();
        msg.setDestination("message");
        msg.setClientId(clientID);
        msg.setMessageId(UUIDUtils.createUUID(false));
        msg.setTimestamp(System.currentTimeMillis());
        HashMap<String, Object> body = new HashMap<String, Object>();
        body.put("username", userroom.getUserName());
        body.put("userroom", userroom);

        msg.setBody(body);
        logger.info("[ColDesManager-enterInRoom]: Sending message" + body);
        msgBroker.routeMessageToService(msg, null);
		logger.info("[ColDesManager-enterInRoom]: Sended");
	}
	
	/**************************************************************************************/
	/**                             FUNCIONES AUXILIARES                                  */
	/**************************************************************************************/
	/**
	 * Función encargada de simular el play de acciones de la sala.
	 * 
	 * @param velocidad Velocidad de reproducción.
	 * @param numFrame Siguente posición de imagen a mostrar.
	 * @return Int que representa la posición de la siguiente imagen a mostrar.
	 * @throws InterruptedException
	 */
	public int playProcess(int velocidad, int numFrame) throws InterruptedException{
		try {
			Thread.currentThread();
			Thread.sleep(velocidad);
		} catch (InterruptedException e) {
			logger.error("Se produjo un error la intentar dormir el proceso");
		}
		return numFrame;
	}
}
