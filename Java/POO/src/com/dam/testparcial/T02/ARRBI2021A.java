package com.dam.testparcial.T02;

import java.util.Random;
import java.util.Scanner;

public class ARRBI2021A {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.print("Introduce el valor n: ");
        int n = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el valor de m");
        int m = Integer.parseInt(sc.nextLine());

        int[][] arrayA = new int[n][m];
        fillArr(arrayA);
        printARR(arrayA);
    }

    public static void fillArr(int[][] array) {
        Random rd = new Random();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                int n;
                do {
                    n = rd.nextInt(1, 181);
                } while (n % 2 != 0 || n % 3 != 0);
                array[i][j] = n;
            }
        }
    }

    public static void printARR(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }
    public static void sumRow(int[][] array, int m){
        for (int i = 0; i < array.length; i++) {
            int suma = 0;
            for (int j = 0; j < array[i].length; j++) {
                suma += array[i][j];
            }
            if(suma > m * 20){
                System.out.println("fila " + i + " da " + suma);
            }
        }
        
        //cols

        for (int l = 0; l < array[0].length; l++) {
            int sumacol = 0;
            for (int k = 0; k < array.length; k++) {
                sumacol += array[k][l];
            }
            if (sumacol > m * 20) {
                System.out.println(sumacol + " es la suma de las columnas");
            }
        }
    }
}
