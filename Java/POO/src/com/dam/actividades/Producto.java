package com.dam.actividades;

public class Producto {
    String name; 
    int cant;
    public Producto(String name, int cant) {
        this.name = name;
        this.cant = cant;
    }
    @Override
    public String toString() {
        return "Productos lista " + name + ", cantidad a comprar " + cant + "]";
    }
    
}

