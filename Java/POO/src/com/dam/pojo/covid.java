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
    public covid(int edad, String sintomas, String enfermado) {
        this.edad = edad;
        this.sintomas = sintomas;
        this.nivelGravedad = calcnivelGravedad();
        this.enfermado = enfermado;
    }
    
    public int getNivelGravedad() {
        return nivelGravedad;
    }
    
    public String getEnfermado() {
        return enfermado;
    }

    public int getEdad() {
        return edad;
    }
    public int calcnivelGravedad(){
        int nivelGravedad = 0;
        String[] sintomasPaciente = sintomas.split(",");
        for(int i = 0; i < sintomasPaciente.length; i++){
            String sintoma = sintomasPaciente[i].trim();
            if(!sintoma.isEmpty() && !sintoma.equals("Ninguno")){
                nivelGravedad++;
            }
        }
        
        if(nivelGravedad == 0){
            return 0;
        } else if(nivelGravedad == 1){
            return 1;
        } else if(nivelGravedad == 2){
            return 2;
        } else if(nivelGravedad == 3){
            return 3;
        } else if(nivelGravedad == 4){
            return 4;
        } else {
            return 5;
        }
    }
    @Override
    public String toString() {
        return "covid [edad=" + edad + ", sintomas=" + sintomas + ", nivelGravedad=" + calcnivelGravedad() + ", enfermado="
                + enfermado + "]";
    }
}
