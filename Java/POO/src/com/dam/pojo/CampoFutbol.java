package com.dam.pojo;

public class CampoFutbol {
    private int largo;
    private int ancho;
    private int capacidad;
    private String nombre;

    public CampoFutbol(int largo, int ancho, int capacidad, String nombre) {
        this.ancho = ancho;
        this.largo = largo;
        this.capacidad = capacidad;
        this.nombre = nombre;
    }

    public int areaCampos() {
        int area = largo * ancho;
        return area;
    }

    public int perimeroCampos() {
        int perimetro = largo + largo + ancho + ancho;
        return perimetro;
    }

    @Override
    public String toString() {
        String cadena = "El nombre de tu campo es " + nombre + " su capacidad es " + capacidad + " su alto es " + largo
                + " Su ancho es " + ancho;
        return cadena;
    }
}