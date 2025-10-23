package main.java.dam;

import java.util.Scanner;

public class ex08 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el día:");
        int dia = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el mes:");
        int mes = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce el año:");
        int ano = Integer.parseInt(sc.nextLine());
        sc.close();
        
        boolean fechaValida = false;
        if (mes >= 1 && mes <= 12) {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                if (dia >= 1 && dia <= 31) {
                    fechaValida = true;
                }
            } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia >= 1 && dia <= 30) {
                    fechaValida = true;
                }
            } else if (mes == 2) {
                boolean esBisiesto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
                if (esBisiesto) {
                    if (dia >= 1 && dia <= 29) {
                        fechaValida = true;
                    }
                } else {
                    if (dia >= 1 && dia <= 28) {
                        fechaValida = true;
                    }
                }
            }
        }
        if (fechaValida) {
            System.out.println("La fecha " + dia + "/" + mes + "/" + ano + "OK");
        } else {
            System.out.println("La fecha " + dia + "/" + mes + "/" + ano + "La fecha introducida no es valida");
        }
    }
}