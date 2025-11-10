package com.dam.actividades;
import java.util.Scanner;
public class ActividadTelesillaDamaga {

    static final int PESO_MINIMO = 150;
    static final int PERSONAS_MAXIMAS = 4;
    static final int PERSONAS_EN_FILA = 15;

    public void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int personasmontadas = 0;
        double pesototal = 0;
        while (personasmontadas < PERSONAS_MAXIMAS && pesototal < PESO_MINIMO) {
            System.out.println("Introduce el peso de la persona " + (personasmontadas + 1) + ":");
            double peso = Double.parseDouble(sc.nextLine());
            pesototal += peso;
            personasmontadas++;
        }
        if (personasmontadas < PERSONAS_MAXIMAS && pesototal < PESO_MINIMO) {
            System.out.println("El telesilla no puede salir");
        } else {
            System.out.println("El telesilla puede salir");
        }
        sc.close();
    }
}