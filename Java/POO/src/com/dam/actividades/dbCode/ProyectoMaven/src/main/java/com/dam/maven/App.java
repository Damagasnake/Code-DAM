package com.dam.maven;

import com.dam.maven.model.AccesoDB;
import com.dam.maven.model.PPersistencia;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
  public static void main(String[] args) {
    PPersistencia TP = new PPersistencia();
    TP.runSelect();
  }
}