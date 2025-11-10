package com.dam.tests;

public class StringsBuilder {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hola");
        sb.append(" Mundo");
        System.out.println(sb);
        sb.reverse();
        System.out.println(sb);
    }
}