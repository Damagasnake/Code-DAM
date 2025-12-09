package com.dam.actividades;

import java.util.Scanner;

public class actividad9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un numero");
        int numPrimario = Integer.parseInt(sc.nextLine());
        sc.close();
    }

    public static int NumInv(int numPrimario) {
        int numInverso = 0;
        int res = 0;
        int digits = 0;
        while (numPrimario <= 10) {
            numPrimario /= 10;
            digits++;
        }
        while (digits > 0) {
            numInverso = (numPrimario % 10) * (digits * 10);
            digits--;
        }
        numInverso = (numPrimario % 10) * (digits * 10);

        return numInverso;
    }
}

// Lo primero será minimizar el ejemplo
/*
 * NUM Original
 * 7894
 * NUM Inverso
 * 4987
 * 
 */