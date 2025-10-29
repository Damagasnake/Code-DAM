package com.dam.ejercicio2.pojo;

public class OpcionRestaurante {
    private final int RAPIDO = 80;
    private final int MEDIO = 50;
    private final int LENTO = 30;
    private String nombre;
    private double distancia;
    private int trafico;

    public OpcionRestaurante(String nombre, double distancia, int trafico){
        this.distancia = distancia;
        this.trafico = trafico;
        // 0 = 80 / 1 = 50 / 2 = 30
        this.nombre = nombre;
    }
    public double calcularTiempo(){
        double tiempo = 0;
        if(trafico == 0){
            tiempo = distancia / RAPIDO;
        }
        else if(trafico == 1){
            tiempo = distancia / MEDIO;
        }
        else if(trafico == 2){
            tiempo = distancia / LENTO;
        }
        return tiempo;
    }
    public String showSpeed(){
         String stringVel = "";
        if (trafico == 0)
            stringVel = "poco";
        if (trafico == 1)
        stringVel = "normal";
        if (trafico == 2)
        stringVel = "mucho";
        return stringVel;

    }
    public String getNombre(){
        return nombre;
    }
    @Override
    public String toString() {
        String data = nombre + " a " + distancia + " km " + "\n" + showSpeed() + "trafico";
        return data;
    }
}
