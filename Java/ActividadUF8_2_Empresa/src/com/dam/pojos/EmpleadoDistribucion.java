package com.dam.pojos;

public class EmpleadoDistribucion extends Empleado {
	
	private String zona;

	public EmpleadoDistribucion(String nombre, String dni, double salario, 
			String zona) {
		super(nombre, dni, salario);
		this.zona = zona;
	}
	
	@Override
	public String toString() {
		return "\nEmpleado de Distribución: " + super.toString()
		+ "\nZona de reparto: " + zona;
	}

}
