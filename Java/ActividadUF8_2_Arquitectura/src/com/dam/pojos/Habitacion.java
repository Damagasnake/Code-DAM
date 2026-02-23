package com.dam.pojos;

public class Habitacion extends Estancia {
	private int numPuertasArm;
	private boolean accesoBanio;
	
	public Habitacion(String nombre, double metrosCuad, int numPuertas, 
			int numVentanas, int numPuertasArm, boolean accesoBanio) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
		this.numPuertasArm = numPuertasArm;
		this.accesoBanio = accesoBanio;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nNúmero de puertas de armario: " + numPuertasArm 
				+ "\n Tiene acceso a baño? " + (accesoBanio? "SI" : "NO");
	}
	

}
