package com.dam.actividades;

import java.util.Scanner;

public class ejercicio2repaso {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int valoresIngresados = 0;
        int num;
        int neg = 0;
        int pos = 0;
        int div = 0;
        int parSum = 0;
        System.out.println("introduce 10 valores");
        while (valoresIngresados < 10) {
            num = Integer.parseInt(sc.nextLine());
            if (num >= 0){
                pos++;
            } else {
                neg++;
            }
            if (num % 15 == 0) {
                div++;
            }
            if (num % 2 == 0) {
                parSum = parSum + num;                
            }
            valoresIngresados++;
        }
        sc.close();
        System.out.println("Cantidad negativos " + neg);
        System.out.println("Cantidad positivos " + pos);
        System.out.println("Cantidad mul 15 " + div);
        System.out.println("Suma pares " + parSum);
    }
}
