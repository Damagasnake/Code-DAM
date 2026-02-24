package com.dam.actividades.herencia.empresa;

import java.util.ArrayList;

public class Empresa {
    private String nombre;
    private ArrayList<Empleado> listaEm;

    public Empresa(String nombre, ArrayList<Empleado> listaEm) {
        this.nombre = nombre;
        this.listaEm = listaEm;
    }

    public ArrayList<Empleado> getListaEm() {
        return listaEm;
    }

    public void addEmpleado(Empleado empleado) {
        listaEm.add(empleado);
    }
    public double calcSal() {
        double totalsalario = 0;
        for (Empleado empleado : listaEm) {
            totalsalario += empleado.getSalario();
            if (empleado instanceof EmpleadoProduccion) {
                EmpleadoProduccion emprod = (EmpleadoProduccion) empleado;
                if (emprod.getTurno().equals(EmpleadoProduccion.TURNOS[2])) {
                    totalsalario += emprod.getPlusNoct();
                }
            }
        }
        return totalsalario;
    }
    
    public String getNombre() {
        return nombre;
    }
}
