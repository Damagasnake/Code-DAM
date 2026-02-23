package com.dam.pojos;

public class Banio extends Estancia {
	
	private boolean ducha;
	private boolean banera;
	
	public Banio(String nombre, double metrosCuad, int numPuertas, int numVentanas, boolean ducha, boolean banera) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
		this.ducha = ducha;
		this.banera = banera;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nTiene ducha? " + (ducha? "SI" : "NO")
				+ "\nTiene bañera? " + (banera? "SI" : "NO");
	}

}
