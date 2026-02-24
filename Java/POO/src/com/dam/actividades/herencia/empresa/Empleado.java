package com.dam.actividades.herencia.empresa;

public class Empleado {
    private static final String[] TIPO_EMP = {"Produccion", "Distribucion"};
    protected String nombre;
    protected String dni;
    protected double salario;
    
    public Empleado(String nombre, String dni, double salario) {
        this.nombre = nombre;
        this.dni = dni;
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "Empleado [nombre=" + nombre + ", dni=" + dni + ", salario=" + salario + "]";
    }
}
