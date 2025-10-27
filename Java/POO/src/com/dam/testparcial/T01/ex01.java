package com.dam.testparcial.T01;
public class ex01 {
    static final int TERMINOS = 18;
    public static void main(String[] args) {
        int i = 1;
        int n = 94;
        while (i <= TERMINOS) {
            System.out.println(n);
            n -= 6;
            i++;
        }
    }
}