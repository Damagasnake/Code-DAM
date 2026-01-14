package com.dam.actividades;

import java.util.ArrayList;
import java.util.Scanner;
public class Productomain {
    public static void main(String[] args) {
        ArrayList<Producto> listaCompra = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int result = Accion(sc);

        switch (result) {
            case 1:
                AddProducto(listaCompra, sc);
                break;
            case 2:

                break;
            case 3:
                mostrarLista(listaCompra);
            case 4:
                break;
            case 5:
                borrarProd(listaCompra, sc);
                break;
            default:
                break;
        }
    }

    private static int Accion(Scanner sc) {
        boolean works = false;
        int result = 0;
        try {
            System.out.println("1 - Añadir producto a la lista de la compra");
            System.out.println("2 - Eliminar producto de la lista de la compra");
            System.out.println("3 - Mostrar la lista de la compra");
            System.out.println("4 - Para terminar");
            result = Integer.parseInt(sc.nextLine());
            if (result < 0 || result > 4) {
                throw new Exception("Introduce un num del 1 - 4");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    private static void AddProducto(ArrayList<Producto> p, Scanner sc) {
        System.out.println("Introduce un nombre de producto");
        String n = sc.nextLine();
        System.out.println("Introduce  la cantidad de los productos");
        int cantidad = Integer.parseInt(sc.nextLine());
        p.add(new Producto(n, cantidad));
    }

    private static String solNombre(Scanner sc) {
        String nombre = "";
        while (nombre.isEmpty()) {
            try {
                System.out.println("Introduce el nombre");
                nombre = sc.nextLine().toUpperCase().trim();
                if (nombre.isEmpty()) {
                    throw new Exception("Introduce un nombre");
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return nombre;
    }

    private static void mostrarLista(ArrayList<Producto> listaCompra) {
        System.out.println("\n Lista de la compra");
        for (int i = 0; i < listaCompra.size(); i++) {
            System.out.println(listaCompra);
        }
    }

    private static void borrarProd(ArrayList<Producto> listaCompra, Scanner sc) {
        String nombre = solNombre(sc);
        //int cantidad = solCant(sc);

    }
}