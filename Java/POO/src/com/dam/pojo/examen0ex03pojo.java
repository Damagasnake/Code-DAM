package com.dam.pojo;

public class examen0ex03pojo {
    private String nombre;
    private String ciudad;
    private int ganados;
    private int empatados;
    private int perdidos;
    
    public examen0ex03pojo(String nombre, String ciudad,int ganados, int empatados, int perdidos){
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.empatados = empatados;
        this.perdidos = perdidos;
        this.ganados = ganados;
    }
    public int puntos(){
        return ganados * 3 + empatados + perdidos;
    }
    @Override
    public String toString() {
        String res = nombre + " - " + ciudad + "\n" + "numero de partidos ganados " + ganados + "\n" + "numero de partidos empatados " + empatados + "numero de partidos perdidos " + perdidos;
        return res;
    }
    public String getName(){
        return nombre;
    }
}
