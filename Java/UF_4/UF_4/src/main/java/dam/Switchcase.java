package main.java.dam;
import java.util.Scanner;
public class Switchcase {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce un num del 1-5");
        int num = Integer.parseInt(sc.nextLine());
        sc.close();
        if (num == 1) {
            System.out.println("*");
        }
        else if (num == 2) {
            System.out.println("**");
        }
        else if (num == 3) {
            System.out.println("***");
        }
        else if (num == 4) {
            System.out.println("****");
        }
        else{
            System.out.println("*****");
        }
        switch (num) {
            case 1:
                System.out.println("*");
                break;
        
            case 2:
                System.out.println("**");
                break;
            case 3:
                System.out.println("***");
                break;
            case 4:
                System.out.println("****");
                break;
            case 5:
                System.out.println("*****");
                break;
            default:
            System.out.println("Mentecato");
        }
    }
}
