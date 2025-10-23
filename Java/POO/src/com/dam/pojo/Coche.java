package com.dam.pojo;
public class Coche{
    private String matricula;
    private int velocidad;
    
    public void acelera(int i){
        velocidad += i;
    }
    public void frena(int i){
        velocidad -= i;
    }
    public String getMatricula(){
        return(matricula);
    }
    public int getVelocidad(){
        return(velocidad);
    }
    public void setMatricula(String valor){
        matricula = valor;
    }
    public void setVelocidad(int valor){
        velocidad = valor;
    }
}