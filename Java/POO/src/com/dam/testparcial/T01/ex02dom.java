package com.dam.testparcial.T01;
import java.util.Scanner;
public class ex02dom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int vuelta = 1; vuelta <= 5; vuelta++) {
            int i = 2;
            int cont = 0;
            System.out.println("Vuelta " + vuelta + " - introduce un num");
            int n1 = Integer.parseInt(sc.nextLine());
            while (i < n1) {
                if (n1 % i == 0) {
                    cont++;
                }
                i++;
            }
            System.out.println(cont);
        }

        sc.close();
    }
}