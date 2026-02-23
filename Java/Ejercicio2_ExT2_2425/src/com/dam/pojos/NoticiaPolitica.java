package com.dam.pojos;

public class NoticiaPolitica extends Noticia {
	
	public static final String [] PARTIDOS = {"Partido Socialista Obrero Español", 
			"Alianza Popular", "Izquierda Unida", "Centro Democrático Social"};
				
	private String partido;

	public NoticiaPolitica(String titulo, String fecha, String partido) {
		super(titulo, fecha);
		this.partido = partido;
	}
	
	public String obtenerInicialesPartido() {
		String inicialies = "";
		
		// Alianza Popular --> AP
		// split --> {"Alianza", "Popular"}
		String [] palabras = partido.split(" ");
		
		for (int i = 0; i < palabras.length; i++) {
			inicialies += palabras[i].charAt(0);
		}
		
		return inicialies;
	}

	@Override
	public String toString() {
		return TIPOS_NOTICIAS[2] + ":" + super.toString() 
				+ "\n\tPartido: " + partido + "(" + obtenerInicialesPartido() + ")";
	}
	
	

}
