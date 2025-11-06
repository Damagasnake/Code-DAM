package com.dam.actividades;
import java.util.Scanner;
public class ejercicio10 {
    static final int reps = 10;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int contador = 0;
        int more = 0;
        int less = 0;
        int same = 0;
        int numActual;
        System.out.println("Introduce un numero");
        numActual = Integer.parseInt(sc.nextLine());
        contador++;
        int compare;
        while (contador < reps) {
            compare = Integer.parseInt(sc.nextLine());
            contador++;
            if (numActual < compare) {
                more++;
            }
            else if(numActual > compare){
                less++;
            }
            else{
                same++;
            }
            numActual = compare;
        }
        System.out.println("Numeros mayores que el anterior " + more);
        System.out.println("Numeros menores que el anterior " + less);
        System.out.println("Numeros iguales que el anterior " + same);
        sc.close();
    }
}