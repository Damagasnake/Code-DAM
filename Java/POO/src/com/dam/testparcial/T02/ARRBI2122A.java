package com.dam.testparcial.T02;
import java.util.Scanner;
public class ARRBI2122A {
    static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        int[][] ArrayB = CrearArray();
        fillArrayB(ArrayB);
    }
    public static int[][] CrearArray(){
        System.out.println("Introduce el valor n (columnas)");
        int n = Integer.parseInt(sc.nextLine());

        int[][] arrayB = new int[n][n];
        return arrayB;
    }
    private static void fillArrayB(int[][] arrayB){
        int Valor;
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[i].length; j++) {
                System.out.println("Introduce un num para rellenar el array");
                Valor = Integer.parseInt(sc.nextLine());
                arrayB[i][j] = Valor;
            }
        }
    }
    private static void sumaDiag(int[][] arrayB){
        int sumaInf = 0;
        int sumaSup = 0;
        for (int i = 0; i < arrayB.length; i++) {
            for (int j = 0; j < arrayB[i].length; j++) {
                if(i < j){
                    sumaSup += arrayB[i][j];
                }else if(i > j){
                    sumaInf = sumaInf += arrayB[i][j];
                }
            }
        }
    }
}
