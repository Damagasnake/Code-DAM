package com.dam;

import com.dam.pojo.CampoFutbol;
import java.util.Scanner;

public class CompararCamposF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce el nombre de tu campo 2");
        String NombreC1 = sc.nextLine();

        System.out.println("Introduce el largo de tu campo 1");
        int largoC1 = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el ancho de tu campo 1");
        int anchoC1 = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce la capacidad de tu campo 1");
        int capacidadC1 = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el nombre de tu campo 2");
        String NombreC2 = sc.nextLine();

        System.out.println("Introduce el largo de tu campo 2");
        int largoC2 = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el ancho de tu campo 2");
        int anchoC2 = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce la capacidad de tu campo 2");
        int capacidadC2 = Integer.parseInt(sc.nextLine());
        sc.close();
        CampoFutbol campoFutbol1 = new CampoFutbol(largoC1, anchoC1, capacidadC1, NombreC1);
        CampoFutbol campoFutbol2 = new CampoFutbol(largoC2, anchoC2, capacidadC2, NombreC2);

        System.out.println("El area de tu campo 1 es " + campoFutbol1.areaCampos());
        System.out.println("El area de tu campo 1 es " + campoFutbol2.areaCampos());
        System.out.println(campoFutbol1.toString());
        System.out.println(campoFutbol2.toString());
    }
}
