package main.java.dam;
import java.util.Scanner;
public class ex06 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un entero");
        int n1 = Integer.parseInt(sc.nextLine());
        System.out.println("Introduce un segundo entero para dividir");
        int n2 = Integer.parseInt(sc.nextLine());
        sc.close();
        if (n2 == 0) {
            System.out.println("No puedes dividir entre 0");
        }
        else{
            int res = (n1 / n2);
            System.out.println("El resultado es: " + res);
        }
    }
}
