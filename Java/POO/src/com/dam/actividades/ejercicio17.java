package com.dam.actividades;

import java.util.Scanner;

public class ejercicio17 {
    static final int MANZANASBANDEJA = 5;
    static final int NMANZANAS = 200;
    static final double precioKG = 1.85;

    public static void main(String[] args) {
        int manzanasprocesadastotal = 0;
        int manzanasprocesadasbandeja = 0;
        double pesoTotalBandeja = 0;
        double peso = 0;
        double precioBandeja = 0;
        int numeroBandeja = 1;
        Scanner sc = new Scanner(System.in);

        while (manzanasprocesadastotal < NMANZANAS) {
            System.out.println("Introduce el peso de la manzana " + (manzanasprocesadastotal + 1) + " (en kg): ");
            peso = Double.parseDouble(sc.nextLine());

            pesoTotalBandeja += peso;
            manzanasprocesadasbandeja++;
            manzanasprocesadastotal++;

            if (manzanasprocesadasbandeja == MANZANASBANDEJA) {
                precioBandeja = pesoTotalBandeja * precioKG;
                System.out.println("\nBandeja " + numeroBandeja + " completada");
                System.out.println("Peso total de la bandeja: " + pesoTotalBandeja + " kg");
                System.out.println("Precio de la bandeja: " + precioBandeja + " EUR\n");

                manzanasprocesadasbandeja = 0;
                pesoTotalBandeja = 0;
                numeroBandeja++;
            }
        }
        System.out.println("Total de manzanas procesadas: " + manzanasprocesadastotal);
        System.out.println("Total de bandejas: " + (numeroBandeja - 1));

        sc.close();
    }

}