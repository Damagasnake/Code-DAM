package com.dam.main;

import java.util.Arrays;
import java.util.Scanner;

import com.dam.pojos.Empleado;
import com.dam.pojos.EmpleadoDistribucion;
import com.dam.pojos.EmpleadoProduccion;
import com.dam.pojos.Empresa;

/*
 * En la clase ejecutable, en el método main se mostrar al usuario un menú para:
-	Añadir un empleado
-	Mostrar datos de la empresa junto con el total de salarios
-	Salir de la aplicación
Para cada empleado que se quiera añadir, se deberá solicitar si es de Producción o 
de Distribución, y en función de la respuesta se solicitarán los datos que correspondan.
Para los empleados de producción el turno puede ser MAÑANA, TARDE o NOCHE. Si el turno 
es noche el empleado podrá tener un plus por nocturnidad

 */
public class GestionEmpresa {
	
	static Empresa empresa;
	static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		
		String nombre = solicitarCadenaNoVacia("Introduce el nombre de la empresa");
		
		// empresa = new Empresa("Mi empresa");
		empresa = new Empresa(nombre);
		
		String opcion;
		
		do {
			System.out.println("\nIntroduce una de las siguiente opciones:");
			System.out.println("A para añadir un empleado");
			System.out.println("M para mostrar los datos de la empresa junto al total de salarios");
			System.out.println("S para salir del programa");
			opcion = sc.nextLine().toUpperCase();
			
			switch (opcion) {
			case "A":
				addEmpleado();
				break;
			case "M":
				mostrarInfoEmpresa();
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

	private static void addEmpleado() {
		String tipo = solicitarTipo();
		
		String nombre = solicitarCadenaNoVacia("Introduce el nombre del empleado");
		String dni = solicitarCadenaNoVacia("Introduce el dni");
		double salario = solicitarDecimalPos("Introduce el salario");
		
		Empleado emp = null;
		
		if (tipo.equals(Empleado.TIPOS_EMPLEADO_ABR[0])) {
			String turno = solicitarTurno();
			double plusNoct = 0;
			if (turno.equals(EmpleadoProduccion.TURNOS[2])) {
				plusNoct = solicitarDecimalPos("Introduce el plus por nocturnidad");
			}
			
			emp = new EmpleadoProduccion(nombre, dni, salario, turno, plusNoct);
			
		} else {
			String zona = solicitarCadenaNoVacia("Introduce la zona de distribución");
			
			emp = new EmpleadoDistribucion(nombre, dni, salario, zona);
		}
		
		empresa.addEmpleado(emp);
		System.out.println("El empleado se ha añadido con éxito");
	}

	private static String solicitarTurno() {
		String turno = "";
		
		do {
			System.out.println("Introduce el turno " + Arrays.toString(EmpleadoProduccion.TURNOS));
			turno = sc.nextLine().trim().toUpperCase();
			
			//if (!encontrado(turno, EmpleadoProduccion.TURNOS)) {
			
			if (!turno.equals(EmpleadoProduccion.TURNOS[0]) 
					&& !turno.equals(EmpleadoProduccion.TURNOS[1]) 
					&& !turno.equals(EmpleadoProduccion.TURNOS[2])) {
				System.out.println("ERROR: El valor debe ser " + Arrays.toString(EmpleadoProduccion.TURNOS));
			}
			
		} while (!turno.equals(EmpleadoProduccion.TURNOS[0]) 
				&& !turno.equals(EmpleadoProduccion.TURNOS[1]) 
				&& !turno.equals(EmpleadoProduccion.TURNOS[2]));
		
		return turno;
	}

	private static double solicitarDecimalPos(String mensaje) {
		double salario = 0;
		
		while (salario <= 0) {
			try {
				System.out.println(mensaje);
				salario = Double.parseDouble(sc.nextLine());
				
				if (salario <= 0) {
					throw new NumberFormatException();
				}
				
			} catch (NumberFormatException e) {
				System.out.println("ERROR: El valor debe ser numérico y mayor que 0");
			}
			
		}
		
		return salario;
	}

	private static String solicitarTipo() {
		String tipo = "";
		
		do {
			// Indica el tipo de empleado (P - Producción, D - Distribución)
			System.out.println("Indica el tipo de empleado (" 
					+ Empleado.TIPOS_EMPLEADO_ABR[0] + " - " + Empleado.TIPOS_EMPLEADO[0] + ","
					+ Empleado.TIPOS_EMPLEADO_ABR[1] + " - " + Empleado.TIPOS_EMPLEADO[1] + ")");
			tipo = sc.nextLine().trim().toUpperCase();
			
			// if (!encontrado(tipo, Empleado.TIPOS_EMPLEADO_ABR)) {
			if (!tipo.equals(Empleado.TIPOS_EMPLEADO_ABR[0]) 
					&& !tipo.equals(Empleado.TIPOS_EMPLEADO_ABR[1])) {
				System.out.println("ERROR: El valor debe ser " + Empleado.TIPOS_EMPLEADO_ABR[0] 
						+ " o " + Empleado.TIPOS_EMPLEADO_ABR[1]);
			}
		} while (!tipo.equals(Empleado.TIPOS_EMPLEADO_ABR[0]) 
					&& !tipo.equals(Empleado.TIPOS_EMPLEADO_ABR[1]));
		
		return tipo;
	}

	private static void mostrarInfoEmpresa() {
		if (empresa.getListaEmpleados().isEmpty()) {
			System.out.println("No se ha añadido ningún empleado a la empresa " + empresa.getNombre());
		} else {
			System.out.println(empresa);
			
			System.out.println("\nTotal de salalios: " + empresa.calcularTotalSalarios());
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

}
