package com.dam.actividades;

import java.util.Scanner;

public class arraysDamaga {
    static final int MEMARR = 10;

    public static void main(String[] args) {
        int[] valores = new int[MEMARR];
        fillArr(valores);
        clasArr(valores);
    }

    private static void fillArr(int[] valores) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < valores.length; i++) {
            System.out.println("Introduce un valor para el array");
            valores[i] = Integer.parseInt(sc.nextLine());
        }
        sc.close();
    }

    private static void clasArr(int[] valores) {
        int contpos = 0;
        int contneg = 0;
        int ceros = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i] > 0) {
                contpos++;
            } else if (valores[i] < 0) {
                contneg++;
            } else {
                ceros++;
            }
        }
        System.out.println("Cantidad de positivos " + contpos);
        System.out.println("Cantidad de negativos " + contneg);
        System.out.println("Cantidad de ceros " + ceros);
    }
}
