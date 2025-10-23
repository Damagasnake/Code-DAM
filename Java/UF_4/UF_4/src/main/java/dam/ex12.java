package main.java.dam;
import java.util.Scanner;

public class ex12 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un número del 1 al 12:");
        int mes = Integer.parseInt(sc.nextLine());
        sc.close();
        
        if(mes < 1 || mes > 12){
            System.out.println("ERROR: Número inválido");
        } else {
            switch (mes) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    System.out.println("El mes tiene 31 días");
                    break;
                case 4: case 6: case 9: case 11:
                    System.out.println("El mes tiene 30 días");
                    break;
                case 2:
                    System.out.println("El mes tiene 28 días");
                    break;
            }
        }
    }
}
