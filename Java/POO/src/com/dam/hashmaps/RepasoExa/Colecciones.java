package com.dam.hashmaps.RepasoExa;

import java.util.*;

public class Colecciones {

public static void main(String[] args) {

/* =======================
COLECCIONES (GENERAL)
======================= */

// List → permite duplicados, mantiene orden
List<String> lista = new ArrayList<>();
lista.add("A");
lista.add("B");
lista.add("A");

// Set → NO duplicados
Set<String> conjunto = new HashSet<>();
conjunto.add("A");
conjunto.add("B");
conjunto.add("A"); // no se añade

// Map → clave-valor (clave única)
Map<String, Integer> mapa = new HashMap<>();


/* =======================
HASHMAP
======================= */

// ❌ No mantiene orden
// ✔ Muy rápido
// ✔ Permite UNA clave null
// ✔ Permite valores null

HashMap<String, Integer> hashMap = new HashMap<>();

hashMap.put("Ana", 20); // Añadir
hashMap.put("Luis", 25);
hashMap.put("Ana", 30); // Machaca valor
hashMap.put(null, 99); // Clave null permitida

hashMap.get("Ana"); // Obtener valor (30)
hashMap.containsKey("Luis"); // true
hashMap.containsValue(25); // true
hashMap.remove("Luis"); // Elimina clave
hashMap.size(); // Tamaño
hashMap.isEmpty(); // ¿vacío?

// Recorrer HashMap (FORMA CORRECTA DE EXAMEN)
for (Map.Entry<String, Integer> e : hashMap.entrySet()) {
e.getKey(); // clave
e.getValue(); // valor
}


/* =======================
TREEMAP
======================= */

// ✔ Mantiene claves ORDENADAS
// ❌ Más lento
// ❌ NO permite clave null

TreeMap<Integer, String> treeMap = new TreeMap<>();

treeMap.put(3, "C");
treeMap.put(1, "A");
treeMap.put(2, "B");
// treeMap.put(null, "X"); // ❌ NullPointerException

treeMap.get(1); // "A"
treeMap.firstKey(); // Primera clave
treeMap.lastKey(); // Última clave

// Siempre recorre en orden
for (Integer k : treeMap.keySet()) {
treeMap.get(k);
}


/* =======================
MÉTODOS IMPORTANTES MAP
======================= */

mapa.put("A", 1); // Añadir / reemplazar
mapa.get("A"); // Obtener valor
mapa.containsKey("A"); // ¿existe clave?
mapa.containsValue(1); // ¿existe valor?
mapa.remove("A"); // Eliminar
mapa.size(); // Tamaño
mapa.clear(); // Vaciar

mapa.keySet(); // Devuelve Set de claves
mapa.values(); // Devuelve Collection de valores
mapa.entrySet(); // Devuelve Set<Entry>


/* =======================
ERRORES TÍPICOS EXAMEN
======================= */

// mapa.get(0); // ❌ Map NO tiene índice
// for(String s : mapa) // ❌ Map NO es Iterable

// ✔ Correcto
for (String clave : mapa.keySet()) {
mapa.get(clave);
}
}
}