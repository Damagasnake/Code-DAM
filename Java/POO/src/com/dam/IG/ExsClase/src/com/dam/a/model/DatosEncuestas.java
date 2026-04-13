package com.dam.a.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

// MODEL — Repositorio en memoria de TODAS las encuestas.
// Es el único sitio donde se acumulan los datos; la vista nunca toca esto directamente.
public class DatosEncuestas {

    // Lista que actúa como "base de datos" en memoria.
    private ArrayList<Encuesta> listaEncuestas;

    public DatosEncuestas() {
        listaEncuestas = new ArrayList<Encuesta>();   // empieza vacía
    }

    // Añade una nueva encuesta a la lista. Llamado por el controlador.
    public void addEncuesta(Encuesta en) {
        listaEncuestas.add(en);
    }

    // Devuelve la lista completa. El controlador la pasa a la vista para mostrarla.
    public ArrayList<Encuesta> getListaEncuestas() {
        return listaEncuestas;
    }

    // ── ANÁLISIS ESTADÍSTICO ──────────────────────────────────────────────────
    // Recorre todas las encuestas y calcula:
    //   1. Conteo de encuestados por rango de edad
    //   2. Frecuencia de visionado más votada
    //   3. Serie más vista
    // Devuelve un String formateado listo para mostrar en un JOptionPane.
    public String realizarAnalisis() {
        String analisis = "";

        // ── Paso 1: inicializar tres contadores (HashMap clave→conteo) ────────
        // Clave = valor posible (ej. "Breaking Bad"), Valor = nº de votos

        HashMap<String, Integer> contadorRangos = new HashMap<String, Integer>();
        for (String rango : Encuesta.RANGOS_EDAD) {
            contadorRangos.put(rango, 0);    // inicializar a 0
        }

        HashMap<String, Integer> contadorFrec = new HashMap<String, Integer>();
        for (String frec : Encuesta.FRECUENCIAS) {
            contadorFrec.put(frec, 0);
        }

        HashMap<String, Integer> contadorSeries = new HashMap<String, Integer>();
        for (String serie : Encuesta.SERIES) {
            contadorSeries.put(serie, 0);
        }

        // ── Paso 2: recorrer encuestas y acumular conteos ─────────────────────
        for (Encuesta encuesta : listaEncuestas) {
            // sumar 1 al rango de edad de esta encuesta
            contadorRangos.put(encuesta.getRangoEdad(),
                    contadorRangos.get(encuesta.getRangoEdad()) + 1);

            // sumar 1 a la frecuencia de esta encuesta
            contadorFrec.put(encuesta.getFrecuencia(),
                    contadorFrec.get(encuesta.getFrecuencia()) + 1);

            // sumar 1 a CADA serie que marcó este encuestado
            for (String serie : encuesta.getListaSeriesVistas()) {
                contadorSeries.put(serie, contadorSeries.get(serie) + 1);
            }
        }

        // ── Paso 3: construir el String de salida ─────────────────────────────

        // 3a. Listar todos los rangos con su conteo
        analisis += "Encuestados por rango: \n";
        for (Entry<String, Integer> entry : contadorRangos.entrySet()) {
            analisis += "\t" + entry.getKey() + " - " + entry.getValue() + " encuestados\n";
        }

        // 3b. Frecuencia más usada: recorrer el mapa buscando el máximo
        analisis += "\nFrecuencia más usada: ";
        String frecMU   = "";
        int mayorFrec   = -1;   // -1 garantiza que el primer valor siempre gana
        for (Entry<String, Integer> entry : contadorFrec.entrySet()) {
            if (entry.getValue() > mayorFrec) {
                frecMU    = entry.getKey();
                mayorFrec = entry.getValue();
            }
        }
        analisis += frecMU;

        // 3c. Serie más vista: igual que el paso anterior
        analisis += "\n\nSerie más vista: ";
        String serieMV = "";
        int mayorVis   = -1;
        for (Entry<String, Integer> entry : contadorSeries.entrySet()) {
            if (entry.getValue() > mayorVis) {
                serieMV  = entry.getKey();
                mayorVis = entry.getValue();
            }
        }
        analisis += serieMV;

        return analisis;
    }
}