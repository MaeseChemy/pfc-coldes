package es.uc3m.coldes.control.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Clase base encargada de modelar la conexión a base de datos.
 * Los DAO`s usados por la parte servidora extenderan de esta clase.
 * 
 * @author Jose Miguel Blanco García.
 *
 */
public class BBDD {

	static Logger logger = Logger.getLogger(BBDD.class.getName());
	
	/**
	 * URL de la base de datos.
	 */
	private String url;
	/**
	 * Puerto en el que se encuentra escuchando la base de datos.
	 */
	private String port;
	/**
	 * Schema que contiene los datos de la aplicación.
	 */
	private String schema;
	/**
	 * Nombre de usuario para conectarse a la base de datos del sistema.
	 */
	private String username;
	/**
	 * Password del usuario de la base de datos.
	 */
	private String password;
	
	/**
	 * Constructor por defecto en el que se leen las propiedades de la base de
	 * datos del fichero de propiedades coldes.properties.
	 */
	public BBDD() {
		Properties colDesPropierties = new Properties();

		try {
			// Cargamos el archivo de propiedades de ColDes
			logger.info("[BBDD]: Loading ColDes properties ...");
			//InputStream in = ClassLoader.getSystemResourceAsStream("coldes.properties");
			InputStream in = this.getClass().getClassLoader().getResource("coldes.properties").openStream();
			colDesPropierties.load(in);
			in.close();

			// Leemos las propiedades relativas a la conexión con la BBDD
			logger.info("[BBDD]: Reading propierties ...");
			url = colDesPropierties.getProperty("db.url");
			port = colDesPropierties.getProperty("db.port");
			schema = colDesPropierties.getProperty("db.schema");
			username = colDesPropierties.getProperty("db.username");
			password = colDesPropierties.getProperty("db.password");

			// Cargamos el driver JDBC
			logger.info("Cargando conector MySQL");
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			logger.error("[BBDD]: Error: ", e);
		} catch (FileNotFoundException e) {
			logger.error("[BBDD]: ColDes file properties not found: "
					+ e.getLocalizedMessage());
		} catch (IOException e) {
			logger.error("[BBDD]: Error opening ColDes file properties: "
					+ e.getLocalizedMessage());
		}

	}
	
	/**
	 * Constructr que recibe ya las distintas propiedades de la conexión
	 * a base de datos.
	 * 
	 * @param colDesPropierties Propiedades de la base de datos.
	 */
	public BBDD(Properties colDesPropierties) {
		try {
			// Leemos las propiedades relativas a la conexión con la BBDD
			logger.info("[BBDD]: Reading propierties ...");
			url = colDesPropierties.getProperty("db.url");
			port = colDesPropierties.getProperty("db.port");
			schema = colDesPropierties.getProperty("db.schema");
			username = colDesPropierties.getProperty("db.username");
			password = colDesPropierties.getProperty("db.password");

			// Cargamos el driver JDBC
			logger.info("[BBDD]: Opening MySQL connector...");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("[BBDD]: Error: ", e);
		}

	}

	/**
	 * Función mediante la cual se obtiene una conexión a la base de datos
	 * del sistema.
	 * 
	 * @return Conexión a la base de datos.
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			// Intentamos conectarnos a la base de Datos
			logger.info("[BBDD]: Connecting to the database of ColDes");
			conn = DriverManager.getConnection("jdbc:mysql://" + url + ":"
					+ port + "/" + schema, username, password);

			logger.info("[BBDD]: Database connection established");
		} catch (SQLException ex) {
			logger.error("[BBDD]: Error MySQL: ", ex);
		}
		return conn;

	}

}