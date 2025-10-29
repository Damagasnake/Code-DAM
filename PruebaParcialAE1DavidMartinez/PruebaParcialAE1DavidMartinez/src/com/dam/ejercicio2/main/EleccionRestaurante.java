package com.dam.ejercicio2.main;
import com.dam.ejercicio2.pojo.OpcionRestaurante;
import java.util.Scanner;
public class EleccionRestaurante {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre del primer restaurante a valorar ");
        String nomRes1 = sc.nextLine();
        System.out.println("Introduce la distancia al primer restaurante ");
        double disRes1 = Double.parseDouble(sc.nextLine());
        System.out.println("introduce el identificador de trafico (0 - poco | 1 - medio | 2 - lento)");
        int trafRes1 = Integer.parseInt(sc.nextLine());


        System.out.println("Introduce el nombre del segundo restaurante a valorar ");
        String nomRes2 = sc.nextLine();
        System.out.println("Introduce la distancia al segundo restaurante ");
        double disRes2 = Double.parseDouble(sc.nextLine());
        System.out.println("introduce el identificador de trafico (0 - poco | 1 - medio | 2 - lento)");
        int trafRes2 = Integer.parseInt(sc.nextLine());


        System.out.println("Introduce el nombre del tercer restaurante a valorar ");
        String nomRes3 = sc.nextLine();
        System.out.println("Introduce la distancia al tercer restaurante ");
        double disRes3 = Double.parseDouble(sc.nextLine());
        System.out.println("introduce el identificador de trafico (0 - poco | 1 - medio | 2 - lento)");
        int trafRes3 = Integer.parseInt(sc.nextLine());
        sc.close();

        OpcionRestaurante res1 = new OpcionRestaurante(nomRes1, disRes1, trafRes1);

        OpcionRestaurante res2 = new OpcionRestaurante(nomRes2, disRes2, trafRes2);

        OpcionRestaurante res3 = new OpcionRestaurante(nomRes3, disRes3, trafRes3);

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);

        if(res1.calcularTiempo() < res2.calcularTiempo() && res1.calcularTiempo() < res3.calcularTiempo()){
            System.out.println("El restaurante mas cercano es " + res1.getNombre());
        }
        if(res2.calcularTiempo() < res1.calcularTiempo() && res2.calcularTiempo() < res3.calcularTiempo()){
            System.out.println("El restaurante mas cercano es " + res2.getNombre());
        }
        if(res3.calcularTiempo() < res1.calcularTiempo() && res3.calcularTiempo() < res2.calcularTiempo()){
            System.out.println("El restaurante mas cercano es " + res3.getNombre());
        }
    }
}
