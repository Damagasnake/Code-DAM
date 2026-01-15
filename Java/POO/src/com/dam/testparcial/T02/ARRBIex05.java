package com.dam.testparcial.T02;

import java.util.Random;
import java.util.Scanner;

public class ARRBIex05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // Solicitar dimensiones de la matriz
        System.out.print("Introduce el número de filas (n): ");
        int n = sc.nextInt();

        System.out.print("Introduce el número de columnas (m): ");
        int m = sc.nextInt();

        // Validar que la matriz tenga al menos 2 columnas para el intercambio
        if (m < 2) {
            System.out.println("Error: La matriz debe tener al menos 2 columnas para realizar el intercambio.");
            sc.close();
            return;
        }

        // Crear la matriz
        int[][] matriz = new int[n][m];

        // Rellenar la matriz con enteros aleatorios (rango 0-99)
        fillMatrix(matriz, rand);

        // Imprimir la matriz antes del cambio
        System.out.println("\n=== MATRIZ ANTES DEL INTERCAMBIO ===");
        printMatrix(matriz);

        // Intercambiar la primera columna por la segunda
        swapColumns(matriz, 0, 1);

        // Imprimir la matriz después del cambio
        System.out.println("\n=== MATRIZ DESPUÉS DEL INTERCAMBIO ===");
        printMatrix(matriz);

        sc.close();
    }

    /**
     * Rellena la matriz con números aleatorios entre 0 y 99
     */
    public static void fillMatrix(int[][] matriz, Random rand) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = rand.nextInt(100); // Números del 0 al 99
            }
        }
    }

    /**
     * Imprime la matriz en formato tabular
     */
    public static void printMatrix(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%4d ", matriz[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Intercambia dos columnas de la matriz
     */
    public static void swapColumns(int[][] matriz, int col1, int col2) {
        for (int i = 0; i < matriz.length; i++) {
            int temp = matriz[i][col1];
            matriz[i][col1] = matriz[i][col2];
            matriz[i][col2] = temp;
        }
    }
}
