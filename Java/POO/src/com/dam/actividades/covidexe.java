package com.dam.actividades;

import java.util.Scanner;
import com.dam.pojo.covid;

public class covidexe {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = nencuestas();
        covid[] pacientes = realizarEncuestas(n);
        int sup = covidsup(pacientes);
        int grav = pacgrav(pacientes);
        covid mayor = mayorConMenorGravedad(pacientes);
        System.out.println("El numero de pacientes que estan enfermos es " + sup);
        System.out.println("El numero de pacientes que tienen una gravedad mayor a 3 es " + grav);
        System.out.println("El paciente con menor gravedad y mayor edad es " + mayor);
    }

    private static int nencuestas() {
        System.out.println("Introduce el numero de encuestas");
        int n = Integer.parseInt(sc.nextLine());
        return n;
    }

    private static covid encuesta() {
        System.out.println("Introduce la edad");
        int edad = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce los sintomas");
        String sintomas = sc.nextLine();
        System.out.println("Introduce si esta enfermo");
        String enfermado = sc.nextLine();
        return new covid(edad, sintomas, enfermado);
    }

    private static covid[] realizarEncuestas(int n) {
        covid[] pacientes = new covid[n];
        for (int i = 0; i < n; i++) {
            pacientes[i] = encuesta();
        }
        return pacientes;
    }

    private static int covidsup(covid[] pacientes) {
        int sup = 0;
        for (int i = 0; i < pacientes.length; i++) {
            if (pacientes[i].getEnfermado().equals("Si")) {
                sup++;
            }
        }
        return sup;
    }

    private static int pacgrav(covid[] pacientes) {
        int grav = 0;
        for (int i = 0; i < pacientes.length; i++) {
            if (pacientes[i].calcnivelGravedad() > 3) {
                grav++;
            }
        }
        return grav;
    }
    private static covid mayorConMenorGravedad(covid[] pacientes){
        covid resultado = null;
        
        for(int i = 0; i < pacientes.length; i++){
            if(pacientes[i].getEnfermado().equals("Si")){
                if(resultado == null){
                    resultado = pacientes[i];
                } else {
                    if(pacientes[i].getNivelGravedad() < resultado.getNivelGravedad() ||
                       (pacientes[i].getNivelGravedad() == resultado.getNivelGravedad() && 
                        pacientes[i].getEdad() > resultado.getEdad())){
                        resultado = pacientes[i];
                    }
                }
            }
        }
        return resultado;
    }
}