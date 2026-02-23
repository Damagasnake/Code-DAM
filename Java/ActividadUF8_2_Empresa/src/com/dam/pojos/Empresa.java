package com.dam.pojos;

import java.util.ArrayList;

public class Empresa {
	
	private String nombre;
	private ArrayList<Empleado> listaEmpleados;
	
	public Empresa(String nombre) {
		this.nombre = nombre;
		listaEmpleados = new ArrayList<Empleado>();
	}

	public ArrayList<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}
	
	public void addEmpleado(Empleado empleado) {
		listaEmpleados.add(empleado);
	}

	public double calcularTotalSalarios() {
		double totalSalarios = 0;
		
		for (Empleado empleado : listaEmpleados) {
			totalSalarios += empleado.getSalario();
			
			if (empleado instanceof EmpleadoProduccion) {
				EmpleadoProduccion empProd = (EmpleadoProduccion) empleado;
				if (empProd.getTurno().equals(EmpleadoProduccion.TURNOS[2])) {
					totalSalarios += empProd.getPlusNoct();
				}
			}
		}
		
		return totalSalarios;
	}
	
	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		String cadena = "Empresa: " + nombre;
		for (Empleado empleado : listaEmpleados) {
			cadena += "\n" + empleado;
		}
		return cadena;
	}
}
