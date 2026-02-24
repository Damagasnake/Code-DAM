package com.dam.main;

import java.util.Scanner;

/**
 * ============================================================
 *  LIBRERÍA DE MÉTODOS REUTILIZABLES - DAM Programación
 *  Basada en los patrones de la profesora (3 ejercicios):
 *    - GestionNoticias (herencia, ArrayList, try-catch)
 *    - GestionArq      (herencia, boolean, double)
 *    - GestionEmpresa  (herencia, instanceof, calcularTotal)
 * ============================================================
 *
 * ÍNDICE:
 *  1. VALIDACIÓN DE ENTRADA
 *     - solicitarCadenaNoVacia(String mensaje)
 *     - solicitarFecha(String mensaje)
 *     - solicitarOpcion(String mensaje, String[] arrayValores)
 *     - solicitarBoolean(String mensaje)
 *     - solicitarEnteroPositivo(String mensaje, int min)
 *     - solicitarDouble(String mensaje)
 *
 *  2. BÚSQUEDA
 *     - buscarValor(String valor, String[] array) → boolean
 *     - [patrón comentado] buscarEnLista()        → int posición
 *
 *  3. UTILIDADES DE STRING
 *     - obtenerIniciales(String cadena)
 *
 *  4. REFERENCIA RÁPIDA POJO (todo comentado)
 */
public class Libreria {

    static Scanner sc = new Scanner(System.in);

    // ============================================================
    // 1. VALIDACIÓN DE ENTRADA
    // ============================================================

    /**
     * Solicita una cadena no vacía.
     * APARECE EN: GestionNoticias, GestionArq, GestionEmpresa (todos)
     */
    public static String solicitarCadenaNoVacia(String mensaje) {
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

    /**
     * Solicita una fecha de exactamente 10 caracteres (ej: "17/03/2025").
     * APARECE EN: GestionNoticias
     */
    public static String solicitarFecha(String mensaje) {
        final int TAM_FECHA = 10;
        String fecha = "";
        do {
            System.out.println(mensaje);
            fecha = sc.nextLine().trim();
            if (fecha.length() != TAM_FECHA) {
                System.out.println("ERROR: La fecha debe tener " + TAM_FECHA + " caracteres (ej: 17/03/2025)");
            }
        } while (fecha.length() != TAM_FECHA);
        return fecha;
    }

    /**
     * Solicita una opción válida de un array de Strings (case-insensitive).
     * APARECE EN: GestionNoticias (tipos de noticia, partidos políticos)
     *
     * Uso: String tipo = solicitarOpcion("Elige tipo", Noticia.TIPOS_NOTICIAS);
     */
    public static String solicitarOpcion(String mensaje, String[] arrayValores) {
        String opcion = "";
        boolean encontrado = false;
        do {
            System.out.println(mensaje);
            opcion = sc.nextLine().trim();
            encontrado = buscarValor(opcion, arrayValores);
            if (!encontrado) {
                System.out.println("ERROR: El valor introducido no es uno de los esperados");
            }
        } while (!encontrado);
        return opcion;
    }

    /**
     * Solicita SI o NO y retorna true/false.
     * APARECE EN: GestionArq (puerta blindada, ducha, bañera, terraza...)
     */
    public static boolean solicitarBoolean(String mensaje) {
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

    /**
     * Solicita un entero >= min con try-catch.
     * - Si no es número → NumberFormatException capturada automáticamente.
     * - Si es número pero < min → se lanza NumberFormatException manualmente.
     *
     * APARECE EN: GestionArq (puertas, ventanas, puntos de red...)
     *
     * Ejemplos:
     *   solicitarEnteroPositivo("Nº puertas", 1)   → mínimo 1
     *   solicitarEnteroPositivo("Nº ventanas", 0)  → mínimo 0
     */
    public static int solicitarEnteroPositivo(String mensaje, int min) {
        int entero = min - 1;
        while (entero < min) {
            try {
                System.out.println(mensaje);
                entero = Integer.parseInt(sc.nextLine().trim());
                if (entero < min) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: El valor debe ser un entero >= " + min);
                entero = min - 1;
            }
        }
        return entero;
    }

    /**
     * Solicita un número decimal (double) positivo con try-catch.
     * - Si no es número → NumberFormatException capturada.
     * - Si es <= 0 → se lanza manualmente.
     *
     * APARECE EN: GestionArq (metros cuadrados), GestionEmpresa (salario, plus nocturnidad)
     * NOMBRES EN LA PROFE: solicitarM2(), solicitarDecimalPos() → mismo patrón, aquí unificado.
     */
    public static double solicitarDouble(String mensaje) {
        double valor = 0;
        while (valor <= 0) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(sc.nextLine().trim());
                if (valor <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: El valor debe ser numérico y mayor que 0");
                valor = 0;
            }
        }
        return valor;
    }


    /**
     * Solicita una palabra que pertenezca a un array de opciones validas.
     * Usa IllegalArgumentException para el control de errores.
     * Diferencia con solicitarOpcion: esta usa throw explicito.
     * USA: throw new IllegalArgumentException cuando el valor no esta en el array.
     */
    public static String solicitarPalabra(String mensaje, String[] opciones) {
        String valor = "";
        boolean valido = false;

        while (!valido) {
            try {
                System.out.println(mensaje);
                valor = sc.nextLine().trim();

                if (!buscarValor(valor, opciones)) {
                    throw new IllegalArgumentException("Valor no valido");
                }
                valido = true;

            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        return valor;
    }


    // ============================================================
    // 2. BÚSQUEDA
    // ============================================================

    /**
     * Busca si una cadena existe en un array de Strings (case-insensitive).
     * Retorna true si la encuentra, false si no.
     * APARECE EN: GestionNoticias (auxiliar de solicitarOpcion)
     *
     * Patrón del for con && !encontrado → para en cuanto lo encuentra:
     *   for (int i = 0; i < array.length && !encontrado; i++) { ... }
     */
    public static boolean buscarValor(String valor, String[] array) {
        boolean encontrado = false;
        for (int i = 0; i < array.length && !encontrado; i++) {
            if (valor.equalsIgnoreCase(array[i])) {
                encontrado = true;
            }
        }
        return encontrado;
    }

    /*
     * PATRÓN: buscar en ArrayList por campos → retorna posición int (-1 si no encontrado)
     * APARECE EN: GestionNoticias → buscarNoticia(titulo, fecha)
     *
     * Copiar y adaptar los getters según el ejercicio:
     *
     *   private static int buscarNoticia(String titulo, String fecha) {
     *       int pos = -1;
     *       for (int i = 0; i < listaNoticias.size() && pos == -1; i++) {
     *           if (listaNoticias.get(i).getTitulo().equalsIgnoreCase(titulo)
     *                   && listaNoticias.get(i).getFecha().equals(fecha)) {
     *               pos = i;
     *           }
     *       }
     *       return pos;
     *   }
     *
     *   // Uso en el main:
     *   int pos = buscarNoticia(titulo, fecha);
     *   if (pos != -1) {
     *       Noticia n = listaNoticias.get(pos);
     *       // operar con n...
     *   } else {
     *       System.out.println("No encontrada");
     *   }
     */


    // ============================================================
    // 3. UTILIDADES DE STRING
    // ============================================================

    // ============================================================
    // 4. REFERENCIA RÁPIDA POJO
    // ============================================================

    /*
     * ── CONSTANTES ESTÁTICAS EN CLASE PADRE ──────────────────
     *
     * public static final String[] ESTADOS = {"BORRADOR", "PUBLICADA", "ARCHIVADA"};
     * public static final String[] TIPOS   = {"Tipo A", "Tipo B"};
     * public static final int TAM_FECHA = 10;
     *
     * Acceso desde cualquier sitio: Noticia.ESTADOS[0], Noticia.TAM_FECHA
     *
     *
     * ── ATRIBUTOS protected vs private ───────────────────────
     *
     * protected → accesible desde subclases (extends) y mismo paquete
     * private   → solo en la propia clase, necesita getter para acceder desde fuera
     *
     *
     * ── CLASE PADRE ──────────────────────────────────────────
     *
     * public class Empleado {
     *     protected String nombre;
     *     protected double salario;
     *
     *     public Empleado(String nombre, double salario) {
     *         this.nombre = nombre;
     *         this.salario = salario;
     *     }
     *
     *     public double getSalario() { return salario; }
     *
     *     @Override
     *     public String toString() {
     *         return "Nombre: " + nombre + "\nSalario: " + salario;
     *     }
     * }
     *
     *
     * ── SUBCLASE ─────────────────────────────────────────────
     *
     * public class EmpleadoProduccion extends Empleado {
     *     private String turno;
     *     private double plusNoct;
     *
     *     public EmpleadoProduccion(String nombre, double salario, String turno, double plusNoct) {
     *         super(nombre, salario);         // llama constructor del padre PRIMERO
     *         this.turno = turno;
     *         if (turno.equals("NOCHE")) {   // lógica condicional en constructor
     *             this.plusNoct = plusNoct;
     *         }
     *     }
     *
     *     public String getTurno() { return turno; }
     *     public double getPlusNoct() { return plusNoct; }
     *
     *     @Override
     *     public String toString() {
     *         return "Empleado Producción:" + super.toString()  // reutiliza toString padre
     *                 + "\nTurno: " + turno
     *                 + (turno.equals("NOCHE") ? "\nPlus: " + plusNoct : "");
     *     }
     * }
     *
     *
     * ── MÉTODO boolean PARA CAMBIAR ESTADO ───────────────────
     *
     * public boolean cambiarEstado() {
     *     if (estado.equals(ESTADOS[0])) {        // BORRADOR → PUBLICADA
     *         estado = ESTADOS[1];
     *         return true;
     *     } else if (estado.equals(ESTADOS[1])) { // PUBLICADA → ARCHIVADA
     *         estado = ESTADOS[2];
     *         return true;
     *     } else {
     *         return false;                        // ARCHIVADA → no cambia
     *     }
     * }
     *
     *
     * ── instanceof + CASTING ─────────────────────────────────
     *
     * // Con variable auxiliar (más claro):
     * if (objeto instanceof EmpleadoProduccion) {
     *     EmpleadoProduccion emp = (EmpleadoProduccion) objeto;
     *     emp.getTurno();
     * }
     *
     * // En una línea (casting directo):
     * ((NoticiaPolitica) noticia).obtenerInicialesPartido();
     *
     *
     * ── ARRAYLIST CON HERENCIA (polimorfismo) ─────────────────
     *
     * ArrayList<Empleado> lista = new ArrayList<Empleado>();
     * lista.add(new EmpleadoProduccion(...));    // subclase en lista del padre
     * lista.add(new EmpleadoDistribucion(...));
     *
     * for (Empleado emp : lista) {
     *     System.out.println(emp);  // llama al toString() de cada subclase
     * }
     *
     *
     * ── calcularTotal() CON instanceof ───────────────────────
     *
     * public double calcularTotalSalarios() {
     *     double total = 0;
     *     for (Empleado emp : listaEmpleados) {
     *         total += emp.getSalario();                        // suma base
     *         if (emp instanceof EmpleadoProduccion) {
     *             EmpleadoProduccion ep = (EmpleadoProduccion) emp;
     *             if (ep.getTurno().equals("NOCHE")) {
     *                 total += ep.getPlusNoct();                // suma extra
     *             }
     *         }
     *     }
     *     return total;
     * }
     *
     *
     * ── ARRAYLIST DENTRO DE POJO (Casa, Empresa) ─────────────
     *
     * public class Empresa {
     *     private String nombre;
     *     private ArrayList<Empleado> listaEmpleados;
     *
     *     public Empresa(String nombre) {
     *         this.nombre = nombre;
     *         listaEmpleados = new ArrayList<Empleado>(); // inicializar en constructor
     *     }
     *
     *     public void addEmpleado(Empleado emp) { listaEmpleados.add(emp); }
     *     public ArrayList<Empleado> getListaEmpleados() { return listaEmpleados; }
     * }
     *
     *
     * ── OPERADOR TERNARIO ─────────────────────────────────────
     *
     * condicion ? valor_si_true : valor_si_false
     *
     * // En toString():
     * + (tieneTerraza ? "\nM2 terraza: " + m2Terr : "")
     * + (turno.equals("NOCHE") ? "\nPlus: " + plusNoct : "")
     *
     * // Para resultado de boolean:
     * String msg = cambiarEstado() ? "¡Modificado!" : "No se pudo modificar";
     * String texto = ducha ? "SI" : "NO";
     *
     *
     * ── MENÚ DO-WHILE CON SWITCH ──────────────────────────────
     *
     * String opcion;
     * do {
     *     System.out.println("0 - Añadir | S - Salir");
     *     opcion = sc.nextLine().toUpperCase();  // toUpperCase antes del switch
     *
     *     switch (opcion) {
     *         case "0": anadir(); break;
     *         case "S": System.out.println("Saliendo..."); break;
     *         default:  System.out.println("ERROR: opción no válida"); break;
     *     }
     * } while (!opcion.equalsIgnoreCase("S"));
     *
     * → Opciones pueden ser números ("0","1"...) o letras ("A","M","S")
     * → sc.nextLine().toUpperCase() evita problemas de mayúsculas/minúsculas
     */



    // ============================================================
    // 5. METODOS CON STRING + BUCLES
    // ============================================================

    /**
     * Obtiene las iniciales de una cadena de palabras.
     * Ejemplo: "Partido Socialista Obrero Espanol" -> "PSOE"
     * USA: split(), charAt(), isEmpty()
     */
    public static String obtenerIniciales(String cadena) {
        String iniciales = "";
        String[] palabras = cadena.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            if (!palabras[i].isEmpty()) {
                iniciales += palabras[i].charAt(0);
            }
        }
        return iniciales.toUpperCase();
    }

    /**
     * Cuenta las vocales de una cadena.
     * Ejemplo: "Hola Mundo" -> 4
     * USA: toLowerCase(), charAt(), length()
     */
    public static int contarVocales(String cadena) {
        int contador = 0;
        cadena = cadena.toLowerCase();
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba si una cadena es completamente numerica.
     * Ejemplo: "12345" -> true | "123a5" -> false
     * USA: charAt(), Character.isDigit(), length()
     * El && esNum en el for para en cuanto encuentra un no digito.
     */
    public static boolean esNumerico(String cadena) {
        boolean esNum = !cadena.isEmpty();
        for (int i = 0; i < cadena.length() && esNum; i++) {
            if (!Character.isDigit(cadena.charAt(i))) {
                esNum = false;
            }
        }
        return esNum;
    }

    /**
     * Valida formato DNI: 8 digitos + 1 letra.
     * Ejemplo: "12345678A" -> true | "1234567A" -> false
     * USA: length(), charAt(), Character.isDigit(), Character.isLetter()
     * MUY PROBABLE: GestionEmpresa solicita DNI pero no lo valida.
     */
    public static boolean validarDNI(String dni) {
        if (dni.length() != 9) return false;
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(dni.charAt(i))) return false;
        }
        return Character.isLetter(dni.charAt(8));
    }

    /**
     * Invierte una cadena.
     * Ejemplo: "Hola" -> "aloH"
     * USA: length(), charAt() - recorre de atras hacia adelante
     */
    public static String invertir(String cadena) {
        String invertida = "";
        for (int i = cadena.length() - 1; i >= 0; i--) {
            invertida += cadena.charAt(i);
        }
        return invertida;
    }

    /**
     * Capitaliza la primera letra de cada palabra.
     * Ejemplo: "hola mundo" -> "Hola Mundo"
     * USA: split(), substring(), toUpperCase(), toLowerCase(), trim()
     * substring(0,1) -> primera letra | substring(1) -> resto
     */
    public static String capitalizar(String cadena) {
        String resultado = "";
        String[] palabras = cadena.split(" ");
        for (int i = 0; i < palabras.length; i++) {
            if (!palabras[i].isEmpty()) {
                resultado += palabras[i].substring(0, 1).toUpperCase()
                           + palabras[i].substring(1).toLowerCase() + " ";
            }
        }
        return resultado.trim();
    }

    /*
     * REFERENCIA RAPIDA - METODOS STRING
     *
     * cadena.trim()               -> elimina espacios al inicio y final
     * cadena.isEmpty()            -> true si esta vacia
     * cadena.length()             -> numero de caracteres
     * cadena.charAt(i)            -> caracter en posicion i
     * cadena.substring(ini, fin)  -> extrae parte: "Hola".substring(1,3) -> "ol"
     * cadena.substring(ini)       -> desde ini hasta el final
     * cadena.split(" ")           -> divide por espacios -> array de palabras
     * cadena.equals()             -> compara exacto (mayusculas importan)
     * cadena.equalsIgnoreCase()   -> compara ignorando mayusculas
     * cadena.toUpperCase()        -> todo en mayusculas
     * cadena.toLowerCase()        -> todo en minusculas
     * cadena.contains("texto")    -> true si contiene la subcadena
     * cadena.startsWith("texto")  -> true si empieza por algo
     * cadena.endsWith(".com")     -> true si termina por algo
     * cadena.replace("a", "b")    -> reemplaza todas las "a" por "b"
     * cadena.indexOf("texto")     -> posicion donde aparece (-1 si no esta)
     *
     * Character.isDigit(c)        -> true si el char es un digito (0-9)
     * Character.isLetter(c)       -> true si el char es una letra
     * Character.isUpperCase(c)    -> true si es mayuscula
     * Character.isLowerCase(c)    -> true si es minuscula
     */

    /**
     * Conta cuantas veces aparece un caracter en una cadena.
     * Ejemplo: contarCaracter("hola mundo", 'o') -> 2
     * USA: charAt(), length()
     */
    public static int contarCaracter(String cadena, char caracter) {
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) == caracter) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Comprueba si una cadena es palindromo (igual al reves).
     * Ejemplo: "Ana" -> true | "Hola" -> false
     * USA: equalsIgnoreCase() + invertir()
     */
    public static boolean esPalindromo(String cadena) {
        return cadena.equalsIgnoreCase(invertir(cadena));
    }

    /**
     * Cuenta las palabras de una cadena, ignorando espacios multiples.
     * Ejemplo: "hola   mundo" -> 2
     * USA: split(" +") — el + significa uno o mas espacios
     */
    public static int contarPalabras(String cadena) {
        if (cadena.trim().isEmpty()) return 0;
        String[] palabras = cadena.trim().split(" +");
        return palabras.length;
    }

    /**
     * Comprueba si todos los caracteres son letras (sin numeros ni simbolos).
     * Ejemplo: "Hola" -> true | "Hola123" -> false
     * USA: Character.isLetter(), charAt(), length()
     */
    public static boolean soloLetras(String cadena) {
        boolean soloLetras = !cadena.isEmpty();
        for (int i = 0; i < cadena.length() && soloLetras; i++) {
            if (!Character.isLetter(cadena.charAt(i))) {
                soloLetras = false;
            }
        }
        return soloLetras;
    }

    /**
     * Comprueba si la cadena empieza por mayuscula.
     * Ejemplo: "Hola" -> true | "hola" -> false
     * USA: charAt(0), Character.isUpperCase()
     */
    public static boolean empiezaPorMayuscula(String cadena) {
        if (cadena.isEmpty()) return false;
        return Character.isUpperCase(cadena.charAt(0));
    }

    /**
     * Extrae solo los digitos de una cadena mixta.
     * Ejemplo: "ABC123def45" -> "12345"
     * USA: charAt(), Character.isDigit(), length()
     */
    public static String extraerDigitos(String cadena) {
        String resultado = "";
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isDigit(cadena.charAt(i))) {
                resultado += cadena.charAt(i);
            }
        }
        return resultado;
    }

    /**
     * Cuenta las mayusculas de una cadena.
     * Ejemplo: "HolaMundo" -> 2
     * USA: charAt(), Character.isUpperCase(), length()
     */
    public static int contarMayusculas(String cadena) {
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            if (Character.isUpperCase(cadena.charAt(i))) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Valida un email basico: debe contener @ y un punto despues del @.
     * Ejemplo: "ana@gmail.com" -> true | "anagmail.com" -> false
     * USA: contains(), indexOf()
     */
    public static boolean validarEmail(String email) {
        int posArroba = email.indexOf("@");
        if (posArroba == -1) return false;
        String despuesArroba = email.substring(posArroba);
        return despuesArroba.contains(".");
    }

    /**
     * Valida matricula espanola: 4 digitos + 3 letras (ej: "1234ABC").
     * USA: length(), charAt(), Character.isDigit(), Character.isLetter()
     */
    public static boolean validarMatricula(String matricula) {
        if (matricula.length() != 7) return false;
        for (int i = 0; i < 4; i++) {
            if (!Character.isDigit(matricula.charAt(i))) return false;
        }
        for (int i = 4; i < 7; i++) {
            if (!Character.isLetter(matricula.charAt(i))) return false;
        }
        return true;
    }

    // ============================================================
    // 6. PATRONES TRY-CATCH
    // ============================================================

    /**
     * Parsear int con try-catch.
     * NumberFormatException se lanza automáticamente si no es número.
     * Se lanza manualmente si no cumple la condición (< min).
     * ESTE ES EL MÁS IMPORTANTE → aparece en los ejercicios de la profe.
     */
    public static int parsearInt(String mensaje, int min) {
        int valor = min - 1;
        while (valor < min) {
            try {
                System.out.println(mensaje);
                valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < min) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ser un entero >= " + min);
                valor = min - 1;
            }
        }
        return valor;
    }

    /**
     * Parsear double con try-catch.
     * Mismo patrón que int pero con Double.parseDouble().
     * TAMBIÉN MUY PROBABLE en el examen.
     */
    public static double parsearDouble(String mensaje) {
        double valor = 0;
        while (valor <= 0) {
            try {
                System.out.println(mensaje);
                valor = Double.parseDouble(sc.nextLine().trim());
                if (valor <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ser un número positivo");
                valor = 0;
            }
        }
        return valor;
    }

    /**
     * Parsear long con try-catch.
     * Mismo patrón que int pero para números muy grandes.
     */
    public static long parsearLong(String mensaje, long min) {
        long valor = min - 1;
        while (valor < min) {
            try {
                System.out.println(mensaje);
                valor = Long.parseLong(sc.nextLine().trim());
                if (valor < min) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Debe ser un entero largo >= " + min);
                valor = min - 1;
            }
        }
        return valor;
    }

    /*
     * OTROS PATRONES TRY-CATCH (menos probables pero útiles)
     *
     *
     * ── ArrayIndexOutOfBoundsException ───────────────────────
     * Salta cuando accedes a una posición que no existe en un array.
     *
     *   try {
     *       System.out.println(array[posicion]);
     *   } catch (ArrayIndexOutOfBoundsException e) {
     *       System.out.println("ERROR: posición fuera del array");
     *   }
     *
     *
     * ── NullPointerException ──────────────────────────────────
     * Salta cuando intentas usar un objeto que es null.
     *
     *   try {
     *       System.out.println(objeto.toString());
     *   } catch (NullPointerException e) {
     *       System.out.println("ERROR: el objeto es null");
     *   }
     *
     *
     * ── Exception genérica (captura cualquier excepción) ──────
     * Se usa cuando no sabes qué tipo de error puede ocurrir.
     * Si pones varios catch, Exception siempre va AL FINAL.
     *
     *   try {
     *       // código que puede fallar
     *   } catch (NumberFormatException e) {
     *       System.out.println("ERROR: no es un número");
     *   } catch (Exception e) {
     *       System.out.println("ERROR inesperado: " + e.getMessage());
     *   }
     *
     *
     * ── ESTRUCTURA COMPLETA try-catch-finally ─────────────────
     * finally se ejecuta SIEMPRE, haya error o no.
     * (menos probable en el examen pero por si acaso)
     *
     *   try {
     *       // código
     *   } catch (NumberFormatException e) {
     *       System.out.println("ERROR: " + e.getMessage());
     *   } finally {
     *       System.out.println("Esto se ejecuta siempre");
     *   }
     *
     *
     * ── RESUMEN DE EXCEPCIONES MÁS COMUNES ───────────────────
     *
     *   NumberFormatException       → Integer.parseInt() / Double.parseDouble() con texto no numérico
     *   ArrayIndexOutOfBoundsException → array[i] con i fuera de rango
     *   NullPointerException        → objeto.metodo() cuando objeto es null
     *   ArithmeticException         → división por cero (int/int)
     *   StringIndexOutOfBoundsException → cadena.charAt(i) con i fuera de rango
     *   Exception                   → madre de todas, captura cualquier error
     */
}

