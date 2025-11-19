package com.dam.actividades;
/**
 * Programa para rellenar un Array de 10 números enteros.
 * @author Stiv Brandon Gaviria Ramos
 * @version 1.0
 **/
import java.util.Scanner;

/*
 * Programa Java que guarda en un array 10 números enteros que se leen por teclado.
 * A continuación se recorre el array y calcula cuántos números son positivos, cuántos negativos y cuántos ceros.
 */
public class Ejercicio2 {
	static final int VALOR = 10;//Declaramos la constante
	public static void main(String[] args) {
		
		
		int numeros[] = new int [VALOR];//Declarar el objeto de Array
		
		rellenoArray(numeros);//Creamos el método para rellenar el Arrays
		identificadorSignoArray(numeros);//Creamos eñ método para identificar y contar los positivos, negativos, y ceros insertados por el usuario
	}
/**
 * Inicializa el método para identificar y contar los positivos,
 * negativos, y ceros insertados por el usuario
 * @param numeros
 */
	private static void identificadorSignoArray(int[] numeros) { //Inicializamos el método para identificar y contar los positivos, negativos, y ceros insertados por el usuario
		int pos = 0;//Creamos e inicializamos las variables en valor cero
		int neg = 0;
		int cero = 0;
		
		for (int j = 0; j < VALOR; j++) {//Recorremos el array para poder identificar y contar los valores
			if(numeros[j] > 0) {
				pos++;
			}else if  (numeros[j] < 0) {
				neg++;
			}else {
				cero++;
			}
			
		}
		System.out.println("Los números positivos son: " + pos + ", negativos: " + neg + " y los que son cero: " + cero);//Imprimimos los resultados 	
		
	}
/**
 * Inicializamos el método para rellenar el Array con los números
 * introducidos por el usuario
 * @param numeros
 */
	private static void rellenoArray(int[] numeros) { //Inicializamos el método para rellenar el Array con los números introducidos por el usuario
		Scanner sc = new Scanner(System.in);//Declaramos el objeto Scanner
		
		for (int i = 0; i < VALOR; i++) {//Recorremos el Array mientras se va rellenando con los números introducidos por el usuario
			System.out.println("Introduce el " + (i+1) + "º número entero");
			numeros[i] = Integer.parseInt(sc.nextLine());
			
		}
		sc.close();//cerramos el Scanner
	}

}
