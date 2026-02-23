package com.dam.pojos;

// clase padre
public class Estancia {
	public static final String[] TIPOS_ESTANCIA = {"HALL", "COCINA", "SALÓN", "HABITACIÓN", "BAÑO"};
	
	protected String nombre;
	protected double metrosCuad;
	protected int numPuertas;
	protected int numVentanas;
	
	public Estancia(String nombre, double metrosCuad, 
			int numPuertas, int numVentanas) {
		this.nombre = nombre;
		this.metrosCuad = metrosCuad;
		this.numPuertas = numPuertas;
		this.numVentanas = numVentanas;
	}

	public double getMetrosCuad() {
		return metrosCuad;
	}

	@Override
	public String toString() {
		return "\nEstancia " + nombre 
				+ "\nMetros cuadrados: " + metrosCuad 
				+ "\nNúmero de puertas: " + numPuertas
				+ "\nNúmero de ventanas: " + numVentanas;
	}
	
	

}
