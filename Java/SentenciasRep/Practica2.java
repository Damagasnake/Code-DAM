package com.dam.practicas;

import java.util.Scanner;

/*(do-while) Crea un programa que pida números hasta que se introduzca un cero.
Debe imprimir la suma y la media de todos los números introducidos.*/

public class Practica2 {

	public static void main(String[] args) {
		
		int s = 0;//suma
		double m = 0;//media
		int n;//numero
		int cont = 0;
		
		Scanner sc = new Scanner(System.in);
		
		do {
			
			System.out.println("escribe un número");
			n = Integer.parseInt(sc.nextLine());
			
			if (n!=0) {
			s = s + n;
			cont++;
			m = (double) s / cont;
			}
			
		}while (n!=0);
		
		System.out.println("la suma de los numeros introducidos es: " + s + " y la media de los numeros introducidos es: " + m);
		
		sc.close();
	}

}
