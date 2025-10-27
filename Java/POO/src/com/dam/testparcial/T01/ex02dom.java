package com.dam.testparcial.T01;
import java.util.Scanner;
public class ex02dom {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = 2;
        int cont = 0;
        System.out.println("introduce el primer num");
        int n1 = Integer.parseInt(sc.nextLine());
        while (i < n1) {
            if (n1 % i == 0) {
                cont++;
            }
            i++;
        }
        System.out.println(cont);
        cont = 0;
        i = 2;

        sc.close();
    }
}
