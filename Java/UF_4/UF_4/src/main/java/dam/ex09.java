package main.java.dam;
import java.util.Scanner;
public class ex09 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce cuantos alumnos van a la excursion");
        int alumnos = Integer.parseInt(sc.nextLine());
        int totalPagar;
        double costoPorAlumno;
        sc.close();
        if (alumnos >= 100)
        {
            totalPagar = alumnos * 65;
            costoPorAlumno = 65;
        }
        else if (alumnos >= 50)
        {
            totalPagar = alumnos * 70;
            costoPorAlumno = 70;
        }
        else if (alumnos >= 30)
        {
            totalPagar = alumnos * 95;
            costoPorAlumno = 95;
        }
        else
        {
            totalPagar = 4000;
            costoPorAlumno = 4000.0 / alumnos;
        }
        System.out.println("Total a pagar a la compañía = " + totalPagar + " euros");
        System.out.println("Costo por alumno = " + costoPorAlumno + " euros");
    }
}
