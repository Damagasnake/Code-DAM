package com.dam.actividades;

import java.util.Scanner;
import com.dam.pojo.PojoPPB2;

public class PojoPPB2Main {
    private static final int NDEPORTES = 8;
    private static Scanner sc;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            sc = scanner;
            PojoPPB2[] metricas = Metricas();
            System.out.println("\n=== MÉTRICAS REGISTRADAS ===");
            for (int i = 0; i < metricas.length; i++) {
                System.out.println("\nDeporte " + (i + 1) + ":");
                System.out.println(metricas[i].toString());
            }
        }
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
                        break; // no se cortar el bucle sin el break pero aqui ha comprobado que mi String sea
                               // uno valido con el foreach
                               // se me ocurrio hacer split pero estoy en el hospital :)
                    }
                }
            }

            System.out.println("Introduce la distancia recorrida");
            Double distancia = 0.0;
            boolean distVal = false;
            while (!distVal) {
                try {
                    System.out.println("Introduce un número entre 60 y 1000000:");
                    distancia = Double.parseDouble(sc.nextLine());
                    if (distancia >= 60 && distancia <= 1000000) {
                        distVal = true;
                    } else {
                        throw new Exception("Error: La distancia debe estar entre 60 y 1000000");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debes introducir un número válido");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            Double tiempo = 0.0;
            boolean validData = false;
            while (!validData) {
                try {
                    System.out.println("Introduce el tiempo empleado");
                    System.out.println("Introduce un valor entre 0.5 y 2880:");
                    tiempo = Double.parseDouble(sc.nextLine());
                    if (tiempo >= 0.5 && tiempo <= 2880) {
                        validData = true;
                    } else {
                        throw new Exception("Error: El tiempo debe estar entre 0.5 y 2880");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: Debes introducir un número válido");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            String fecha = "";
            boolean fechaValida = false;
            while (!fechaValida) {
                try {
                    System.out.println("Introduce la fecha con el siguiente formato (27/01/2022 08:05):");
                    fecha = sc.nextLine();
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
                    sdf.setLenient(false);
                    sdf.parse(fecha);

                    fechaValida = true;
                } catch (java.text.ParseException e) {
                    System.out.println(
                            "Error: El formato de fecha es incorrecto. Usa el formato dd/MM/yyyy HH:mm (ejemplo: 27/01/2022 08:05)");
                }
            }

            metricas[i] = new PojoPPB2(disciplina, distancia, tiempo, fecha);
        }
        return metricas;
    }
}
