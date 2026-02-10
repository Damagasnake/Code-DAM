package com.dam.actividades.arq;

public class Habitacion extends Estancia {
    private int numPuertasArm;
    private boolean accesoBanio;

    public Habitacion(String nombre, double metrosCuad, int numPuertas, 
			int numVentanas, boolean tieneTerraza, int numPuertasArm, boolean accesoBanio) {
		super(nombre, metrosCuad, numPuertas, numVentanas);
        this.numPuertasArm = numPuertasArm;
            }

    @Override
    public String toString() {
        return "Habitacion [numPuertasArm=" + numPuertasArm + ", accesoBanio=" + accesoBanio + "]";
    }
}
