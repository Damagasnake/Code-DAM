package com.dam.actividades.herencia.Noticias;

public class NoticiaDeportes extends Noticia{
    private String deporte;

    public NoticiaDeportes(String titulo, String fecha, String deporte) {
        super(titulo, fecha);
        this.deporte = deporte;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Deporte: " + deporte;
    }
}
