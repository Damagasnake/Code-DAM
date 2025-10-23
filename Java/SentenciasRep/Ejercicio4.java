import java.util.Scanner;
public class Ejercicio4 {
    public static void main(String[] args) {
        int numero;
        int sumacumulada = 0;
        int fueraIntervalo = 0;
        int intervalo = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce dos numeros enteros");
        int LimitInf = Integer.parseInt(sc.nextLine());
        int LimitSup = Integer.parseInt(sc.nextLine());
        while (LimitSup < LimitInf)
            {
                System.out.println("Introduce los limites correctamente");
                LimitInf = Integer.parseInt(sc.nextLine());
                LimitSup = Integer.parseInt(sc.nextLine());
            }
        
        do {
            System.out.println("Introduce un numero");
            numero = Integer.parseInt(sc.nextLine());
            if (numero >= LimitInf && numero <= LimitSup) {
                sumacumulada += numero;    
            }
            if(numero < LimitInf || numero > LimitSup)
                fueraIntervalo++;
            if (numero == LimitInf || numero == LimitSup) {
                intervalo++;
            }
        } while (numero != 0);
        System.out.println("Suma Intervalo = " + sumacumulada);
        System.out.println("Numeros que coinciden = " + intervalo);
        System.out.println("Fuera de intervalo = "  + fueraIntervalo);
        sc.close();
    }
}
