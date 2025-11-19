package com.dam.actividades;

import java.util.Random;
import java.util.Scanner;

public class Ejercicio6ArraysDamaga {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int[] array = crearArr();
        mostrarArray(array);
        sumarArray(array);
        sc.close();
    }

    private static int[] crearArr() {
        Random rd = new Random();
        System.out.println("Tamaño del array a generar = ");
        int len = Integer.parseInt(sc.nextLine());
        int cont = 0;
        int i = 0;
        int[] array = new int[len];
        while (cont < len) {

            array[i] = rd.nextInt(0, 10);
            i++;
            cont++;
        }
        return (array);
    }

    private static void mostrarArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private static void sumarArray(int[] array) {
        int sumArr = 0;
        for (int i = 0; i < array.length; i++) {
            sumArr += array[i];
        }
        System.out.println(sumArr);
    }
}
