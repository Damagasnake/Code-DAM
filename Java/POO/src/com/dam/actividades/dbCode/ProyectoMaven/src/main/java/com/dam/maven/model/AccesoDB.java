package com.dam.maven.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {
    private String driver;
    private String url = "";

    public AccesoDB(){
        driver = "org.sqlite.JDBC";
        url = "jdbc:sqlite:" + resolvePath("DB/TablaPrueba.db");
    }

    public Connection getConexion() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    private String resolvePath(String relativePath) {
        Path directPath = Paths.get(relativePath);
        if (Files.exists(directPath)) {
            return directPath.toAbsolutePath().toString();
        }

        Path projectPath = Paths.get("ProyectoMaven", relativePath);
        if (Files.exists(projectPath)) {
            return projectPath.toAbsolutePath().toString();
        }

        return directPath.toAbsolutePath().toString();
    }
}
