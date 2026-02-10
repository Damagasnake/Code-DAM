package com.dam.actividades.arq;

public class Banio extends Estancia {
    boolean ducha;
    boolean baniera;

    public Banio(String nombre, double metrosCuad, int numPuertas,
            int numVentanas, boolean baniera, boolean ducha) {
        super(nombre, metrosCuad, numPuertas, numVentanas);
        this.baniera = baniera;
        this.ducha = ducha;
    }

    @Override
    public String toString() {
        return "Banio [ducha=" + ducha + ", baniera=" + baniera + "]";
    }
}
