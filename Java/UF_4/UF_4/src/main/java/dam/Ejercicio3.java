package main.java.dam;
import java.util.Scanner;
public class Ejercicio3 {
    private static final int HoraPromedio = 16;
    private static final int Extra = 20;
    private static final int jornada = 40;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("introduce n horas trabajadas");
        int horas = Integer.parseInt(sc.nextLine());
        sc.close();
        int salario = 0;
        int horasextras = horas - 40;
    
        if (horas <= 40) {
            salario = horas * HoraPromedio;
        }
        else{
            salario = ((horasextras * Extra) + (jornada * HoraPromedio));
        }
        System.out.println(salario);
    }
}
