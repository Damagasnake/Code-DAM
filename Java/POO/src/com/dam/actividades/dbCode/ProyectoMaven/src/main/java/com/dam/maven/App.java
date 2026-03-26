package com.dam.maven;

import com.dam.maven.model.AccesoDB;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
  public static void main(String[] args) {
    AccesoDB accesoDB = new AccesoDB();
    try {
      Connection conexion = accesoDB.getConexion();
      System.out.println("Conexion exitosa");
      conexion.close();
      System.out.println("Conexion cerrada");
    }catch (ClassNotFoundException e){
      System.out.println("Error al conectar driver");
    }catch (SQLException e){
      System.out.println("Error al conectar url");
    }
  }
}
