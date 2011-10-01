package es.uc3m.coldes.model;

import java.util.Date;

/**
 * Clase que modela a una Sala del sistema.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class Room {
	/**
	 * Id de la sala.
	 */
	private int id;
	/**
	 * Nombre de la sala.
	 */
	private String name;
	/**
	 * Descripción de la sala.
	 */
	private String description;
	/**
	 * Nombre de usuario del propietario de la sala.
	 */
	private String owner;
	/**
	 * Boolean que indica si la sala es privada o no.
	 */
	private boolean privateRoom;
	/**
	 * Tipo de participación que soporta la sala.
	 */
	private int participationType;
	/**
	 * Estado de la sala: abierta/cerrada.
	 */
	private int status;
	/**
	 * Fecha de creación de la sala.
	 */
	private Date creationDate;
	/**
	 * Fecha de última modificación de la sala.
	 */
	private Date modificationDate;

	/**
	 * Constructor por defecto de una nueva sala.
	 */
	public Room() {
	}
	/**
	 * Constructor de sala que recibe los distintos elementos
	 * de cualquier sala del sistema.
	 * 
	 * @param id Id de la sala.
	 * @param name Nombre de la sala.
	 * @param description Descripción de la sala.
	 * @param owner Nombre de usuario del propietario.
	 * @param privateRoom Sala privada o pública.
	 * @param participationType Tipo de participación dentro
	 * de la sala.
	 * @param status Estado de la sala:abierta/cerrada.
	 * @param creationDate Fecha de creación de la sala.
	 * @param modificationDate Fecha de última modificación de la sala.
	 */
	public Room(int id, String name, String description, String owner, boolean privateRoom, int participationType, 
			int status, Date creationDate, Date modificationDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.owner = owner;
		this.privateRoom = privateRoom;
		this.participationType = participationType;
		this.status = status;
		this.creationDate = creationDate;
		this.modificationDate = modificationDate;
	}

	/**
	 * Obtiene el id de la sala.
	 * 
	 * @return Id de la sala.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Asigna un id a la sala.
	 * @param id Int que representa el id nuevo de la sala.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Obtiene el nombre de la sala.
	 * @return Nombre de la sala.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Asigna un nombre a la sala.
	 * 
	 * @param name Nombre de la sala.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Obtiene la descripción de la sala.
	 * 
	 * @return Descripción de la sala.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Asigna una descripción a la sala.
	 * 
	 * @param description Texto que describe la sala.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Obtiene el nombre de usuario del propietario 
	 * de la sala.
	 * 
	 * @return Nombre de usuario del propietario.
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * Asigna el nombre de usuario del propietario.
	 * @param owner Nombre de usuario del propietario.
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * Obtiene si la sala es privada o no.
	 * 
	 * @return true en caso de ser privada, false en caso
	 * contrario.
	 */
	public boolean getPrivateRoom() {
		return privateRoom;
	}
	/**
	 * Define la visibilidad de la sala: privada o publica.
	 * 
	 * @param privateRoom Boolean que indica si la sala es
	 * privada o no.
	 */
	public void setPrivateRoom(boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	/**
	 * Obtiene el tipo de participación de la sala.
	 * 
	 * @return Entero que indica el tipo de participación.
	 */
	public int getParticipationType() {
		return participationType;
	}
	/**
	 * Asigna el tipo de participación de la sala.
	 * 
	 * @param participationType Tipo de participación.
	 */
	public void setParticipationType(int participationType) {
		this.participationType = participationType;
	}

	/**
	 * Obtiene el estado de la sala.
	 * 
	 * @return Estado de la sala.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * Asigna el estado de la sala.
	 * 
	 * @param status Estado de la sala.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Obtiene la fecha de creación de la sala.
	 * 
	 * @return Fecha de creación de la sala.
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * Asigna la fecha de creación de la sala.
	 * 
	 * @param creationDate Fecha de creación de la sala.
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Obtiene la última fecha de modificación de la sala.
	 * 
	 * @return Fecha de modificación.
	 */
	public Date getModificationDate() {
		return modificationDate;
	}
	/**
	 * Asigna la fecha de última modifación de la sala.
	 * 
	 * @param modificationDate Fecha de modificación.
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
}
