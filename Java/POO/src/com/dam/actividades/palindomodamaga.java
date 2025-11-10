package com.dam.actividades;
import java.util.Scanner;

public class palindomodamaga {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una palabra");
        String palabra = sc.nextLine();
        String palabraInvertida = new StringBuilder(palabra).reverse().toString();
        if (palabra.equals(palabraInvertida)) {
            System.out.println("La palabra es palindroma");
        } else {
            System.out.println("La palabra no es palindroma");
        }
        sc.close();
    }
}
