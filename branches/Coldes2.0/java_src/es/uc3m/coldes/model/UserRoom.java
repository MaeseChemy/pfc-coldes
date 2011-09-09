package es.uc3m.coldes.model;

public class UserRoom {

	private Room room;
	private String roomName;
	private String ownerUserName;
	private String userName;
	private int userfunction;
	private String userfunctionDescription;
	
	public UserRoom() {
	}

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

	public int getUserfunction() {
		return userfunction;
	}

	public void setUserfunction(int userfunction) {
		this.userfunction = userfunction;
	}

	public String getUserfunctionDescription() {
		return userfunctionDescription;
	}

	public void setUserfunctionDescription(String userfunctionDescription) {
		this.userfunctionDescription = userfunctionDescription;
	}

}
