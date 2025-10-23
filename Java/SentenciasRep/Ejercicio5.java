import java.util.Scanner;
public class Ejercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Intrduce un numero real y un entero");
        double base = Double.parseDouble(sc.nextLine());
        int exponente = Integer.parseInt(sc.nextLine());
        double res = 1;
        sc.close();
        while (exponente > 0)
        {
            res = res * base;
            exponente--;
        }
        System.out.println(res);
    }
}
