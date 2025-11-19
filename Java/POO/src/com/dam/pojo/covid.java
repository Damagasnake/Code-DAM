package com.dam.pojo;

public class covid {
    static String[] POSIBLES_SINTOMAS = {
            "diarrea",
            "Cansancio",
            "Olfato",
            "Fiebre",
            "gusto",
            "nada"
    };

    static int[] POSIBLES_GRAVEDAD = { 1, 2, 3, 4, 5 };
    static String[] GRAVEDAD_S = {
            "Ninguno",
            "Leve",
            "Moderado",
            "Severo",
            "Critico"
    };

    static final String[] POSIBLES_ENFERMO = {
            "Si",
            "No"
    };
    private int edad;
    private String sintomas;
    private int nivelGravedad;
    private String enfermado;
    public covid(int edad, String sintomas, int nivelGravedad, String enfermado) {
        this.edad = edad;
        this.sintomas = sintomas;
        this.nivelGravedad = nivelGravedad;
        this.enfermado = enfermado;
    }
    @Override
    public String toString() {
        return "covid [edad=" + edad + ", sintomas=" + sintomas + ", nivelGravedad=" + nivelGravedad + ", enfermado="
                + enfermado + "]";
    }
}
