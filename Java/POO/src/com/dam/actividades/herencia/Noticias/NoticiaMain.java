package com.dam.actividades.herencia.Noticias;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NoticiaMain {
    static void main(String[] args) {
        List<Noticia> noticiaList = new ArrayList<>();
        addNoticia(noticiaList);
        ModEstado(noticiaList);
        mostrarNoticias(noticiaList);
        borrarNoticia(noticiaList);
    }

    public static void addNoticia(List<Noticia> noticiaList) {
        Scanner sc = new Scanner(System.in);

        System.out.println("De que tipo es tu Noticia (Economia/Politica/Deportes)");
        String nNoticia = sc.nextLine().trim();

        if (!nNoticia.equalsIgnoreCase("Economia") && !nNoticia.equalsIgnoreCase("Politica") && !nNoticia.equalsIgnoreCase("Deportes")) {
            System.out.println("El valor introducido no es correcto");
            return;
        }

        // Comunes
        String titulo = pedirNoVacio(sc, "Introduce el titulo de tu noticia");
        String fecha = pedirNoVacio(sc, "Introduce la fecha de tu noticia");

        try {
            System.out.println("Introduce el titulo de tu noticia");
            titulo = sc.nextLine();
            if (titulo.isEmpty()) {
                throw new Exception("Cadena Vacia");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("Introduce la fecha de tu noticia");
            fecha = sc.nextLine();
            if (fecha.isEmpty()) {
                throw new Exception("Fecha vacia");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (nNoticia.equalsIgnoreCase("Economia")) {
            System.out.println("Introduce el sector de tu noticia");
            String sector = sc.nextLine();

            noticiaList.add(new NoticiaEconomia(titulo, fecha, sector));

        } else if (nNoticia.equalsIgnoreCase("Politica")) {
            System.out.println("Introduce el Partido Politico de tu noticia");
            String partido = sc.nextLine();

            noticiaList.add(new NoticiaPolitica(titulo, fecha, partido));

        } else { // Deportes
            System.out.println("Introduce el deporte de tu noticia");
            String deporte = sc.nextLine();
            noticiaList.add(new NoticiaDeportes(titulo, fecha, deporte));
        }
    }

    private static String pedirNoVacio(Scanner sc, String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String s = sc.nextLine().trim();

            if (!s.isEmpty()) {
                return s; // válido
            }
            System.out.println("No puede estar vacío. Intenta otra vez.");
        }
    }

    public static void ModEstado(List<Noticia> noticiaList) {
        Scanner sc = new Scanner(System.in);
        String titulo = pedirNoVacio(sc, "Introduce el titulo de la noticia");
        String fecha = pedirNoVacio(sc, "Introduce la fecha de la noticia");
        for (Noticia n : noticiaList) {
            int i = noticiaList.indexOf(n);
            if (noticiaList.get(i).getTitulo().equals(titulo) && noticiaList.get(i).getFecha().equals(fecha)) {
                System.out.println("La noticia que buscas es: " + n.getTitulo() + " - " + n.getFecha());
                System.out.println("Introduce el nuevo estado de la noticia " + n.ESTADO[0] + " - " + n.ESTADO[1] + " - " + n.ESTADO[2]);
                String estado = sc.nextLine();
                noticiaList.get(i).cambiarEstado();
                System.out.println("Estado modificado");
                if (!(estado.equals(n.ESTADO[0]) || estado.equals(n.ESTADO[1]) || estado.equals(n.ESTADO[2]))) {
                    System.out.println("El estado no es correcto");
                }
            }
        }
    }

    public static void mostrarNoticias(List<Noticia> noticiaList) {
        for (Noticia n : noticiaList) {
            System.out.println(n.toString());
        }
    }

    public static void borrarNoticia(List<Noticia> noticiaList) {
        Scanner sc = new Scanner(System.in);
        String titulo = pedirNoVacio(sc, "Introduce el titulo de la noticia");
        String fecha = pedirNoVacio(sc, "Introduce la fecha de la noticia");
        for (Noticia n : noticiaList) {
            int i = noticiaList.indexOf(n);
            if (noticiaList.get(i).getTitulo().equals(titulo) && noticiaList.get(i).getFecha().equals(fecha)) {
                noticiaList.remove(i);
                System.out.println("Noticia eliminada");
            }
        }
    }
}
