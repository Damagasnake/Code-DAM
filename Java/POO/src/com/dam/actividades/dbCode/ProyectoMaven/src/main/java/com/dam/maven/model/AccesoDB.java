package com.dam.maven.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {
    private String driver;
    private String url = "";

    public AccesoDB(){
        driver = "org.sqlite.JDBC";
        url = "jdbc:sqlite:DB/TablaPrueba.db";
    }
    public Connection getConexion() throws SQLException, ClassNotFoundException {
            Class.forName(driver);
        return DriverManager.getConnection(url);
        }
    }