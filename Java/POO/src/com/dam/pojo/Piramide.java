package com.dam.pojo;

public class Piramide {
    private String nombre;
    private double lado;
    private double altura;
public Piramide(String nombre, double lado, double altura){
    this.altura = altura;
    this.nombre = nombre;
    this.lado = lado;
}
public double CalculoVol(){
    double areaBase = lado * lado;
    return (areaBase * altura) / 2;
}
@Override
public String toString() {
    String datos = " El nombre de tu piramide es " + nombre + " el lado de tu piramide es " + lado + " su altura es " + altura;
    return datos;
}
}