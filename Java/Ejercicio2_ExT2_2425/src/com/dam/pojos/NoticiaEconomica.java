package com.dam.pojos;

public class NoticiaEconomica extends Noticia {
	
	private String sector;

	public NoticiaEconomica(String titulo, String fecha, String sector) {
		super(titulo, fecha);
		this.sector = sector;
	}
	
	@Override
	public String toString() {
		return TIPOS_NOTICIAS[1] + ":" + super.toString() 
				+ "\n\tSector: " + sector;
	}
	
	

}
