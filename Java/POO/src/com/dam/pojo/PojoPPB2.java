package com.dam.pojo;

public class PojoPPB2 {
    private String disciplina;
    private double distancia;
    private double tiempo;
    private String fecha;

    public PojoPPB2(String disciplina, double distancia, double tiempo, String fecha) {
        this.disciplina = disciplina;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Actividad: " + disciplina + "\n" + "distancia " + distancia + ", " + tiempo + " min " + "\n" + "fecha " + fecha;
    }
    
}
