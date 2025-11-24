package com.dam.actividades;

import java.util.Scanner;

import com.dam.pojo.PrepExa;

public class PrepExamain {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        PrepExa[] satelites = Satelites();
        mostrarSatelites(satelites);
        mostrarSatelitesOrbitando(satelites);
        masAntiguoOrbitando(satelites);
    }

    public static PrepExa[] Satelites() {
        System.out.println("Cuantos satelites vas a registrar?");
        int num = Integer.parseInt(sc.nextLine());
        PrepExa[] satelites = new PrepExa[num];
        for (int i = 0; i < num; i++) {
            System.out.println("Introduce el nombre del satelite");
            String name = sc.nextLine();
            System.out.println("Agencia supervisora");
            String agencia = sc.nextLine();
            System.out.println("Estado del satelite (Orbitando/Caido)");
            String estado = sc.nextLine();
            System.out.println("Introduce (año de lanzamiento - año de caida si ha caido)");
            String year = sc.nextLine();
            satelites[i] = new PrepExa(name, agencia, estado, year);
        }
        return satelites;
    }

    public static void mostrarSatelites(PrepExa[] satelites) {
        for (int i = 0; i < satelites.length; i++) {
            System.out.println(satelites[i].toString());
        }
    }

    public static void mostrarSatelitesOrbitando(PrepExa[] satelites) {
        for (int i = 0; i < satelites.length; i++) {
            if (satelites[i].getEstado().equals("Orbitando")) {
                System.out.println(satelites[i].toString());
            }
        }
    }

    public static void masAntiguoOrbitando(PrepExa[] satelites) {
        int antiguedad = -1;
        PrepExa masAntiguo = null;
        for (int i = 0; i < satelites.length; i++) {
            if (satelites[i].getEstado().equals("Orbitando")) {
                int ant = satelites[i].calcAntiguedad();
                if (ant > antiguedad) {
                    antiguedad = ant;
                    masAntiguo = satelites[i];
                }
            }
        }
        System.out.println("Satelite mas antiguo: " + masAntiguo.toString());
    }
}
