package es.uc3m.coldes.model;

import java.util.Date;

public class RoomUserPencilRequest {
	private int idRoom;
	private String username;
	private int userrol;
	private Date requestTime;
	private boolean pencilowner;
	
	public RoomUserPencilRequest(){
		super();
	}
	
	public RoomUserPencilRequest(int idRoom, String username, int userrol,
			Date requestTime, boolean pencilowner) {
		super();
		this.idRoom = idRoom;
		this.username = username;
		this.userrol = userrol;
		this.requestTime = requestTime;
		this.pencilowner = pencilowner;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserrol() {
		return userrol;
	}

	public void setUserrol(int userrol) {
		this.userrol = userrol;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public boolean isPencilowner() {
		return pencilowner;
	}

	public void setPencilowner(boolean pencilowner) {
		this.pencilowner = pencilowner;
	}
	
}
