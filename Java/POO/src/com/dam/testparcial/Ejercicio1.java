package com.dam.testparcial;

public class Ejercicio1 {
    static final int Saltos = 7;
    static final int Terminos = 24;

    public static void main(String[] args) {
        int numini = 17;
        int i = 0;
        while (i <= Terminos) {
            System.out.println(numini);
            numini = numini + Saltos;
            i++;
        }
    }
}
