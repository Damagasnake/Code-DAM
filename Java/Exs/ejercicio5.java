package Exs;
import java.util.Scanner;
public class ejercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce tu num: ");
        double precioIni = Double.parseDouble(sc.nextLine());
        System.out.print("Introduce tu descuento (Sin %): ");
        int descuentopr = sc.nextInt();
        sc.close();
        double descuento = (precioIni * descuentopr) / 100;
        System.out.println("El precio a pagar es: " + (precioIni - descuento));
    }
}
