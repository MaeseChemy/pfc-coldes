package es.uc3m.coldes.model;

public class UserRoom {

	private Room room;
	private String roomName;
	private String ownerUserName;
	private String userName;
	private int rol;
	private String rolDescription;
	
	public UserRoom() {
	}

	public UserRoom(Room room, String roomName, String ownerUserName,
			String userName, int rol, String rolDescription) {
		super();
		this.room = room;
		this.roomName = roomName;
		this.ownerUserName = ownerUserName;
		this.userName = userName;
		this.rol = rol;
		this.rolDescription = rolDescription;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getOwnerUserName() {
		return ownerUserName;
	}

	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public String getRolDescription() {
		return rolDescription;
	}

	public void setRolDescription(String rolDescription) {
		this.rolDescription = rolDescription;
	}

}
