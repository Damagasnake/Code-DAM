package com.dam.hashmaps;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Ex01Hashmaps {
    public static void main(String[] args) {
        TreeMap<Integer, String> alineacion = new TreeMap<>();
        alineacion.put(1, "Casillas");
        alineacion.put(3, "Pique");
        alineacion.put(5, "Puyol");
        alineacion.put(6, "Iniesta");
        alineacion.put(7, "Villa");
        alineacion.put(8, "Xavi Hernandez");
        alineacion.put(11, "Capdevila");
        alineacion.put(14, "Xavi Alonso");
        alineacion.put(15, "Ramos");
        alineacion.put(16, "Busquets");
        alineacion.put(17, "Pedrito");

        salidaCampo(alineacion);
        provocaFalta(alineacion);
        finPartido(alineacion);
    }

    public static void salidaCampo(TreeMap<Integer, String> alineacion) {
        for (Entry<Integer, String> iterator : alineacion.entrySet()) { // Uso entry hace print de [CLAVE - VALOR]
            System.out.println("Con el numero " + iterator.getKey() + "... " + iterator.getValue());
        }
    }

    public static void provocaFalta(TreeMap<Integer, String> alineacion) {
        boolean existe = false;
        Scanner sc = new Scanner(System.in);
        
        do {
            System.out.println("Introduce un dorsal");
            int dorsal = Integer.parseInt(sc.nextLine());
            if (alineacion.containsKey(dorsal)) {
                existe = true;
                String eliminar = alineacion.get(dorsal);
                alineacion.remove(dorsal);
                System.out.println("El jugador " + eliminar + "ha sido expulsado");
            }else{
                System.out.println("El dorsal no existe");
            }
        } while (!existe);
        sc.close();
    }
    public static void finPartido(TreeMap<Integer, String> alineacion){
        alineacion.clear();
        if (alineacion.isEmpty()) {
            System.out.println("El partido ha finalizado");   
        }
    }
}
