package com.dam.actividades;

import java.util.Scanner;
import com.dam.pojo.Estacion;

public class Estacionexe {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Estacion Estaciones[] = preguntas();
        sc.close();
        showinfo(Estaciones);
    }

    private static Estacion[] preguntas() {
        System.out.println("Numero de estaciones a introducir");
        int n = Integer.parseInt(sc.nextLine());
        Estacion[] Estaciones = new Estacion[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Intoduce el nombre de tu estación");
            String Nombre = sc.nextLine();
            System.out.println("Introduce la provincia de tu estación");
            String Provincia = sc.nextLine();
            System.out.println("Introduce el estado de la estacion (Abierto/cerrado)");
            String Estado = sc.nextLine();

            String Pistas = "0";
            String Km = "0/1";
            String remontes = "0";

            if (Estado.equalsIgnoreCase("Abierto")) {
                System.out.println("Introduce las pistas de la estacion");
                Pistas = sc.nextLine();
                System.out.println("Introduce los km de tu estacion");
                Km = sc.nextLine();
                System.out.println("Introduce los remontes de tu estacion");
                remontes = sc.nextLine();
            }

            Estaciones[i] = new Estacion(Nombre, Provincia, Estado, Pistas, remontes, Km);
        }
        return Estaciones;
    }

    private static boolean isopen(Estacion estacion) {
        return estacion.getEstado().equalsIgnoreCase("Abierto");
    }

    private static void showinfo(Estacion[] estaciones) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < estaciones.length; i++) {
            if (isopen(estaciones[i])) {
                sb.append(estaciones[i].toString());
                sb.append("\nPorcentaje KM esquiables: ");
                sb.append(estaciones[i].kmesq());
                sb.append("%\n\n");
                sb.append(estaciones[i].kmab());
                sb.append("%\n\n");
            } else {
                sb.append(estaciones[i].toString());
            }
        }
        System.out.println(sb.toString());
    }
}