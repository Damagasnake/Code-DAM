package com.dam.testparcial.T02;

import java.util.Random;
import java.util.Scanner;

public class ARRBI2021B {
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int[][] arrayC = createarrayC();
        sc.close();
        fillArrayC(arrayC);
        showArr(arrayC);
        sum(arrayC, arrayC.length);
    }

    private static int[][] createarrayC() {
        System.out.println("Introduce el valor de n(filas)");
        int n = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el valor de m(cols)");
        int m = Integer.parseInt(sc.nextLine());

        int[][] arrayC = new int[n][m];
        return arrayC;
    }

    private static void fillArrayC(int[][] arrayC) {
        Random rd = new Random();
        for (int i = 0; i < arrayC.length; i++) {
            for (int j = 0; j < arrayC[i].length; j++) {
                int n;
                do {
                    n = rd.nextInt(5, 200);
                } while (n % 5 != 0 || n % 2 == 0);
                arrayC[i][j] = n;
            }
        }
    }

    private static void showArr(int[][] arrayC) {
        for (int i = 0; i < arrayC.length; i++) {
            for (int j = 0; j < arrayC[i].length; j++) {
                System.out.print(arrayC[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static void sum(int[][] arrayC, int n) {
        int menosveinticinco = 0;

        for (int j = 0; j < arrayC[0].length; j++) {
            int sumCols = 0;

            for (int i = 0; i < arrayC.length; i++) {
                sumCols += arrayC[i][j];
            }

            if (sumCols < n * 25) {
                menosveinticinco++;
            }
        }

        System.out.println("Columnas que suman menos de " + (n * 25) + ": " + menosveinticinco);
    }
}
//        Col 0   Col 1   Col 2
//      ┌───────┬───────┬───────┐
//i=0   │   A   │   B   │   C   │  ↓
//      ├───────┼───────┼───────┤  ↓ arriba a abajo
//i=1   │   D   │   E   │   F   │  ↓ (arrayC.length = 3)
//      ├───────┼───────┼───────┤  ↓
//i=2   │   G   │   H   │   I   │  ↓
//      └───────┴───────┴───────┘
//        →→→→→→→→→→→→→→→→→→→→→
//        izquierda a derecha
//        (arrayC[0].length = 3)