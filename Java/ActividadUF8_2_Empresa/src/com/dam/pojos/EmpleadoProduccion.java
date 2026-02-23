package com.dam.pojos;

public class EmpleadoProduccion extends Empleado {
	
	public static final String[] TURNOS = {"MAÑANA", "TARDE", "NOCHE"};
	
	private String turno;
	private double plusNoct;
	
	public EmpleadoProduccion(String nombre, String dni, 
			double salario, String turno, double plusNoct) {
		super(nombre, dni, salario);
		this.turno = turno;
		if (turno.equals(TURNOS[2])) {
			this.plusNoct = plusNoct;
		}
	}

	public String getTurno() {
		return turno;
	}

	public double getPlusNoct() {
		return plusNoct;
	}
	
	@Override
	public String toString() {
		// si el turno es NOCHE --> mostramos el plus de nocturnidad
		return "\nEmpleado de Producción: " + super.toString() 
		+ "\nTurno: " + turno 
		+ (turno.equals(TURNOS[2])? "\nPlus de nocturnidad: " + plusNoct : "");
	}

}
