package com.dam.testparcial;
import java.util.Scanner;
public class Ejercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce la cantidad de números que recibe el programa: ");
        int numrecibir = Integer.parseInt(sc.nextLine());

        int recibidos = 1;
        while (numrecibir >= recibidos) {
            System.out.println("introduce tu num");
            int num = Integer.parseInt(sc.nextLine());
            if(num % 7 == 0){
                System.out.println("Este num lo cumple " + num);
            }
            recibidos++;
        }
        sc.close();        
    }
    }
