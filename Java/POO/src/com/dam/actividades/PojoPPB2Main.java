package com.dam.actividades;

import java.util.Scanner;
import com.dam.pojo.PojoPPB2;

public class PojoPPB2Main {
    private static final int NDEPORTES = 8;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        PojoPPB2[] metricas = Metricas();
    }

    public static PojoPPB2[] Metricas() {

        PojoPPB2[] metricas = new PojoPPB2[NDEPORTES];
        for (int i = 0; i < NDEPORTES; i++) {

            String disciplinasCorr[] = { "Carrera", "Bicicleta", "Natación" };
            String disciplina = "";
            boolean valido = false;
            while (!valido) {
                System.out.println(
                        "Introduce la disciplina a registrar (la actividad tiene que ser Carrera, Bicicleta o Natación.)");
                disciplina = sc.nextLine();
                for (String discip : disciplinasCorr) {
                    if (disciplina.equalsIgnoreCase(discip)) {
                        valido = true;
                        break; // no se cortar el bucle sin el break pero aqui ha comprobado que mi String sea uno valido con el foreach
                    }
                }
            }
            System.out.println("Introduce la distancia recorrida");
            int num = 0;
            boolean distVal = false;
            while (!distVal) {
                try {
                System.out.println("Introduce un num entre 60 y 1000000");
                Double distancia = Double.parseDouble(sc.nextLine());
                if (num >= 60 && num <= 1000000) {
                    distVal = true;
                }
                else{
                    throw new Exception("Espabila brother");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            }
           
            System.out.println("Introduce el tiempo empleado");
            Double tiempo = Double.parseDouble(sc.nextLine());
            System.out.println("Introduce la fecha con el siguiente formato (27/01/2022 08:05)");
            String fecha = sc.nextLine();
            metricas[i] = new PojoPPB2(disciplina, distancia, tiempo, fecha);
        }
        return metricas;
    }
}
