package com.dam.actividades;
import java.util.Scanner;
public class ejercicio5stringsdamaga {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la String a la que quitar los espacios");
        String palabra = sc.nextLine();
        System.out.println(palabra.replace(" ", ""));
        sc.close();
    }
}
