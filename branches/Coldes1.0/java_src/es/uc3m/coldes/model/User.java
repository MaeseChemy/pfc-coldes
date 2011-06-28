package es.uc3m.coldes.model;

public class User {

	private String name;
	private String surname1;
	private String surname2;
	private String email;
	private String username;
	private String password;
	private boolean admin;
	private boolean designer;
	private boolean active;
	
	public User(){
	}
	
	public User(String name, String surname1, String surname2,
			String email, String username, String password, boolean admin,
			boolean designer, boolean active) {
		super();

		this.name = name;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.email = email;
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.designer = designer;
		this.active = active;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname1() {
		return surname1;
	}
	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}
	public String getSurname2() {
		return surname2;
	}
	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isDesigner() {
		return designer;
	}
	public void setDesigner(boolean designer) {
		this.designer = designer;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean activo) {
		this.active = activo;
	}
	
}
