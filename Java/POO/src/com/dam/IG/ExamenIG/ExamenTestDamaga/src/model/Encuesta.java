package model;

import java.util.ArrayList;

public class Encuesta {
    public static final String[] RANGOS_EDAD = {"Entre 5 y 17", "Entre 18 y 30",
            "Entre 31 y 40", "Entre 41 y 65", "Más de 65"};
    public static final String[] FRECUENCIAS = {"Ninguna", "1 o 2 veces por semana",
            "3 o 4 veces por semana", "5 o 6 veces por semana", "Todos los días"};
    public static final String[] SERIES = {"Juego de Tronos", "Vikingos", "Breaking Bad",
            "Stranger Things", "El Cuento de la Criada", "El Juego del Calamar",
            "Dragon Ball", "7 vidas"};
    public static final String VXS = "veces por semana";
    public static final String VXS_SIMP = "v/s";

    private String rangoEdad;
    private String frecuencia;
    private ArrayList<String> listaSeriesVistas;

    public Encuesta(String rangoEdad, String frecuencia, ArrayList<String> listaSeriesVistas) {
        this.frecuencia = frecuencia;
        this.rangoEdad = rangoEdad;
        this.listaSeriesVistas = listaSeriesVistas;
    }

    public String getRangoEdad() {
        return rangoEdad;
    }

    public void setRangoEdad(String rangoEdad) {
        this.rangoEdad = rangoEdad;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public ArrayList<String> getListaSeriesVistas() {
        return listaSeriesVistas;
    }

    public void setListaSeriesVistas(ArrayList<String> listaSeriesVistas) {
        this.listaSeriesVistas = listaSeriesVistas;
    }

    public String showList() {
        String series = "";

        for (String serie : listaSeriesVistas) {
            if (!series.isEmpty()) {
                serie += ", ";
            }
            series += serie;
        }
        return series;
    }

    private String frecSim() {
        String frecuenciaSimp = "";
        if (!frecuenciaSimp.equals(FRECUENCIAS[0]) && !frecuenciaSimp.equals(FRECUENCIAS[4])) {
            frecuenciaSimp = frecuencia.replace(VXS, VXS_SIMP);
        } else if (!frecuencia.equals(FRECUENCIAS[4])) {
            frecuenciaSimp = "Todos";
        } else {
            frecuenciaSimp = "0 " + VXS_SIMP;
        }
        return frecuenciaSimp;
    }
}
