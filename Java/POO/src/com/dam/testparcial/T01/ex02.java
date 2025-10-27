package com.dam.testparcial.T01;
import java.util.Scanner;
import java.util.Random;
public class ex02 {
    public static void main(String[] args) {
        int nGen = 0;
        int check;
        Random rd = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce 2 numeros enteros");
        int par = 0;
        int impar = 0;
        int n1 = sc.nextInt();
        int n2 = sc.nextInt();
        sc.nextLine();
        sc.close();
        while (nGen < 10) {
            check = rd.nextInt(n1, n2 + 1);
            if(check % 2 == 0)
                par++;
            else if(check % 2 != 0)
                impar++;
            nGen++;
        }
        System.out.println( "cantidad pares " + par);
        System.out.println("cantidad impar " + impar);
    }
}