package com.dam.maven.model;

public class PPersistencia {
    static final String NOM_TABLA = "Damaga";
    static final String NOM_COLUMNA_ID = "ID";
    static final String NOM_COLUMNA_DESCRIPCION = "DESCRIPTION";
    private AccesoDBProp accesoDBProp;

    public PPersistencia(){
        accesoDBProp = new AccesoDBProp();
    }
    public void runSelect(){
        // SELECT ID,DESCRIPTION FROM Damaga
        String SentenciaSelect = "SELECT" + NOM_COLUMNA_ID + "," + NOM_COLUMNA_DESCRIPCION + "FROM" + NOM_TABLA;
    }
}
