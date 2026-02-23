package com.dam.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.dam.pojos.Noticia;
import com.dam.pojos.NoticiaDeportiva;
import com.dam.pojos.NoticiaEconomica;
import com.dam.pojos.NoticiaPolitica;

public class GestionNoticias {

	static ArrayList<Noticia> listaNoticias;
	static Scanner sc;
	
	public static void main(String[] args) {
		
		listaNoticias = new ArrayList<Noticia>();
		
		sc = new Scanner(System.in);
		
		String opcion;
		
		do {
			System.out.println("\nIntroduce una de las siguiente opciones:");
			System.out.println("0 para Añadir una noticia a la lista");
			System.out.println("1 para Modificar el estado de una noticia");
			System.out.println("2 para Mostrar las iniciales del partido político");
			System.out.println("3 para Mostrar la lista de noticias");
			System.out.println("4 para salir del programa");
			opcion = sc.nextLine().toUpperCase();
			
			switch (opcion) {
			case "0":
				addNoticia();
				break;
			case "1":
				modificarEstado();
				break;
			case "2":
				mostrarIniciales();
				break;
			case "3":
				mostrarLista();
				break;
			case "4":
				System.out.println("\n** Saliendo del programa **");
				break;
			default:
				System.out.println("ERROR: El valor introducido no es uno de los esperados");
				break;
			}
		} while (!opcion.equalsIgnoreCase("4"));
		
		sc.close();
		

	}

	private static void mostrarIniciales() {
		String titulo = solicitarCadenaNoVacia(
		"Introduce el título de la noticia política para la que desea consultar las iniciales del partido");
		String fecha = solicitarFecha();
		
		// el método buscarNoticia me retornará la posición en la que se encuentra la noticia
		int posNoticia = buscarNoticia(titulo, fecha);
		
		if (posNoticia != -1) {
			Noticia noticia = listaNoticias.get(posNoticia);
			if (noticia instanceof NoticiaPolitica) {
				System.out.println("La iniciales del partido de la noticia son " + 
						((NoticiaPolitica) noticia).obtenerInicialesPartido());
				
			} else {
				System.out.println("La noticia indicada no corresponde con una noticia política");
			}
			
		} else {
			System.out.println("La noticia indicada no ha sido encontrada");
			
		}
		
	}

	private static void modificarEstado() {
		String titulo = solicitarCadenaNoVacia("Introduce el título de la noticia que desea modificar");
		String fecha = solicitarFecha();
		
		// el método buscarNoticia me retornará la posición en la que se encuentra la noticia
		int posNoticia = buscarNoticia(titulo, fecha);
		
		if (posNoticia != -1) {
			String resultado = listaNoticias.get(posNoticia).cambiarEstado()? 
					"Se ha modificado el estado con éxito": 
						"No se puede modificar el estado de esta noticia";
			System.out.println(resultado);
			
		} else {
			System.out.println("La noticia indicada no ha sido encontrada");
			
		}
		
	}

	private static int buscarNoticia(String titulo, String fecha) {
		int pos = -1;
		for (int i = 0; i < listaNoticias.size() && pos == -1; i++) {
			if (listaNoticias.get(i).getTitulo().equalsIgnoreCase(titulo) 
					&& listaNoticias.get(i).getFecha().equals(fecha)) {
				pos = i;
			}
			
		}
		
		return pos;
	}

	private static void mostrarLista() {
		if (listaNoticias.isEmpty()) {
			System.out.println("No hay noticias registradas");
		} else {
			System.out.println("** Listado de noticias **");
			for (Noticia noticia : listaNoticias) {
				System.out.println("\n" + noticia);
			}
		}
		
	}

	private static void addNoticia() {
		String tipo = solicitarOpcion("Indica el tipo de noticia " + Arrays.toString(Noticia.TIPOS_NOTICIAS), Noticia.TIPOS_NOTICIAS);
		
		Noticia noticia = null;
		
		String titulo = solicitarCadenaNoVacia("Introduce el título");
		String fecha = solicitarFecha();
		
		if (tipo.equalsIgnoreCase(Noticia.TIPOS_NOTICIAS[0])) {
			String deporte = solicitarCadenaNoVacia("Introduce el deporte");
			noticia = new NoticiaDeportiva(titulo, fecha, deporte);
			
		} else if (tipo.equalsIgnoreCase(Noticia.TIPOS_NOTICIAS[1])) {
			String sector = solicitarCadenaNoVacia("Introduce el sector");
			noticia = new NoticiaEconomica(titulo, fecha, sector);
			
		} else { //Noticia.TIPOS_NOTICIAS[2]
			String partido = solicitarOpcion("Introduce el partido \n" + Arrays.toString(NoticiaPolitica.PARTIDOS), NoticiaPolitica.PARTIDOS);
			noticia = new NoticiaPolitica(titulo, fecha, partido);
		}
		
		listaNoticias.add(noticia);
		System.out.println("¡¡NOTICIA REGISTRADA!!");
		
	}
	
	private static String solicitarFecha() {
		String fecha = "";
		
		do {
			System.out.println("Introduce fecha");
			fecha = sc.nextLine().trim();
			
			if (fecha.length() != Noticia.TAM_FECHA) {
				System.out.println("ERROR: La fecha tiene que tener " + Noticia.TAM_FECHA + " caracteres");
			}
			
		} while (fecha.length() != Noticia.TAM_FECHA);
		
		return fecha;
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

	private static String solicitarOpcion(String mensaje, String[] arrayValores) {
		String opcion = "";
		
		boolean encontrado = false;
		do {
			System.out.println(mensaje);
			opcion = sc.nextLine();
			
			encontrado = buscarValor(opcion, arrayValores);
			
			if (!encontrado) {
				System.out.println("ERROR: El valor introducido no es uno de los esperados");
			}
		} while (!encontrado);	
		
		
		return opcion;
	}

	private static boolean buscarValor(String opcion, String[] arrayValores) {
		boolean encontrado = false;
		
		for (int i = 0; i < arrayValores.length && !encontrado; i++) {
			if (opcion.equalsIgnoreCase(arrayValores[i])) {
				encontrado = true;
			}
		}
		
		return encontrado;
	}
}
