/*(for) Crea una aplicación que pida un número y calcule su factorial (El factorial de
un número es el producto de todos los enteros entre 1 y el propio número y se
representa por el número seguido de un signo de exclamación. Por ejemplo 5! =
1x2x3x4x5=120)*/

public class Practica1 {
	
	static final int FRAL_D = 5;

	public static void main(String[] args) {
		
		int factorial = 1;
		
		for (int f = 1; f <= FRAL_D; f++) {
			
			factorial = factorial * f;
			System.out.println(factorial);
			
		}
		
		System.out.println("El factorial de " + FRAL_D + " es: " + factorial);

	}

}
