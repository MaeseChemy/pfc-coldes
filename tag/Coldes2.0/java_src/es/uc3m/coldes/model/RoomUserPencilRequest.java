package es.uc3m.coldes.model;

import java.util.Date;

/**
 * Clase que modela a una petición de pincel
 * dentro de una sala del sistema.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class RoomUserPencilRequest {
	/**
	 * Id de la sala donde se realiza la petición del pincel.
	 */
	private int idRoom;
	/**
	 * Nombre de usuario del usuario que hace la petición.
	 */
	private String username;
	/**
	 * Función que desempeña dentro de la sala el usuario que
	 * realiza la petición.
	 */
	private int userfunction;
	/**
	 * Momento en el que se realiza la petición.
	 */
	private Date requestTime;
	/**
	 * Boolean que indica si esta petición es la que obtubo
	 * el pincel.
	 */
	private boolean pencilowner;
	
	/**
	 * Constructor por defecto de una petición de pincel.
	 */
	public RoomUserPencilRequest(){
		super();
	}
	
	/**
	 * Constructor que recibe los distintos elementos necesarios para
	 * modelar una petición de pincel dentro de una sala.
	 * 
	 * @param idRoom Id de la sala donde se recibe la petición.
	 * @param username Nombre de usuario del usuario que realiza la
	 * petición.
	 * @param userfunction Función que desempeña dentro de la sala el usuario que
	 * realiza la petición.
	 * @param requestTime Momento en el que se realiza la petición.
	 * @param pencilowner Boolean que indica si esta petición es la que obtubo
	 * el pincel.
	 */
	public RoomUserPencilRequest(int idRoom, String username, int userfunction,
			Date requestTime, boolean pencilowner) {
		super();
		this.idRoom = idRoom;
		this.username = username;
		this.userfunction = userfunction;
		this.requestTime = requestTime;
		this.pencilowner = pencilowner;
	}

	/**
	 * Obtiene el id de la sala donde se realiza la petición.
	 * 
	 * @return Id de la sala.
	 */
	public int getIdRoom() {
		return idRoom;
	}
	/**
	 * Asigna el id de la sala donde se realiza la petición.
	 * 
	 * @param idRoom Id de la sala.
	 */
	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}
	
	/**
	 * Obtiene el nombre de usuario del usuario que realizó
	 * la petición del pincel en la sala.
	 * 
	 * @return Nombre de usuario del usuario que hizo la 
	 * petición.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Asigna el nombre de usuario del usuario que realizo la
	 * petición de pincel dentro de la sala.
	 * 
	 * @param username Nombre de usuario.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Obtiene la función que desempeña el usuario que realizó
	 * la petición de pincel dentro de la sala.
	 * 
	 * @return Integer que indica la función del usuario.
	 */
	public int getUserfunction() {
		return userfunction;
	}
	/**
	 * Asigna la función que desempeña el usuario que realizó
	 * la petición de pincel dentro de la sala.
	 * 
	 * @param userfunction Integer que indica la función del usuario.
	 */
	public void setUserfunction(int userfunction) {
		this.userfunction = userfunction;
	}
	
	/**
	 * Obtiene el momento de tiempo en el que el usuario realizo la
	 * petición del pincel.
	 * 
	 * @return Fecha de petición.
	 */
	public Date getRequestTime() {
		return requestTime;
	}
	/**
	 * Obtiene el momento de tiempo en el que el usuario realizo la
	 * petición del pincel.
	 * 
	 * @param requestTime Fecha de petición.
	 */
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	/**
	 * Indica si esta petición ha obtenido el pincel de la sala.
	 * 
	 * @return Boolean que indica si la petición es dueña
	 * del pincel.
	 */
	public boolean isPencilowner() {
		return pencilowner;
	}
	/**
	 * Asigna a esta petición si es o no dueña del pincel.
	 * 
	 * @return Boolean que indica si la petición es dueña
	 * del pincel.
	 */
	public void setPencilowner(boolean pencilowner) {
		this.pencilowner = pencilowner;
	}
	
}
