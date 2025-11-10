package com.dam.tests;

public class Strings {
    public static void main(String[] args) {
        String cadena = "hello w.or.l.d";
        System.out.println("La longitud de cadena es " + cadena.length() + " Esta vacía? " + "\n" + cadena.isEmpty() + "\n" + " Son solo espacios? " + "\n" + cadena.isBlank());

        int pospunto = cadena.indexOf('.');
        System.out.println(pospunto);
    }
}
