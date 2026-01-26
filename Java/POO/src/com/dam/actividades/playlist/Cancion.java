package com.dam.actividades.playlist;

public class Cancion {
    private String Titulo;
    private int Duracion;
    private String Artista;
    public Cancion(String titulo, int duracion, String artista) {
        this.Titulo = titulo;
        this.Duracion = duracion;
        this.Artista = artista;
    }
    
    @Override
    public String toString() {
        return "Song [Titulo=" + Titulo + ", Duracion=" + Duracion + ", Artista=" + Artista
                + "]";
    }
    
}
