package com.dam.libreria;

import java.util.ArrayList;
import java.util.Scanner;

public class EstructuraClaseEjecutable {

    // ── 1. VARIABLES GLOBALES ─────────────────────────────────
    // static porque los métodos del main son static
    // y solo pueden acceder a variables static
    static ArrayList<ClasePadre> lista;
    static Scanner sc;

    public static void main(String[] args) {

        // ── 2. INICIALIZAR ────────────────────────────────────
        lista = new ArrayList<ClasePadre>();
        sc = new Scanner(System.in);

        // ── 3. MENÚ DO-WHILE + SWITCH ─────────────────────────
        String opcion;
        do {
            System.out.println("\nIndica la acción que deseas realizar:");
            System.out.println("A - opción 1");
            System.out.println("B - opción 2");
            System.out.println("C - opción 3");
            System.out.println("S - salir");
            opcion = sc.nextLine().toUpperCase(); // toUpperCase para no distinguir mayúsculas

            switch (opcion) {
                case "A": metodo1(); break;
                case "B": metodo2(); break;
                case "C": metodo3(); break;
                case "S": System.out.println("** CERRANDO LA APLICACIÓN **"); break;
                default:  System.out.println("ERROR: El valor introducido no es uno de los esperados"); break;
            }

        } while (!opcion.equalsIgnoreCase("S"));

        sc.close(); // cerrar el scanner al salir
    }

    // ── 4. UN MÉTODO PRIVADO POR CADA OPCIÓN DEL MENÚ ────────
    // private porque solo se usan dentro de esta clase
    // static porque el main es static

    private static void metodo1ANIADIROBJETOS() {
        
    
    

    	    // 1. SOLICITAR Y VALIDAR DATOS COMUNES
    	    String nombre = solicitarCadenaNoVacia("Introduce el nombre");
    	    int anio = solicitarEntero("Introduce el año", 1900);

    	    // 2. PREGUNTAR EL TIPO
    	    String tipo = solicitarOpcion("¿Es de Fútbol o Tenis?", ClasePadre.TIPOS);

    	    // 3. CREAR EL OBJETO (declarar como clase padre para polimorfismo)
    	    ClasePadre objeto = null;

    	    if (tipo.equalsIgnoreCase(ClasePadre.TIPOS[0])) {
    	        // solicitar datos específicos del tipo 1 (clase hija)
    	        String datoExtra = solicitarCadenaNoVacia("Introduce dato extra");
    	        objeto = new SubClase1(nombre, anio, datoExtra);

    	    } else {
    	        // solicitar datos específicos del tipo 2 (clase hija)
    	        String datoExtra2 = solicitarCadenaNoVacia("Introduce dato extra 2");
    	        objeto = new SubClase2(nombre, anio, datoExtra2);
    	    }

    	    // 4. AÑADIR A LA LISTA E INFORMAR
    	    lista.add(objeto);
    	    System.out.println("¡¡REGISTRADO!!");
    	}
    
    //NOTAS
    
    //El objeto se declara como clase padre (Deportista objeto = null) para poder asignarle cualquier subclase
    //Se declara como null antes del if porque Java necesita que esté declarado antes de usarlo
    //lista.add(objeto) va fuera del if, siempre al final
    
    
    
    

    private static void metodo2() {
        // solicitar datos → validar → operar → informar
    }

    private static void metodo3() {
        // solicitar datos → validar → operar → informar
    }

    // ── 5. MÉTODOS AUXILIARES ─────────────────────────────────

    // Buscar en lista por un campo → retorna posición int (-1 si no encontrado)
    private static int buscarEnLista(String campo) {
        int pos = -1;
        for (int i = 0; i < lista.size() && pos == -1; i++) {
            if (lista.get(i).getCampo().equalsIgnoreCase(campo)) {
                pos = i;
            }
        }
        return pos;
    }

    // Buscar en lista por dos campos → retorna posición int (-1 si no encontrado)
    private static int buscarEnListaDos(String campo1, String campo2) {
        int pos = -1;
        for (int i = 0; i < lista.size() && pos == -1; i++) {
            if (lista.get(i).getCampo1().equalsIgnoreCase(campo1)
                    && lista.get(i).getCampo2().equalsIgnoreCase(campo2)) {
                pos = i;
            }
        }
        return pos;
    }

    // Validar cadena no vacía
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

    // Validar opción de un array
    private static String solicitarOpcion(String mensaje, String[] opciones) {
        String opcion = "";
        boolean encontrado = false;
        do {
            System.out.println(mensaje);
            opcion = sc.nextLine().trim();
            for (int i = 0; i < opciones.length && !encontrado; i++) {
                if (opcion.equalsIgnoreCase(opciones[i])) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                System.out.println("ERROR: El valor introducido no es uno de los esperados");
            }
        } while (!encontrado);
        return opcion;
    }

    // Validar entero con try-catch
    private static int solicitarEntero(String mensaje, int min) {
        int valor = min - 1;
        while (valor < min) {
            try {
                System.out.println(mensaje);
                valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < min) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ser un entero >= " + min);
                valor = min - 1;
            }
        }
        return valor;
    }

    // Validar double con try-catch
    private static double solicitarDouble(String mensaje) {
        double valor = 0;
        while (valor <= 0) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(sc.nextLine().trim());
                if (valor <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ser un número positivo");
                valor = 0;
            }
        }
        return valor;
    }

}