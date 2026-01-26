package com.dam.hashmaps;
import java.util.Map.Entry;
import java.util.HashMap;

public class hashmapstest {
    public static void main(String[] args) {
        HashMap<Integer, String> mapacolores = new HashMap<Integer, String>();
        mapacolores.put(1, "Rojo");
        mapacolores.put(2, "Verde");
        mapacolores.put(3, "Naranja");

        for (Integer clave : mapacolores.keySet()) {
            System.out.println(clave + " - " + mapacolores.get(clave)); // al hacer get.clave obtienes el valor asociado a esa clave NO la clave
        }
        for (Entry<Integer,String> registro: mapacolores.entrySet()) { // Entry es cada grupo p.e 1,"Rojo" | 2, "Verde"
            System.out.println(registro.getKey() + registro.getValue());
        }
        for (String valor : mapacolores.values()) {
            System.out.println(valor);
        }
    }
}
