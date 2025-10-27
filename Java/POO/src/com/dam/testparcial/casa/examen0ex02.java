package com.dam.testparcial.casa;
import java.util.Scanner;
public class examen0ex02 {
    public static void main(String[] args) {
        int i = 0;
        int mult = 0;
        int div = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce cantidad de numeros a evaluar");
        int cant = Integer.parseInt(sc.nextLine());
        while (i < cant) {
            System.out.println("introduce un numero");
            int num = Integer.parseInt(sc.nextLine());
            if(num % 7 == 0)
                mult++;
            if(150 % num == 0)
                div++;
            i++;
        }
        sc.close();
        System.out.println(div);
        System.out.println(mult);
    }
}
