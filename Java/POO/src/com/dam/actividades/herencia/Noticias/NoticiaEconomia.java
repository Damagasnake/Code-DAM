package com.dam.actividades.herencia.Noticias;

public class NoticiaEconomia extends Noticia {
    private String sector;

    public NoticiaEconomia(String titulo, String fecha, String sector) {
        super(titulo, fecha);
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Noticia Economica \n" + super.toString() + "\n" + sector;
    }
    
}
