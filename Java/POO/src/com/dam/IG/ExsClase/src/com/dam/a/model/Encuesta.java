package com.dam.a.model;

import java.util.ArrayList;

// MODEL — POJO puro: almacena datos de UNA encuesta individual.
// No sabe nada de Swing ni del controlador.
public class Encuesta {

    // ── CONSTANTES ESTÁTICAS ──────────────────────────────────────────────────
    // Centralizadas aquí para que toda la app use los mismos valores.
    // "static final" = constante de clase (no de instancia), disponible sin crear objeto.
    public static final String[] RANGOS_EDAD = {"Entre 5 y 17", "Entre 18 y 30",
            "Entre 31 y 40", "Entre 41 y 65", "Más de 65"};
    public static final String[] FRECUENCIAS = {"Ninguna", "1 o 2 veces por semana",
            "3 o 4 veces por semana", "5 o 6 veces por semana",  "Todos los días"};
    public static final String[] SERIES = {"Juego de Tronos", "Vikingos", "Breaking Bad",
            "Stranger Things", "El Cuento de la Criada", "El Juego del Calamar",
            "Dragon Ball", "7 vidas"};
    public static final String VXS      = "veces por semana";
    public static final String VXS_SIMP = "v/s";

    // ── CAMPOS (estado de una encuesta concreta) ──────────────────────────────
    private String rangoEdad;                     // ej. "Entre 18 y 30"
    private String frecuencia;                    // ej. "3 o 4 veces por semana"
    private ArrayList<String> listaSeriesVistas;  // ej. ["Breaking Bad", "Dragon Ball"]

    // ── CONSTRUCTOR ───────────────────────────────────────────────────────────
    // Recibe los tres datos y los almacena.
    public Encuesta(String rangoEdad, String frecuencia, ArrayList<String> listaSeriesVistas) {
        this.rangoEdad         = rangoEdad;
        this.frecuencia        = frecuencia;
        this.listaSeriesVistas = listaSeriesVistas;
    }

    // ── toString ──────────────────────────────────────────────────────────────
    // JList llama a toString() automáticamente para mostrar cada elemento.
    // Devuelve: "Entre 18 y 30 - 3 o 4 v/s - Series vistas: Breaking Bad, Dragon Ball"
    @Override
    public String toString() {
        return rangoEdad + " - " + traducirFrecuencia() + " - Series vistas: " + mostrarLista();
    }

    // Convierte ArrayList de series en un String separado por comas.
    private String mostrarLista() {
        String series = "";
        for (String serie : listaSeriesVistas) {
            if (!series.isEmpty()) {
                series += ", ";   // separador solo entre elementos, no al principio
            }
            series += serie;
        }
        return series;
    }

    // ── GETTERS ───────────────────────────────────────────────────────────────
    // El controlador los usa para acceder a los datos cuando construye el análisis.
    public String getRangoEdad()                  { return rangoEdad; }
    public String getFrecuencia()                 { return frecuencia; }
    public ArrayList<String> getListaSeriesVistas() { return listaSeriesVistas; }

    // ── HELPER PRIVADO ────────────────────────────────────────────────────────
    // Abrevia la frecuencia para el toString:
    //   "3 o 4 veces por semana" → "3 o 4 v/s"
    //   "Todos los días"         → "Todos"
    //   "Ninguna"                → "0 v/s"
    private String traducirFrecuencia() {
        String frecuenciaSimp = "";
        // Si NO es "Ninguna" (índice 0) ni "Todos los días" (índice 4): reemplazar la cadena larga
        if (!frecuencia.equals(FRECUENCIAS[0]) && !frecuencia.equals(FRECUENCIAS[4])) {
            frecuenciaSimp = frecuencia.replace(VXS, VXS_SIMP);
        } else if (frecuencia.equals(FRECUENCIAS[4])) {
            frecuenciaSimp = "Todos";
        } else {
            frecuenciaSimp = "0 " + VXS_SIMP;   // caso "Ninguna"
        }
        return frecuenciaSimp;
    }
}