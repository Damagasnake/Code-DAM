package com.dam.actividades;

import java.util.Scanner;

public class Actividad4arrays {
    static final int MEMARR = 10;

    public static void main(String[] args) {
        int[] valores = new int[MEMARR];
        fillArr(valores);
        calcMediaArr(valores);
    }

    private static void fillArr(int[] valores) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < valores.length; i++) {
            System.out.println("Introduce un valor para el array");
            valores[i] = Integer.parseInt(sc.nextLine());
        }
        sc.close();
    }

    private static void calcMediaArr(int[] valores){
        double suma = 0;
        int pares = 0;
        for(int i = 0; i < valores.length;i++){
            if (i % 2 == 0) {
                suma += valores[i];
                pares++;
            }
        }
        System.out.println("La media de los pares = " + suma / pares);
}
}