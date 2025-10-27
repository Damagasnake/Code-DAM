package com.dam.testparcial.T01;

public class ex01dom {
    static final int numini = 5;

    public static void main(String[] args) {
        int i = 0;
        int show = numini;
        while (i < 10) {
            if (show % 2 == 0 || show % 5 == 0) {
                System.out.println(show);
                i++;
            }
            show +=3;
        }
    }
}