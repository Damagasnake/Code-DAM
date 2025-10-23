import java.util.Scanner;
public class Ejercicio6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un numero");
        int numero = Integer.parseInt(sc.nextLine());
        sc.close();
        
        while (numero > 1) {
            boolean primo = true;
            for (int i = 2; i < numero; i++) {
                if (numero % i == 0) {
                    primo = false;
                }
            }
            if (primo) {
                System.out.println(numero + " es primo");
            }
            numero--;
        }
    }
}
