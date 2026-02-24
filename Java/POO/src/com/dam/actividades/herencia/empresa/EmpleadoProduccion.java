package com.dam.actividades.herencia.empresa;

public class EmpleadoProduccion extends Empleado {
    public static final String[] TURNOS = { "Mañana", "Tarde", "Noche" };
    private String turno;
    private double plusNoct;

    public EmpleadoProduccion(String nombre, String dni, double salario, String turno, double plusNoct) {
        super(nombre, dni, salario);
        this.turno = turno;
        this.plusNoct = plusNoct;
    }

    public double getPlusNoct() {
        return plusNoct;
    }

    public String getTurno() {
        return turno;
    }

    @Override
    public String toString() {
        return super.toString() + " turno " + turno
                + (turno.equals(TURNOS[2]) ? " Plus de nocturnidad " + plusNoct : "");
    }

}
