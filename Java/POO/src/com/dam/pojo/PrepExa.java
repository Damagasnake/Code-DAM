package com.dam.pojo;

public class PrepExa {
    private String name;
    private String agencia;
    private String estado;
    private String year;

    public PrepExa(String name, String agencia, String estado, String year) {
        this.name = name;
        this.agencia = agencia;
        this.estado = estado;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Nombre del satelite: " + name + ", agencia que lo lanzo: " + agencia + ", estado funcional: " + estado
                + ", años de servicio: " + year;
    }

    public int calcAntiguedad() {
        int antiguedad;
        String[] años = year.split("-");
        int lanzamiento = Integer.parseInt(años[0]);
        int actual;
        if (años.length > 1 && !años[1].isEmpty()) {
            actual = Integer.parseInt(años[1]);
        } else {
            actual = 2025;
        }
        antiguedad = actual - lanzamiento;
        return antiguedad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
