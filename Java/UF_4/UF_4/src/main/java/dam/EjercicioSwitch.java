package main.java.dam;
import java.util.Scanner;
public class EjercicioSwitch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la nota de tu examen: ");
        int nota = Integer.parseInt(sc.nextLine());
        sc.close();
        switch (nota) {
            case 0, 1, 2, 3, 4:
                System.out.println("Suspenso");
                break;
            case 5, 6:
                System.out.println("Bien");
                break;
            case 7, 8:
                System.out.println("Notable");
                break;
            case 9, 10:
                System.out.println("Sobresaliente");
                break;
    }
}
}