package com.dam.pojos;

public class Salon extends Estancia {
	
	private int numPuntosRed;
	private boolean tieneTerraza;
	private double m2Terr;
	
	public Salon(String nombre, double metrosCuad, int numPuertas, 
			int numVentanas, int numPuntosRed,
			boolean tieneTerraza, double m2Terr) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
		this.numPuntosRed = numPuntosRed;
		this.tieneTerraza = tieneTerraza;
		if (tieneTerraza) {
			this.m2Terr = m2Terr;
		} // si no, m2Terr = 0
	}

	public boolean isTieneTerraza() {
		return tieneTerraza;
	}

	public double getM2Terr() {
		return m2Terr;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nNúmero de puntos de red: " + numPuntosRed 
				+ "\nTiene terraza? " + (tieneTerraza? "SI" : "NO") 
				+ (tieneTerraza? "\nMetros cuadrados terraza: " + m2Terr + " m2": "");
	}

}
