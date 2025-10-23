package main.java.dam;
import java.util.Scanner;
public class ex07 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("introduce la base de la operacion");
        int base = Integer.parseInt(sc.nextLine());
        System.out.println("introduce el exponente de la operacion");
        int exponente = Integer.parseInt(sc.nextLine());
        sc.close();
        double res;
        if (exponente > 0) {
            res = Math.pow(base, exponente);
            System.out.println("El resultado es " + res);
        }
        else if(exponente == 0){
            System.out.println("el resultado es 1");
        }
        else{
            res = 1.0 / Math.pow(base, -exponente);
            System.out.println("El resultado es " + res);
        }
    }
}
