package es.uc3m.coldes.model;

import java.util.Date;

public class Room {
	private int id;
	private String name;
	private String description;
	private String owner;
	private boolean privateRoom;
	private int participationType;
	private int status;
	private Date creationDate;
	private Date modificationDate;

	
	public Room() {
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public boolean getPrivateRoom() {
		return privateRoom;
	}

	public void setPrivateRoom(boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	public int getParticipationType() {
		return participationType;
	}

	public void setParticipationType(int participationType) {
		this.participationType = participationType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	
}
