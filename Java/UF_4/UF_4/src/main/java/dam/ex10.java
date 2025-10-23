package main.java.dam;
import java.util.Scanner;
public class ex10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Numero sacado al lanzar el dado");
        int cara = Integer.parseInt(sc.nextLine());
        sc.close();
        
        if(cara < 1 || cara > 6){
            System.out.println("ERROR: Número inválido");
        } else {
            switch (cara) {
                case 1:
                    System.out.println("Cara opuesta = 6");
                    break;
                case 2:
                    System.out.println("Cara opuesta = 5");
                    break;
                case 3:
                    System.out.println("Cara opuesta = 4");
                    break;
                case 4:
                    System.out.println("Cara opuesta = 3");
                    break;
                case 5:
                    System.out.println("Cara opuesta = 2");
                    break;
                case 6:
                    System.out.println("Cara opuesta = 1");
                    break;
            }
        }
    }
}
