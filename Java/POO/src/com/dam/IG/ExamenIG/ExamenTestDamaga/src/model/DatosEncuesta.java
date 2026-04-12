package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DatosEncuesta {
    private ArrayList<Encuesta> listEncuestas;

    public void datosEncuesta() {
        listEncuestas = new ArrayList<Encuesta>();
    }

    public void addEncuesta(Encuesta encuesta) {
        listEncuestas.add(encuesta);
    }

    public ArrayList<Encuesta> getListEncuestas() {
        return listEncuestas;
    }

    public String Analisis() {
        String analisis = "";
        HashMap<String, Integer> contadorRangos = new HashMap<String, Integer>();

        for (String rango : Encuesta.RANGOS_EDAD){
            contadorRangos.put(rango, 0);
        }

        HashMap<String,Integer> contadorFrec = new HashMap<String, Integer>();
        for (String frecuencias : Encuesta.FRECUENCIAS){
            contadorFrec.put(frecuencias, 0);
        }

        HashMap<String, Integer> contadorSeries = new HashMap<String, Integer>();
        for (String series : Encuesta.SERIES){
            contadorSeries.put(series, 0);
        }

        analisis += "Encuestados por rango";
        for (Entry<String, Integer> entry : contadorRangos.entrySet()){
            analisis += "\t" + entry.getKey() + " - " + entry.getValue() + " Encuestados\n";
        }
        analisis += "\nFrecuencia más usada: ";
        String frecMU = "";
        int mayorFrec = -1;
        for (Entry<String, Integer> entry : contadorFrec.entrySet()) {
            if (entry.getValue() > mayorFrec) {
                frecMU = entry.getKey();
                mayorFrec = entry.getValue();
            }
        }
        analisis += frecMU;

        analisis += "\n\nSerie más vista: ";
        String serieMV = "";
        int mayorVis = -1;
        for (Entry<String, Integer> entry : contadorSeries.entrySet()) {
            if (entry.getValue() > mayorVis) {
                serieMV = entry.getKey();
                mayorVis = entry.getValue();
            }
        }
        analisis += serieMV;

        return analisis;
    }
}
