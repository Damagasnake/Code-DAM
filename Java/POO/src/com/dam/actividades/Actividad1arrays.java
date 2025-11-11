package com.dam.actividades;
import java.util.Scanner;
public class Actividad1arrays {
    static final int MEMARR = 10;
    public static void main(String[] args) {
        int [] valores = new int[MEMARR];
        fillArr(valores);
        mostrarArr(valores);
    }
    private static void fillArr(int[] valores){
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < valores.length; i++) {
                System.out.println("Introduce un valor para el array");
                valores[i] = Integer.parseInt(sc.nextLine());
            }
            sc.close();
    }
    private static void mostrarArr(int[] valores){
        for (int i = 0; i < valores.length; i++) {
            System.out.print(" Tu array contiene : " + valores[i]);
        }
    }
}
