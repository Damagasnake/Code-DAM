package com.dam.pojos;

public class Empleado {
	
	public static final String[] TIPOS_EMPLEADO = {"Producción", "Distribución"};
	public static final String[] TIPOS_EMPLEADO_ABR = {"P", "D"};
	
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
		return "\nNombre:" + nombre + "\nDNI: " + dni + "\nSalario: " + salario;
	}
	
	

}
