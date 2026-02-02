package com.dam.actividades.UML;

public class Actor {
    private String nombre;
    private int edad;

    public Actor(){}

    public Actor(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public void mostrarActor(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Autor [nombre=" + nombre + ", edad=" + edad + "]";
    }
    
}
