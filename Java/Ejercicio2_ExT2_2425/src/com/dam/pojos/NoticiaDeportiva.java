package com.dam.pojos;

public class NoticiaDeportiva extends Noticia {
	
	private String deporte;

	public NoticiaDeportiva(String titulo, String fecha, String deporte) {
		super(titulo, fecha);
		this.deporte = deporte;
	}
	
	@Override
	public String toString() {
		return TIPOS_NOTICIAS[0] + ":" + super.toString() 
				+ "\n\tDeporte: " + deporte;
	}
	
	

}
