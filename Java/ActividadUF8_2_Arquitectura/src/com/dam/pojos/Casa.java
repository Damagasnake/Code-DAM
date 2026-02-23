package com.dam.pojos;

import java.util.ArrayList;

public class Casa {
	private String direccion;
	private ArrayList<Estancia> listaEstancias;

	public Casa(String direccion) {
		this.direccion = direccion;
		// this.listaEstancias = listaEstancias;
		listaEstancias = new ArrayList<Estancia>();
	}
	
	public void addEstancia(Estancia estancia) {
		listaEstancias.add(estancia);
	}

	public ArrayList<Estancia> getListaEstancias() {
		return listaEstancias;
	}

	public double calcularTotalM2() {
		double totalM2 = 0;
		
		for (Estancia estancia : listaEstancias) {
			totalM2 += estancia.getMetrosCuad();
			
			if (estancia instanceof Cocina) {
				Cocina objCocina = (Cocina) estancia;
				if (objCocina.isTieneTendedero()) {
					totalM2 += objCocina.getM2Tend();
				}
				
			} else if (estancia instanceof Salon) {
				if (((Salon) estancia).isTieneTerraza()) {
					totalM2 += ((Salon) estancia).getM2Terr();
				}
			}
		}
		
		return totalM2;
	}
	
	@Override
	public String toString() {
		String cadena = "";
		
		cadena = "La casa con dirección \n" + direccion + " tiene " + listaEstancias.size() + " estancias:\n";
		
		for (Estancia estancia : listaEstancias) {
			cadena += "\n" + estancia;
		}
		
		return cadena;
	}
}
 