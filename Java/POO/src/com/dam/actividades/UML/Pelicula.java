package com.dam.actividades.UML;

import java.util.Arrays;

import com.dam.pojo.punto;

public class Pelicula {
    public static final int CAPACIDAD = 15;
    private String titulo;
    private String director;
    private int duracion;
    private Actor[] actores;
    private int numActores;

    public Pelicula(){
        actores = new Actor[CAPACIDAD];
        numActores = 0;
    }

    public Pelicula(String titulo, String director, int duracion) {
        this.titulo = titulo;
        this.director = director;
        this.duracion = duracion;
        actores = new Actor[CAPACIDAD];
        numActores = 0;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public void mostrarPelicula(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Pelicula [titulo=" + titulo + ", director=" + director + ", duracion=" + duracion + ", actores="
                + Arrays.toString(actores) + ", numActores=" + numActores + "]";
    }
    public void addActor(Actor actor){
        actores[numActores] = actor;
        numActores++;
    }
}
