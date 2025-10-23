package com.dam.practicas;

import java.util.Scanner;

/*(while o for) Escribir un programa que imprima todos los números pares entre dos
números que se le pidan al usuario.*/

public class Practica3 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("escriba 2 numeros");
		int n1 = Integer.parseInt(sc.nextLine());
		int n2 = Integer.parseInt(sc.nextLine());
		
		sc.close();
		
		if (n1 < n2) {
			
			int tmp = n1;
			n1 = n2;
			n2 = tmp;
		}//me aseguro de que n1 sea el mayor
		
		
		for(int i = n2; i <= n1; i++ ) {//mucho muy importante empezar por el menor 
			
			if (i % 2 == 0) {
				System.out.println(i);
			}
			
		}
		

	}

}
