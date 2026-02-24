package com.dam.actividades.herencia.empresa;

public class EmpleadoDistribuccion extends Empleado{
    private String zona;

    public EmpleadoDistribuccion(String nombre, String dni, double salario, String zona) {
        super(nombre, dni, salario);
        this.zona = zona;
    }

    @Override
    public String toString() {
        return super.toString() + "zona " + zona;
    }
    
}
