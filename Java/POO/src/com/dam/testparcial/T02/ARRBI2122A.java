package com.dam.testparcial.T02;

import java.util.Scanner;

public class ARRBI2122A {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int[][] ArrayB = CrearArray();
        fillArrayB(ArrayB);
        sumaDiag(ArrayB);
    }

    public static int[][] CrearArray() {
        System.out.println("Introduce el valor n (columnas)");
        int n = Integer.parseInt(sc.nextLine());

        int[][] arrayB = new int[n][n];
        return arrayB;
    }

    private static void fillArrayB(int[][] arrayB) {
        int Valor;
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[i].length; j++) {
                System.out.println("Introduce un num para rellenar el array");
                Valor = Integer.parseInt(sc.nextLine());
                arrayB[i][j] = Valor;
            }
        }
    }

    private static void sumaDiag(int[][] arrayB) {
        int sumaInf = 0;
        int sumaSup = 0;
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[i].length; j++) {
                if (i < j) {
                    sumaSup += arrayB[i][j];
                } else if (i >= j) { // Incluye la diagonal
                    sumaInf += arrayB[i][j];
                }
            }
        }
        System.out.println(sumaInf);
        System.out.println(sumaSup);
        if (sumaInf % 2 == 0 && sumaSup % 2 == 0) {
            System.out.println("Las 2 son pares");
        } else if (sumaInf % 2 == 0) {
            System.out.println("la suma inferior es par");
        } else if (sumaSup % 2 == 0) {
            System.out.println("la suma superior es par");
        } else {
            System.out.println("Ninguna es par");
        }
    }
}
