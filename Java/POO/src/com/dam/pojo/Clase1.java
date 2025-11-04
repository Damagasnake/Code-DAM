package com.dam.pojo;

public class Clase1 {
    private double valor = 9.8;
    private int x = 7;

    public void imprimir(double valor, int x) {
        System.out.println(valor + " " + this.x);
    }

    @Override
    public String toString() {
        return "Clase1 [valor=" + valor + ", x=" + x + "]";
    }
}