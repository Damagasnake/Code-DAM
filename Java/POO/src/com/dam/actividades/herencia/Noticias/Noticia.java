package com.dam.actividades.herencia.Noticias;

public class Noticia {
    protected String titulo;
    protected String fecha;
    protected String estado;
    protected String[] ESTADO = {"BORRADOR", "PUBLICADA", "ARCHIVADA"};
    protected String[] TNOTICIA = {"Economia", "Deportes", "Politica"};

    public Noticia(String titulo, String fecha) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.estado = ESTADO[0];
    }

    public boolean cambiarEstado() {
        boolean posible = false;
        if (estado.equals(ESTADO[0])) {
            posible = true;
            estado = ESTADO[1];
        } else if (estado.equals(ESTADO[1])) {
            posible = true;
            estado = ESTADO[2];
        } else {
            System.out.println("No se puede realizar el cambio de estado");
        }
        return posible;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return " Titulo: " + titulo + "\n" + "Fecha: " + fecha + "\n" + "Estado: " + estado;
    }
}
