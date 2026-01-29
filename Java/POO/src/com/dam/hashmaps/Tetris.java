package com.dam.hashmaps;

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
public class Tetris {
    static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        HashMap<String, Integer> TablaTT = new HashMap<>();
        boolean cont = true;
        do {
            System.out.println("Introduce un numero (1 -> Nuevo Resultado | 2 -> Mostrar puntuaciones | 0 -> Salir)");
            int eleccion = Integer.parseInt(sc.nextLine());
            if (eleccion == 1) {
                op01(TablaTT);
            } else if (eleccion == 2) {
                op02(TablaTT);
            } else {
                cont = false;
                System.out.println("Ejecucion terminada");
            }
        } while (cont);
        sc.close();
    }

    public static void op01(HashMap<String,Integer> TablaTT){
        System.out.println("Introduce el nick del jugador");
        String nick = sc.nextLine();
        System.out.println("Introduce tu puntuacion");
        int puntuacion = Integer.parseInt(sc.nextLine());
        if (!TablaTT.containsKey(nick)) {
            TablaTT.put(nick, puntuacion);
            System.out.println("Puntuacion añadida");
        }else{
            int nuevaP = TablaTT.get(nick);
            if (puntuacion > nuevaP) {
                TablaTT.put(nick, puntuacion);
                System.out.println("Nuevo Record");
            }else{
                System.out.println("Intentalo otra vez!");
            }
        }
    }

    public static void op02(HashMap<String, Integer> TablaTT) {
        TreeMap<String,Integer> sort = new TreeMap<>(TablaTT);
        for (Entry<String,Integer> iterar : sort.entrySet()) {
            System.out.println(iterar.getKey() + "-> " + iterar.getValue());
        }
    }
}
