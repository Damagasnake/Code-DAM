package main.java.dam;
import java.util.Scanner;
public class TestIF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un num != 0");
        int num = Integer.parseInt(sc.nextLine());
        sc.close();
        int resto = num % 2;
        if (resto == 0){
            System.out.println("Par");            
        }else{
            System.out.println("Impar");
        }
        if (num < 0) {
            System.out.println("Negativo");
        } else{
            System.out.println("Positivo");
        }
    }
}
