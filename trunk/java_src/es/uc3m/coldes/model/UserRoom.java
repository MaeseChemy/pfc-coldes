package es.uc3m.coldes.model;

/**
 * Clase que modela a un relación usuario-sala.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class UserRoom {
	/**
	 * Sala de la relación.
	 */
	private Room room;
	/**
	 * Nombre de la sala.
	 */
	private String roomName;
	/**
	 * Nombre del propietario de la sala.
	 */
	private String ownerUserName;
	/**
	 * Nombre de usuario de la relación.
	 */
	private String userName;
	/**
	 * Función del usuario dentro de la sala.
	 */
	private int userfunction;
	/**
	 * Descripción de la función.
	 */
	private String userfunctionDescription;
	
	/**
	 * Constructor por defecto de una nueva relación sala-usuario.
	 */
	public UserRoom() {
	}
	
	/**
	 * Constructor que recibe los distitos elementos de una relación sala-usuario.
	 * 
	 * @param room Sala de la relación.
	 * @param roomName Nombre de la sala.
	 * @param ownerUserName Propietario de la sala.
	 * @param userName Nombre de usuario del usuario de la relación.
	 * @param userfunction Función del usuario dentro de la sala.
	 * @param userfunctionDescription Descripción de la función del usuario.
	 */
	public UserRoom(Room room, String roomName, String ownerUserName,
			String userName, int userfunction, String userfunctionDescription) {
		super();
		this.room = room;
		this.roomName = roomName;
		this.ownerUserName = ownerUserName;
		this.userName = userName;
		this.userfunction = userfunction;
		this.userfunctionDescription = userfunctionDescription;
	}
	
	/**
	 * Obtiene la sala de la relación.
	 * 
	 * @return Sala de la relación.
	 */
	public Room getRoom() {
		return room;
	}
	/**
	 * Asigna el valor de la sala de la relación.
	 * @param room Sala asociada a la relación.
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Nombre de la sala.
	 * 
	 * @return Nombre de la sala.
	 */
	public String getRoomName() {
		return roomName;
	}
	/**
	 * Asigna el nombre de la sala de la relación.
	 * 
	 * @param roomName Nombre de la sala.
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	/**
	 * Obtiene el nombre de usuario del propietario de la sala.
	 * 
	 * @return Nombre de usuario del propietario de la sala.
	 */
	public String getOwnerUserName() {
		return ownerUserName;
	}
	/**
	 * Asigna el nombre de usuario del propietario de la sala.
	 * 
	 * @param ownerUserName Nombre de usuario del propietario
	 * de la sala.
	 */
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
	
	/**
	 * Obtiene el nombre de usuario del usuario de la relación.
	 * @return Nombre de usuario del usuario de la relación.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Asigna el nombre de usuario del usuario de la relación.
	 * @param userName Nombre de usuario del usuario de la relación.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Obtiene la función del usuario dentro de la sala.
	 * 
	 * @return Valor númerico que especifica la función
	 * del usuario dentro de la sala.
	 */
	public int getUserfunction() {
		return userfunction;
	}
	/**
	 * Asigna la función del usuario dentro de la sala.
	 * 
	 * @param userfunction Valor númerico que especifica la función
	 * del usuario dentro de la sala.
	 */
	public void setUserfunction(int userfunction) {
		this.userfunction = userfunction;
	}
	
	/**
	 * Obtiene la descripción de la función del usuario dentro de la sala.
	 * 
	 * @return Descripción de la función de usuario.
	 */
	public String getUserfunctionDescription() {
		return userfunctionDescription;
	}
	/**
	 * Asigna la descripción de la función del usuario dentro de la sala.
	 * 
	 * @param userfunctionDescription Descripción de la función de usuario.
	 */
	public void setUserfunctionDescription(String userfunctionDescription) {
		this.userfunctionDescription = userfunctionDescription;
	}

}
