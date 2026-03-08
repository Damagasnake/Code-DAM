package com.dam.main;

import java.util.Arrays;
import java.util.Scanner;

import com.dam.pojos.Banio;
import com.dam.pojos.Casa;
import com.dam.pojos.Cocina;
import com.dam.pojos.Estancia;
import com.dam.pojos.Habitacion;
import com.dam.pojos.Hall;
import com.dam.pojos.Salon;

/*
 * En la clase ejecutable, en el método main se debe crear un objeto Casa y mostrarle al usuario un menú para:
-	Añadir una estancia
-	Mostrar datos de la casa junto con el total de metros cuadrados
-	Salir de la aplicación
Para cada estancia que se quiera añadir, se deberá solicitar si es HALL, COCINA, SALÓN, HABITACIÓN o BAÑO, y
en función de la respuesta se solicitarán los datos que correspondan.
 */
public class GestionArq {

	static Casa objCasa;
	static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);

		String direc = solicitarCadenaNoVacia("Introduce la dirección: ");

		objCasa = new Casa(direc);

		String opcion;

		do {
			System.out.println("\nIntroduce una de las siguiente opciones:");
			System.out.println("A para añadir una estancia a la casa");
			System.out.println("M para mostrar la información de la casa");
			System.out.println("S para salir del programa");
			opcion = sc.nextLine().toUpperCase();

			switch (opcion) {
				case "A":
					addEstancia();
					break;
				case "M":
					mostrarInfoCasa();
					break;
				case "S":
					System.out.println("\n** Terminando el programa **");
					break;
				default:
					System.out.println("ERROR: El valor introducido no es uno de los esperados");
					break;
			}
		} while (!opcion.equalsIgnoreCase("S"));

		sc.close();

	}

	private static void addEstancia() {
		String tipo = solicitarTipo();

		String nombre = solicitarCadenaNoVacia("Introduce el nombre de la estancia");
		double m2 = solicitarM2("Introduce los metros cuadrados de la estancia");
		int numP = solicitarEnteroPositivo("Indica el número de puertas de la estancia", 1);
		int numV = solicitarEnteroPositivo("Indica el número de ventanas de la estancia", 0);

		Estancia estancia = null;

		switch (tipo) {
			case "HALL":
				boolean tienePB = solicitarBoolean("Indica si tiene puerta blindada: (SI/NO)");
				estancia = new Hall(m2, numP, numV, tienePB);
				break;

			case "COCINA":
				boolean tieneTend = solicitarBoolean("Indica si tiene tendero: (SI/NO)");
				double m2Tend = 0;
				if (tieneTend) {
					m2Tend = solicitarM2("Introduce los metros cuadrados del tendedero");
				}
				estancia = new Cocina(nombre, m2, numP, numV, tieneTend, m2Tend);
				break;

			case "SALÓN":
				int numPR = solicitarEnteroPositivo("Introduce el número de puntos de red", 1);
				boolean tieneTerr = solicitarBoolean("Indica si tiene terraza: (SI/NO)");
				double m2Terr = 0;
				if (tieneTerr) {
					m2Terr = solicitarM2("Introduce los metros cuadrados de la terraza");
				}
				estancia = new Salon(nombre, m2, numP, numV, numPR, tieneTerr, m2Terr);
				break;

			case "HABITACIÓN":
				int numPA = solicitarEnteroPositivo("Introduce el número de puertas de armario", 0);
				boolean tieneAcceso = solicitarBoolean("Indica si tiene acceso directo a baño: (SI/NO)");
				estancia = new Habitacion(nombre, m2, numP, numV, numPA, tieneAcceso);
				break;

			case "BAÑO":
				boolean ducha = solicitarBoolean("Indica si tiene ducha: (SI/NO)");
				boolean banera = solicitarBoolean("Indica si tiene bañera: (SI/NO)");
				estancia = new Banio(nombre, m2, numP, numV, ducha, banera);
				break;

		}

		objCasa.addEstancia(estancia);
		System.out.println("Estancia añadida correctamente");

	}

	private static boolean solicitarBoolean(String mensaje) {
		String respuesta = "";

		while (!respuesta.equals("SI") && !respuesta.equals("NO")) {
			System.out.println(mensaje);
			respuesta = sc.nextLine().trim().toUpperCase();

			if (!respuesta.equals("SI") && !respuesta.equals("NO")) {
				System.out.println("ERROR: La respuesta debe ser SI o NO");
			}
		}

		return respuesta.equals("SI");
	}

	private static int solicitarEnteroPositivo(String mensaje, int min) {
		int entero = min - 1;

		while (entero < min) {
			try {
				System.out.println(mensaje);
				entero = Integer.parseInt(sc.nextLine());

				if (entero < min) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR: El valor debe ser entero mayor que " + min);
			}

		}

		return entero;
	}

	private static double solicitarM2(String mensaje) {
		double m2 = 0;

		while (m2 <= 0) {
			try {
				System.out.println(mensaje);
				m2 = Double.parseDouble(sc.nextLine());

				if (m2 <= 0) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR: Los metros cuadrados debe ser una valor numérico positivo");
			}
		}

		return m2;
	}

	private static String solicitarTipo() {
		String tipo = "";
		boolean encontrado = false;

		do {
			System.out.println("Introduce un de los siguientes tipos: " + Arrays.toString(Estancia.TIPOS_ESTANCIA));
			tipo = sc.nextLine().trim().toUpperCase();

			for (int i = 0; i < Estancia.TIPOS_ESTANCIA.length && !encontrado; i++) {
				if (tipo.equals(Estancia.TIPOS_ESTANCIA[i])) {
					encontrado = true;
				}
			}

			if (!encontrado) {
				System.out.println("ERROR: El valor introducido no es uno de los indicados");
			}
		} while (!encontrado);

		return tipo;
	}

	private static void mostrarInfoCasa() {
		if (objCasa.getListaEstancias().size() == 0) {
			System.out.println("No se han especificado las estancias de esta casa");
		} else {
			System.out.println(objCasa);

			System.out.println("\nTiene " + objCasa.calcularTotalM2() + " m2");
		}

	}

	private static String solicitarCadenaNoVacia(String mensaje) {
		String cadena = "";

		do {
			System.out.println(mensaje);
			cadena = sc.nextLine().trim();

			if (cadena.isEmpty()) {
				System.out.println("ERROR: El valor no puede estar vacío");
			}

		} while (cadena.isEmpty());

		return cadena;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
