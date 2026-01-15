package com.dam.testparcial.T02;

import java.util.Scanner;

/**
 * Programa que crea una matriz n×m, la rellena con múltiplos de 5
 * entre 10 y 100, y muestra su traspuesta.
 */
public class ARRBIex04 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Solicitar dimensiones de la matriz
        int n = getDimension(sc, "n (filas)");
        int m = getDimension(sc, "m (columnas)");

        // Crear la matriz
        int[][] matriz = new int[n][m];

        // Rellenar con múltiplos de 5 entre 10 y 100
        fillMatriz(matriz);

        // Mostrar la matriz original
        System.out.println("\n=== MATRIZ ORIGINAL (" + n + "×" + m + ") ===");
        printMatriz(matriz);

        // Calcular la traspuesta
        int[][] traspuesta = getTranspose(matriz);

        // Mostrar la matriz traspuesta
        System.out.println("\n=== MATRIZ TRASPUESTA (" + m + "×" + n + ") ===");
        printMatriz(traspuesta);

        sc.close();
    }

    /**
     * Solicita una dimensión al usuario y valida la entrada.
     * 
     * @param sc     el Scanner para leer la entrada
     * @param nombre el nombre de la dimensión (para mostrar al usuario)
     * @return la dimensión válida (mayor que 0)
     */
    public static int getDimension(Scanner sc, String nombre) {
        int dimension = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("Introduce " + nombre + ": ");
                dimension = sc.nextInt();

                if (dimension > 0) {
                    valid = true;
                } else {
                    System.out.println("Error: La dimensión debe ser mayor que 0.");
                }
            } catch (Exception e) {
                System.out.println("Error: Debes introducir un número entero.");
                sc.nextLine();
            }
        }

        return dimension;
    }

    /**
     * Rellena la matriz con múltiplos de 5 entre 10 y 100.
     * 
     * @param matriz la matriz a rellenar
     */
    public static void fillMatriz(int[][] matriz) {
        // Múltiplos de 5 entre 10 y 100: 10, 15, 20, ..., 100
        // Son 19 valores: desde 10 hasta 100, de 5 en 5

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                // Generar un múltiplo de 5 aleatorio entre 10 y 100
                // Número de múltiplos: (100-10)/5 + 1 = 19
                int randomIndex = (int) (Math.random() * 19);
                matriz[i][j] = 10 + (randomIndex * 5);
            }
        }
    }

    /**
     * Muestra la matriz por consola.
     * 
     * @param matriz la matriz a mostrar
     */
    public static void printMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%4d", matriz[i][j]);
                if (j < matriz[i].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Calcula la matriz traspuesta.
     * La traspuesta cambia filas por columnas.
     * Si la original es n×m, la traspuesta es m×n.
     * 
     * @param matriz la matriz original
     * @return la matriz traspuesta
     */
    public static int[][] getTranspose(int[][] matriz) {
        int n = matriz.length; // filas de la original
        int m = matriz[0].length; // columnas de la original

        // La traspuesta tiene dimensiones invertidas: m×n
        int[][] traspuesta = new int[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // El elemento (i,j) de la original pasa a (j,i) en la traspuesta
                traspuesta[j][i] = matriz[i][j];
            }
        }

        return traspuesta;
    }
}
