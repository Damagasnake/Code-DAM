package com.dam.pojos;

public class Hall extends Estancia {
	
	private boolean puertaBlind;

	public Hall(double metrosCuad, int numPuertas, 
			int numVentanas, boolean puertaBlind) {
		super("Hall", metrosCuad, numPuertas, numVentanas);
		this.puertaBlind = puertaBlind;
	}
	
	@Override
	public String toString() {
		return super.toString() 
				+ "\nTiene puerta blindada? " + (puertaBlind? "SI" : "NO");
	}

}
