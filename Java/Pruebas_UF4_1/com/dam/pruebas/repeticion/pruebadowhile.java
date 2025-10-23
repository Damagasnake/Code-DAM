import java.util.Scanner;
import java.util.Random;;
public class pruebadowhile {
    public static void main(String[] args) {
        Random rd = new Random();
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce un num: ");
        int askednum = Integer.parseInt(sc.nextLine());
        sc.close();
        int compare;
        
        do {
            compare = rd.nextInt(0,askednum);
        } while (compare %2 != 0);
        System.out.println(compare);
    }
}