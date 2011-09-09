package es.uc3m.coldes.control.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class BBDD {

	static Logger logger = Logger.getLogger(BBDD.class.getName());

	private String url;
	private String port;
	private String schema;
	private String username;
	private String password;

	public BBDD() {
		Properties colDesPropierties = new Properties();

		try {
			// Cargamos el archivo de propiedades de ColDes
			logger.info("[BBDD]: Loading ColDes properties ...");
			InputStream in = ClassLoader.getSystemResourceAsStream("coldes.properties");
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

	public BBDD(Properties samProperties) {
		try {
			// Leemos las propiedades relativas a la conexión con la BBDD
			logger.info("[BBDD]: Reading propierties ...");
			url = samProperties.getProperty("db.url");
			port = samProperties.getProperty("db.port");
			schema = samProperties.getProperty("db.schema");
			username = samProperties.getProperty("db.username");
			password = samProperties.getProperty("db.password");

			// Cargamos el driver JDBC
			logger.info("[BBDD]: Opening MySQL connector...");
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("[BBDD]: Error: ", e);
		}

	}

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