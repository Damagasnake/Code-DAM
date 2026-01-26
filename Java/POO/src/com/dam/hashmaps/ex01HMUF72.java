package com.dam.hashmaps;

import java.util.HashMap;
import java.util.Scanner;

public class ex01HMUF72 {
    static final int NPALABRAS = 10;

    public static void main(String[] args) {
        HashMap<String,Integer> tablapalabras = palabrasM(NPALABRAS);
        fillMap(tablapalabras);
    }

    public static HashMap<String, Integer> palabrasM(int n) {
        HashMap<String, Integer> tablaPalabras = new HashMap<>();
        return tablaPalabras;
    }
    public static void fillMap(HashMap<String,Integer> tablaPalabras){
        Scanner sc = new Scanner(System.in);
        for (int n = 0; n < NPALABRAS; n++) {
            System.out.println("Introduce una palabra");
            String palabra = sc.nextLine();
            if (!tablaPalabras.containsKey(palabra)) {
                tablaPalabras.put(palabra, palabra.length());
            }else{
                n--;
                System.out.println("Introduce otra palabra");
            }
        }
    }
}
//  Clave       Valor
//  1           Luis
//---------------------- ENTRY