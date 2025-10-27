package com.dam.testparcial.casa;
import java.util.Scanner;
import com.dam.pojo.examen0ex03pojo;
public class examen0ex03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el nombre de tu primer equipo");
        String nombreeq1 = sc.nextLine();
        System.out.println("Introduce el nombre de la ciudad de tu primer equipo");
        String nombreciudadeq1 = sc.nextLine();
        System.out.println("Introduce la cantidad de partidos ganados por " + nombreeq1);
        int partidosG1 = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce la cantidad de partidos empatados por " + nombreeq1);
        int partidosE1 = Integer.parseInt(sc.nextLine());


        System.out.println("Introduce el nombre de tu segundo equipo");
        String nombreeq2 = sc.nextLine();
        System.out.println("Introduce el nombre de la ciudad de tu segundo equipo");
        String nombreciudadeq2 = sc.nextLine();
        System.out.println("Introduce la cantidad de partidos ganados por " + nombreeq2);
        int partidosG2 = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce la cantidad de partidos empatados por " + nombreeq2);
        int partidosE2 = Integer.parseInt(sc.nextLine());

        examen0ex03pojo eq1 = new examen0ex03pojo(nombreeq1, nombreciudadeq1, partidosE1, partidosG1, partidosE1);
        examen0ex03pojo eq2 = new examen0ex03pojo(nombreeq2, nombreciudadeq2, partidosE2, partidosG2, partidosE2);

        System.out.println(eq1);
        System.out.println(eq2);
        
        if (eq1.puntos() < eq2.puntos()) {
            System.out.println("gano el equipo " + eq2.getName());
        }
        if (eq2.puntos() < eq1.puntos()) {
            System.out.println("gano el equipo " + eq1.getName());
        }
        sc.close();
    }
}
