package com.dam.testparcial.T02;

import java.util.Scanner;

/**
 * Programa que crea una matriz 9x10 con las tablas de multiplicar del 1 al 9.
 * Pide al usuario un número del 1 al 9 y muestra la tabla correspondiente.
 */
public class ARRBIex06 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Crear la matriz 9x10
        int[][] tablas = new int[9][10];

        // Rellenar la matriz con las tablas de multiplicar
        fillTables(tablas);

        // Mostrar todas las tablas (opcional, para verificar)
        System.out.println("=== TABLAS DE MULTIPLICAR DEL 1 AL 9 ===\n");
        printAllTables(tablas);

        // Pedir al usuario un número del 1 al 9
        int numero = getValidNumber(sc);

        // Mostrar la tabla correspondiente
        System.out.println("\nTabla del " + numero + ":");
        printTable(tablas, numero);

        sc.close();
    }

    /**
     * Rellena la matriz con las tablas de multiplicar del 1 al 9.
     * Fila 0 = tabla del 1, Fila 1 = tabla del 2, etc.
     * 
     * @param tablas la matriz a rellenar
     */
    public static void fillTables(int[][] tablas) {
        for (int i = 0; i < tablas.length; i++) {
            for (int j = 0; j < tablas[i].length; j++) {
                // i+1 es el número de la tabla (1-9)
                // j+1 es el multiplicador (1-10)
                tablas[i][j] = (i + 1) * (j + 1);
            }
        }
    }

    /**
     * Muestra todas las tablas de multiplicar almacenadas en la matriz.
     * 
     * @param tablas la matriz con las tablas
     */
    public static void printAllTables(int[][] tablas) {
        for (int i = 0; i < tablas.length; i++) {
            System.out.print("Tabla del " + (i + 1) + ": ");
            for (int j = 0; j < tablas[i].length; j++) {
                System.out.print(tablas[i][j]);
                if (j < tablas[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Muestra la tabla de multiplicar correspondiente al número especificado.
     * 
     * @param tablas la matriz con las tablas
     * @param numero el número de la tabla a mostrar (1-9)
     */
    public static void printTable(int[][] tablas, int numero) {
        int fila = numero - 1; // Convertir a índice (1-9 -> 0-8)

        for (int j = 0; j < tablas[fila].length; j++) {
            System.out.print(tablas[fila][j]);
            if (j < tablas[fila].length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    /**
     * Solicita al usuario un número del 1 al 9 y valida la entrada.
     * 
     * @param sc el Scanner para leer la entrada
     * @return el número válido introducido por el usuario
     */
    public static int getValidNumber(Scanner sc) {
        int numero = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("\nIntroduce un número del 1 al 9: ");
                numero = sc.nextInt();

                if (numero >= 1 && numero <= 9) {
                    valid = true;
                } else {
                    System.out.println("Error: El número debe estar entre 1 y 9.");
                }
            } catch (Exception e) {
                System.out.println("Error: Debes introducir un número entero.");
                sc.nextLine();
            }
        }

        return numero;
    }
}
