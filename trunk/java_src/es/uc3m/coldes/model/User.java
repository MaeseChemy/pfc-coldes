package es.uc3m.coldes.model;

/**
 * Clase que modela a un Usuario del sistema..
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class User {
	/**
	 * Nombre real del usuario.
	 */
	private String name;
	/**
	 * Primer apellido del usuario.
	 */
	private String surname1;
	/**
	 * Segundo apellido del usuario.
	 */
	private String surname2;
	/**
	 * Correo electronico del usuario.
	 */
	private String email;
	/**
	 * Nombre de usuario.
	 */
	private String username;
	/**
	 * Contraseña del usuario.
	 */
	private String password;
	/**
	 * Boolean que indica si el usuario es administrador
	 * o no.
	 */
	private boolean admin;
	/**
	 * Boolean que indica si el usuario es diseñador
	 * o no.
	 */
	private boolean designer;
	/**
	 * Boolean que indica si el usuario esta activo.
	 */
	private boolean active;
	
	/**
	 * Costructor por defecto de un nuevo Usuario.
	 */
	public User(){
	}
	
	/**
	 * Constructor de un nuevo usuario que recibe cada uno
	 * de sus atributos.
	 * 
	 * @param name Nombre real del nuevo usuario.
	 * @param surname1 Apellido 1 del nuevo usuario.
	 * @param surname2 Apellido 2 del nuevo usuario.
	 * @param email Correo electronico del nuevo usuario.
	 * @param username Nombre de usuario del nuevo usuario.
	 * @param password Contraseña del nuevo usuario.
	 * @param admin Boolean que indica si el usuario es administrador.
	 * @param designer Boolean que indica si el usuario es diseñador.
	 * @param active Boolean que indica si el usuario esta activo.
	 */
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
	
	/**
	 * Devuelve el nombre real del usuario.
	 * 
	 * @return Nombre del usuario.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Asigna un valor al nombre real del usuario.
	 * 
	 * @param name Nombre del usuario.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Devuelve el primer apellido del usuario.
	 * 
	 * @return Primer apellido del usuario.
	 */
	public String getSurname1() {
		return surname1;
	}
	/**
	 * Asigna un valor al primer apellido del usuario.
	 * 
	 * @param surname1 Primer apellido del usuario.
	 */
	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}
	
	/**
	 * Devuelve el segundo apellido del usuario.
	 * 
	 * @return Segundo apellido del usuario.
	 */
	public String getSurname2() {
		return surname2;
	}
	/**
	 * Asigna un valor al segundo apellido del usuario.
	 * 
	 * @param surname1 Segundo apellido del usuario.
	 */
	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}
	
	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return Correo electrónico del usuario.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Asigna un valor al correo electrónico del usuario.
	 * 
	 * @param Correo electrónico del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Devuelve el nombre de usuario del usuario.
	 * 
	 * @return Nombre de usuario.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Asigna el nombre de usuario del usuario.
	 * 
	 * @param Nombre de usuario.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Devuelve la contraseña de usuario.
	 * 
	 * @return Contraseña de usuario.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Asigna la contraseña de usuario.
	 * 
	 * @param Contraseña de usuario.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Indica si el usuario es administrador o no.
	 * 
	 * @return Boolean true si el usuario es admin, false
	 * en caso contrario.
	 */
	public boolean isAdmin() {
		return admin;
	}
	/**
	 * Asigna el valor que indica si el usuario es administrador
	 * o no.
	 * 
	 * @param admin Boolean true para asignar administrador al
	 * usuario, false en caso contrario.
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	/**
	 * Indica si el usuario es diseñador o no.
	 * 
	 * @return Boolean true si el usuario es diseñador, false
	 * en caso contrario.
	 */
	public boolean isDesigner() {
		return designer;
	}
	/**
	 * Asigna el valor que indica si el usuario es diseñador
	 * o no.
	 * 
	 * @param admin Boolean true para asignar diseñador al
	 * usuario, false en caso contrario.
	 */
	public void setDesigner(boolean designer) {
		this.designer = designer;
	}
	
	/**
	 * Asigna el valor que indica si el usuario esta activo.
	 * 
	 * @param admin Boolean true si el usuario esta activo, 
	 * false en caso contrario.
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * Asigna el valor que indica si el usuario esta activo
	 * o no.
	 * 
	 * @param admin Boolean true para activar al usuario,
	 * false para desactivar.
	 */
	public void setActive(boolean activo) {
		this.active = activo;
	}
	
}
