package com.dam.actividades.playlist;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.dam.actividades.playlist.Cancion;

public class playlistmain {
    public static void main(String[] args) {
        ArrayList<Cancion> playlist = new ArrayList<>();
    }
    public static ArrayList<Cancion> AddCancion(){
        Scanner sc = new Scanner(System.in);
        boolean tituloOK = false;

        try {
            System.out.println("Introduce el titulo de tu cancion");
        String titulo = sc.nextLine();
        } catch (InputMismatchException e) {

        }
        System.out.println("Introduce la el artista/grupo de tu cancion");
        String artista = sc.nextLine();
        System.out.println("Introduce la duracion en segundos de la cancion");
        int duracion = Integer.parseInt(sc.nextLine());


    }
}
