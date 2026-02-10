package com.dam.actividades.arq;

public class Salon extends Estancia{
    private int numPuntosRed;
    private boolean tieneTerraza;
	private double m2Terr; // metros cuadrados del terraza
	
	public Salon(String nombre, double metrosCuad, int numPuertas, 
			int numVentanas, boolean tieneTerraza, double m2Terr, int numPuntosRed) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
		this.tieneTerraza = tieneTerraza;
		if (tieneTerraza) {
			this.m2Terr = m2Terr;
		}
	}

	public boolean isTieneTerraza() {
		return tieneTerraza;
	}

	public double getM2Tend() {
		return m2Terr;
	}
	
	@Override
	public String toString() {
		return super.toString() 
				+ "\nTiene Terraza? " + (tieneTerraza? "SI" : "NO") 
				+ (tieneTerraza? "\nMetros cuadrados Terraza: " + m2Terr : "")
                + numPuntosRed;
	}
}
