package es.uc3m.coldes.model;

/**
 * Clase que modela a un diseño del sistema..
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class Design {
	
	/**
	 * Nombre del usuario que guarda el diseño.
	 */
	private String username;
	/**
	 * Contenido del diseño.
	 */
	private byte[] designcontent;
	/**
	 * Nombre del diseño.
	 */
	private String designname;
	
	/**
	 * Constructor por defecto de un nuevo diseño.
	 */
	public Design() {
	}
	/**
	 * Constructor de un diseño que recibe los elementos de un nuevo
	 * diseño.
	 * 
	 * @param username Nombre de usuario.
	 * @param designcontent Contenido del diseño.
	 * @param designname Nombre del diseño.
	 */
	public Design(String username, byte[] designcontent, String designname) {
		super();
		this.username = username;
		this.designcontent = designcontent;
		this.designname = designname;
	}

	/**
	 * Obtiene el nombre de usuario del usuario que
	 * guardo el diseño.
	 * 
	 * @return Nombre de usuario.
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Asigna un valor al nombre de usuario del usuario
	 * que guarda el diseño.
	 * 
	 * @param username Nombre de usuario.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Obtiene el contenido del diseño en forma de array
	 * de bytes.
	 * 
	 * @return Contenido del diseño.
	 */
	public byte[] getDesigncontent() {
		return designcontent;
	}
	/**
	 * Asigna una valor al contenido del diseño.
	 * 
	 * @param designcontent Contenido del diseño en formato
	 * de array de bytes.
	 */
	public void setDesigncontent(byte[] designcontent) {
		this.designcontent = designcontent;
	}
	
	/**
	 * Obtiene el nombre del diseño.
	 * 
	 * @return Nombre del diseño.
	 */
	public String getDesignname() {
		return designname;
	}
	/**
	 * Asigna un nombre al diseño.
	 * @param designname Nombre del diseño.
	 */
	public void setDesignname(String designname) {
		this.designname = designname;
	}

}
