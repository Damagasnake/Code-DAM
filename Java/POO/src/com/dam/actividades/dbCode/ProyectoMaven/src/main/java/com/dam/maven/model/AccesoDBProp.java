package com.dam.maven.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AccesoDBProp {
    private String driver;
    private String url = "";

    public AccesoDBProp() {
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("DB/ConfigDB.properties")) {
            properties.load(is);
            driver = properties.getProperty("DRIVER");
            url = properties.getProperty("URL");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de propiedades");
        }
    }

    public Connection getConexion() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }
}