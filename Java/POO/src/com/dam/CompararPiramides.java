package com.dam;

import com.dam.pojo.Piramide;
import java.util.Scanner;

public class CompararPiramides {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el nombre de tu primera piramide: ");
        String NombreP1 = sc.nextLine();

        System.out.print("Introduce la base de tu primera piramide: ");
        double baseP1 = Double.parseDouble(sc.nextLine());

        System.out.print("Introduce la altura de tu primera piramide: ");
        double alturaP1 = Double.parseDouble(sc.nextLine());

        System.out.print("Introduce el nombre de tu segunda piramide: ");
        String NombreP2 = sc.nextLine();

        System.out.print("Introduce la base de tu segunda piramide: ");
        double baseP2 = Double.parseDouble(sc.nextLine());

        System.out.print("Introduce la altura de tu segunda piramide: ");
        double alturaP2 = Double.parseDouble(sc.nextLine());
        sc.close();
        Piramide Piramide1 = new Piramide(NombreP1, baseP1, alturaP1);
        Piramide Piramide2 = new Piramide(NombreP2, baseP2, alturaP2);

        System.out.println(Piramide1 + "y el volumen es " + Piramide1.CalculoVol());
        System.out.println(Piramide2 + "y el volumen es " + Piramide2.CalculoVol());
        if (Piramide1.CalculoVol() < Piramide2.CalculoVol()) {
            System.out.println("La Piramide " + NombreP2 + " es mayor");
        } else {
            System.out.println("Piramide " + NombreP1 + " es mayor");
        }
    }
}