package com.dam.pojos;

public class Cocina extends Estancia {
	
	private boolean tieneTendedero;
	private double m2Tend; // metros cuadrados del tendedero
	
	public Cocina(String nombre, double metrosCuad, int numPuertas, 
			int numVentanas, boolean tieneTendedero, double m2Tend) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
		this.tieneTendedero = tieneTendedero;
		if (tieneTendedero) {
			this.m2Tend = m2Tend;
		} // si no, m2Tend = 0
	}

	public boolean isTieneTendedero() {
		return tieneTendedero;
	}

	public double getM2Tend() {
		return m2Tend;
	}
	
	@Override
	public String toString() {
		return super.toString() 
				+ "\nTiene tendedero? " + (tieneTendedero? "SI" : "NO") 
				+ (tieneTendedero? "\nMetros cuadrados tendedero: " + m2Tend + " m2": "");
	}
	

}
