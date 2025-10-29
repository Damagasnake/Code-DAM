package com.dam.ejercicio1;

import java.util.Scanner;

public class Ejercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int reps = 0;
        System.out.println("Introduce un numero entero");
        int numBase = Integer.parseInt(sc.nextLine());
        int show = numBase + 1;
        while (reps < 10) {
            if (show % 5 == 0 && show % 2 != 0) {
                System.out.print(show + " ");
                reps++;
            }
            show++;
        }
        sc.close();
    }
}
