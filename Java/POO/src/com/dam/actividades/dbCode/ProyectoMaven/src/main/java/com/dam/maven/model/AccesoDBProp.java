package com.dam.maven.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccesoDBProp {
    private String driver;
    private String url = "";

    public AccesoDBProp() {
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream(resolvePath("DB/ConfigDB.properties").toString())) {
            properties.load(is);
            driver = properties.getProperty("DRIVER");
            url = "jdbc:sqlite:" + resolvePath("DB/TablaPrueba.db");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de propiedades");
        }
    }

    public Connection getConexion() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    private Path resolvePath(String relativePath) {
        Path directPath = Paths.get(relativePath);
        if (Files.exists(directPath)) {
            return directPath.toAbsolutePath();
        }

        Path projectPath = Paths.get("ProyectoMaven", relativePath);
        if (Files.exists(projectPath)) {
            return projectPath.toAbsolutePath();
        }

        return directPath.toAbsolutePath();
    }
}
