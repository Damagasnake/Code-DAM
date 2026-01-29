package com.dam.hashmaps.RepasoExa;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class QuizVerbosIrregulares {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        
        // HashMap de verbos irregulares
        HashMap<String, String> verbosIrregulares = new HashMap<>();
        verbosIrregulares.put("Ser/Estar", "Be-Was/Were-Been");
        verbosIrregulares.put("Empezar", "Begin-Began-Begun");
        verbosIrregulares.put("Traer", "Bring-Brought-Brought");
        verbosIrregulares.put("Venir", "Come-Came-Come");
        verbosIrregulares.put("Hacer", "Do-Did-Done");
        verbosIrregulares.put("Beber", "Drink-Drank-Drunk");
        verbosIrregulares.put("Comer", "Eat-Ate-Eaten");
        verbosIrregulares.put("Sentir", "Feel-Felt-Felt");
        verbosIrregulares.put("Encontrar", "Find-Found-Found");
        verbosIrregulares.put("Obtener", "Get-Got-Got/Gotten");
        verbosIrregulares.put("Dar", "Give-Gave-Given");
        verbosIrregulares.put("Ir", "Go-Went-Gone");
        verbosIrregulares.put("Haber/Tener", "Have-Had-Had");
        verbosIrregulares.put("Saber/Conocer", "Know-Knew-Known");
        verbosIrregulares.put("Aprender", "Learn-Learnt-Learnt");
        verbosIrregulares.put("Dejar/Salir", "Leave-Left-Left");
        verbosIrregulares.put("Producir/Hacer", "Make-Made-Made");
        verbosIrregulares.put("Poner", "Put-Put-Put");
        verbosIrregulares.put("Decir", "Say-Said-Said");
        verbosIrregulares.put("Ver", "See-Saw-Seen");
        verbosIrregulares.put("Dormir", "Sleep-Slept-Slept");
        verbosIrregulares.put("Coger/Tomar", "Take-Took-Taken");
        verbosIrregulares.put("Contar", "Tell-Told-Told");
        verbosIrregulares.put("Pensar", "Think-Thought-Thought");
        
        // HashMap de orden de verbos
        HashMap<Integer, String> ordenVerbos = new HashMap<>();
        ordenVerbos.put(1, "Ser/Estar");
        ordenVerbos.put(2, "Empezar");
        ordenVerbos.put(3, "Traer");
        ordenVerbos.put(4, "Venir");
        ordenVerbos.put(5, "Hacer");
        ordenVerbos.put(6, "Beber");
        ordenVerbos.put(7, "Comer");
        ordenVerbos.put(8, "Sentir");
        ordenVerbos.put(9, "Encontrar");
        ordenVerbos.put(10, "Obtener");
        ordenVerbos.put(11, "Dar");
        ordenVerbos.put(12, "Ir");
        ordenVerbos.put(13, "Haber/Tener");
        ordenVerbos.put(14, "Saber/Conocer");
        ordenVerbos.put(15, "Aprender");
        ordenVerbos.put(16, "Dejar/Salir");
        ordenVerbos.put(17, "Producir/Hacer");
        ordenVerbos.put(18, "Poner");
        ordenVerbos.put(19, "Decir");
        ordenVerbos.put(20, "Ver");
        ordenVerbos.put(21, "Dormir");
        ordenVerbos.put(22, "Coger/Tomar");
        ordenVerbos.put(23, "Contar");
        ordenVerbos.put(24, "Pensar");
        
        // HashMap de intentos
        HashMap<String, Integer> verbosIntentos = new HashMap<>();
        for (String verbo : verbosIrregulares.keySet()) {
            verbosIntentos.put(verbo, 0);
        }
        
        int aciertos = 0;
        int fallos = 0;
        String continuar = "Si";
        
        while (continuar.equalsIgnoreCase("Si")) {
            // Generar número aleatorio y obtener verbo no preguntado
            int numeroAleatorio;
            String verbo;
            
            do {
                numeroAleatorio = random.nextInt(24) + 1; // 1 a 24
                verbo = ordenVerbos.get(numeroAleatorio);
                
                if (verbosIntentos.get(verbo) != 0) {
                    System.out.println("El verbo " + verbo + " ya se ha preguntado");
                }
            } while (verbosIntentos.get(verbo) != 0);
            
            // Registrar intento
            verbosIntentos.put(verbo, 1);
            
            // Pedir respuesta al usuario
            System.out.println("Introduce las tres formas verbales en inglés separadas por un guión del verbo " + verbo);
            String respuesta = sc.nextLine();
            
            // Comprobar respuesta
            if (respuesta.equals(verbosIrregulares.get(verbo))) {
                aciertos++;
                System.out.println("¡¡VERBO CORRECTO!!");
            } else {
                fallos++;
                System.out.println("¡¡VERBO INCORRECTO!!");
                System.out.println("Las tres formas verbales correspondientes a " + verbo + " son: " + verbosIrregulares.get(verbo));
            }
            
            // Preguntar si desea continuar
            System.out.println("¿Deseas probar con otro verbo?");
            continuar = sc.nextLine();
        }
        
        // Mostrar resultados finales
        int totalProbados = aciertos + fallos;
        System.out.println("Has probado con " + totalProbados + " verbos, has respondido correctamente " + aciertos + " y has fallado " + fallos);
        
        System.out.println("** Verbos intentados **");
        for (Map.Entry<String, Integer> entry : verbosIntentos.entrySet()) {
            if (entry.getValue() != 0) {
                System.out.println(entry.getKey() + ": " + verbosIrregulares.get(entry.getKey()));
            }
        }
        
        sc.close();
    }
}