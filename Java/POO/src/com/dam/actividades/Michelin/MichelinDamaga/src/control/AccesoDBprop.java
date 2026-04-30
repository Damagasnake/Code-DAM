package control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccesoDBprop {
	private final Properties properties = new Properties();

	public AccesoDBprop() {
		properties.setProperty("db.url", "jdbc:sqlite:DB/GUIA_MICHELIN.db");
		cargarPropiedades();
	}

	private void cargarPropiedades() {
		Path rutaPropiedades = Paths.get("db.properties");
		if (!Files.exists(rutaPropiedades)) {
			return;
		}

		try (InputStream inputStream = Files.newInputStream(rutaPropiedades)) {
			properties.load(inputStream);
		} catch (IOException ignored) {
			// Si no se puede leer el fichero externo, se usa la URL por defecto.
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(properties.getProperty("db.url"));
	}

	public String getDbUrl() {
		return properties.getProperty("db.url");
	}
}

