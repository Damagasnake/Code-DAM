package com.dam.model;

import java.util.ArrayList;

public class LstEncuestas {
	
	private ArrayList<Encuesta> lstE;
	
	public LstEncuestas() {
		lstE = new ArrayList<Encuesta>();
	}
	
	public ArrayList<Encuesta> getLstESeries(){
		return lstE;
	}
	
	public void addLstESeries(Encuesta e) {
		lstE.add(e);
	}
	
	public void clear() {
		lstE.removeAll(lstE);
	}
}
