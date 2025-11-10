package com.dam.actividades;

public class enunciadostrings1 {
    public static void main(String[] args) {
        int vocales = 0;
        String cadena = "Hola que tal";
        for(int i = 0; i < cadena.length();i++){
            switch (cadena.charAt(i)) {
                case 'a', 'e', 'i', 'o', 'u' :
                    vocales++;
                    break;
            
                default:
                    break;
            }
        }
        System.out.println(vocales);
    }
}
