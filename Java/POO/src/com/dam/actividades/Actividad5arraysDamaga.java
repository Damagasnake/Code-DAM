package com.dam.actividades;

import java.util.Scanner;

public class Actividad5arraysDamaga {
    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int[] datos = recibirDatos();
        calcularMedia(datos);
    }

    private static int[] recibirDatos() {
        System.out.println("A cuantas personas vas a medir? ");
        int n = Integer.parseInt(sc.nextLine());
        int[] alturas = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Introduce la altura de la persona " + (i + 1) + " en cms");
            alturas[i] = Integer.parseInt(sc.nextLine());
        }
        sc.close();
        return alturas;
    }

    private static void calcularMedia(int[] alturas) {
        int suma = 0;
        for (int i = 0; i < alturas.length; i++) {
            suma += alturas[i];
        }
        int media = suma / alturas.length;

        for (int i = 0; i < alturas.length; i++) {
            if (alturas[i] < media) {
                System.out.println("enano");
            } else if (alturas[i] == media) {
                System.out.println("normie");
            } else {
                System.out.println("Gigante");
            }
        }
    }
}
