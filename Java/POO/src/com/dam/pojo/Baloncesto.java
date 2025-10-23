package com.dam.pojo;

public class Baloncesto {
    private String nombre;
    private String nombreCiudad;
    private int partidosGanados;
    private int partidosperdidos;
    private int partidoempatado;

    public Baloncesto(String nombre, String nombreCiudad, int partidosGanados, int partidoempatado, int partidosperdidos) {

        this.nombre = nombre;
        this.nombreCiudad = nombreCiudad;
        this.partidoempatado = partidoempatado;
        this.partidosGanados = partidosGanados;
        this.partidosperdidos = partidosperdidos;
    }

    public int CalculoPts() {
        int puntosempatados = 0;
        int puntosganados = 0;
        if (partidoempatado != 0)
            puntosempatados = (partidoempatado * 1);
        if (partidosGanados != 0)
            puntosganados = (partidosGanados * 3);
        return puntosganados + puntosempatados;
    }

    @Override
    public String toString() {
        return "El nombre de tu equipo es " + nombre + " Has jugado en " + nombreCiudad + " Has ganado "
                + partidosGanados + " Has empatado " + partidoempatado + " Has perdido " + partidosperdidos;
    }
}