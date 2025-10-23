package Exs;
import java.util.Scanner;
public class ejercicio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu num: ");
        int res = sc.nextInt();
        System.out.println((res % 2 == 0) ? "Par" : "Impar");
    sc.close();
    }
}
