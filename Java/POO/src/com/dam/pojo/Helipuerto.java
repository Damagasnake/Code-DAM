package com.dam.pojo;

public class Helipuerto {
    public final double PI = 3.1416;
    private String nombre;
    private double radio;

    public Helipuerto(String nombre, double radio){
        this.radio = radio;
        this.nombre = nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }
    public void setRadio(double radio){
        this.radio = radio;
    }
    public double getRadio() {
        return radio;
    }
    public double areaH(){
        double area = PI * Math.pow(radio, 2);
        return area;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
