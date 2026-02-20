package com.dam.pojo.classexa;

import java.util.Scanner;

public class classexa {
    private static String pedirNoVacio(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String s = sc.nextLine().trim();

            if (!s.isEmpty()) {
                return s; // válido
            }

            System.out.println("No puede estar vacío. Intenta otra vez.");
        }
    }
}
