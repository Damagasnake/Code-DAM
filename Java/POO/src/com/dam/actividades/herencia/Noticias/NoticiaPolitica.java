package com.dam.actividades.herencia.Noticias;

public class NoticiaPolitica extends Noticia {
    static final String[] PARTIDOS = { "Partido Socialista Obrero Español", "Alianza Popular", "Izquierda Unida",
            "Centro Democratico Social" };
    private String partido;

    public NoticiaPolitica(String titulo, String fecha, String partido) {
        super(titulo, fecha);
        this.partido = partido;
    }

    public String InicialesPartido() {
        String[] Iniciales = partido.split(" ");
        String res = "";
        for (int i = 0; i < Iniciales.length; i++) {
            res += Iniciales[i].charAt(0);
        }
        return res;
    }

    public String getPartido() {
        return partido;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Partido: " + partido;
    }

}
// Partido Socialista Obrero Español PartidoSep[0] = Partido
// PartidoSep[1] = Socialista
// PartidoSep[2] = Obrero
// PartidoSep[3] = Español