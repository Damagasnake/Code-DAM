import java.util.Scanner;
public class pruebawhile {
    public static void main(String[] args) {
        int cero = 0;
        int pos = 0;
        int neg = 0;
        int numerosrecibidos = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("introduce num de iteraciones");
        int iteraciones = Integer.parseInt(sc.nextLine());

        while (numerosrecibidos < iteraciones) {
            System.out.println("Introduce un num");
            int num = sc.nextInt();
            if (num == 0){
                cero++;
            }
            else if (num < 0){
                neg++;
            }
            else{
                pos++;
            }
            numerosrecibidos++;
        }
        sc.close();
        System.out.println("Ceros: " + cero);
        System.out.println("Positivos: " + pos);
        System.out.println("Negativos: " + neg);        
   }
}
