package com.dam.pojos;

public class Noticia {
	
	public static final String[] TIPOS_NOTICIAS = {"Noticia Deportiva", 
			"Noticia Económica", "Noticia Política"};
	public static final String[] ESTADOS = {"BORRADOR",
			"PUBLICADA", "ARCHIVADA"};
	public static final int TAM_FECHA = 10;
	
	protected String titulo;
	protected String fecha;
	protected String estado;
	
	public Noticia(String titulo, String fecha) {
		this.titulo = titulo;
		this.fecha = fecha;
		this.estado = ESTADOS[0];
	}
	
	public String getTitulo() {
		return titulo;
	}

	public String getFecha() {
		return fecha;
	}

	public boolean cambiarEstado() {
		boolean cambioEstado = true;
		
		if (estado.equals(ESTADOS[0])) {
			estado = ESTADOS[1];
		} else if (estado.equals(ESTADOS[1])) {
			estado = ESTADOS[2];
		} else {
			cambioEstado = false;
		}
		
		return cambioEstado;
		
	}

	@Override
	public String toString() {
		return "\n\tTítulo: " + titulo + "\n\tFecha: " + fecha 
				+ "\n\tEstado: " + estado;
	}
	
	
	

}
