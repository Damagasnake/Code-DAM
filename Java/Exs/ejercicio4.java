package Exs;
import java.util.Scanner;
public class ejercicio4 {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Introduce las horas: ");
   int horas = sc.nextInt();
   System.out.print("Introduce los minutos: ");
   int min = sc.nextInt();
   System.out.print("Introduce los segundos: ");
   int sec = sc.nextInt();
   sc.close();
   System.out.println("Total de seg = " + (horas * 3600 + min * 60 + sec));
}    
}
