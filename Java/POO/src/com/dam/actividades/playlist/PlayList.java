package com.dam.actividades.playlist;

import java.util.ArrayList;
import java.util.Scanner;

import javax.management.InvalidAttributeValueException;

public class PlayList {
    private static final int DURMIN = 10;
    private static final int DURMAX = 600;
    private static final int firstPos = 1;

    static ArrayList<Cancion> playList;
    static Scanner sc;

    public static void main(String[] args) {
        playList = new ArrayList<Cancion>();
        sc = new Scanner(System.in);
        System.out.println("** Bienvenido a la aplicación TU PLAYLIST **");
        String opcion;
        do {
            System.out.println("Indica la acción que deseas realizar:\r\n"
                    + "1 – para añadir una canción a la playlist\r\n"
                    + "2 – para eliminar una canción de la playlist\r\n"
                    + "3 – para mostrar el listado\r\n"
                    + "4 – para reproducir una canción\r\n"
                    + "5 – para reproducir toda la lista\r\n"
                    + "6 – para consultar el tiempo de duración de la playlist\r\n"
                    + "0 – para salir de la aplicación");
            opcion = sc.nextLine();
            ejecutarAccion(opcion);
        } while (!opcion.equals("0"));
        sc.close();
    }

    private static void ejecutarAccion(String opcion) {
        switch (opcion) {
            case "0":
                System.out.println("** CERRANDO LA PLAYLIST **");
                break;
            case "1":
                addSong();
                break;
            case "2":
                borrarCancion();
                break;
            case "3":
                showPl();
                break;
            case "4":
                playSong();
                break;
            case "5":
                pPlaylist();
                break;
            case "6":
                pDuracion();
                break;
            default:
                System.out.println("La opción introducida no es válida");
                break;
        }
    }

    private static int solicitarDuracion() {
        int duracion = 0;
        while (duracion == 0) {
            try {
                System.out.println("Introduce la duracion");
                duracion = Integer.parseInt(sc.nextLine());
                if(duracion < DURMIN || duracion > DURMAX){
                    throw new InvalidAttributeValueException("El valor no es valido");
                }
            } catch (NumberFormatException e) {
                System.out.println("El valor no es entero");
            } catch (Exception e) {
                System.out.println("Se ha producido un error desconocido HUYE!");
            }
        }

        return duracion;
    }

    private static void addSong() {
        String titulo = solicitarCadena("Introduce el titulo");
        int duracion = solicitarDuracion();
        String artista = solicitarCadena("Introduce el grupo");

        playList.add(new Cancion(titulo, duracion, artista));
        System.out.println("Cancion añadida " + playList.size() + " canciones en tu playlist");
    }

    private static String solicitarCadena(String mensaje) {
        String cadena = "";
        while (cadena.isEmpty()) {
            System.out.println(mensaje);
            cadena = sc.nextLine().trim();
            if (cadena.isEmpty()) {
                System.out.println("ERROR: Valor Vacio");
            }
        }
        return cadena;
    }
    public static void showPl(){
        System.out.println("Canciones");
        for (int i = 0; i < playList.size(); i++) {
            System.out.println((i + 1) + " " + playList.get(i));
        }
    }
    private static void playSong(){
        int pos = solicitarPos();
        System.out.println("Se esta reproduciendo " + playList.get(pos - 1));
    }
    private static int solicitarPos(){
        int pos = 0;
        if (playList.isEmpty()) {
            System.out.println("playlist Vacia");
        }
        else{
            try {
                System.out.println("Introduce la posicion");
                pos = Integer.parseInt(sc.nextLine());
                if(pos < firstPos || pos > playList.size()){
                    throw new InvalidAttributeValueException("El valor no es valido");
                }
            } catch (NumberFormatException e) {
                System.out.println("El valor no es entero");
            } catch (Exception e) {
                System.out.println("Se ha producido un error desconocido HUYE!");
            }
        }
        return pos;
    }
    private static void pPlaylist(){
        if(playList.isEmpty()){
            System.out.println("Playlist Vacia añade temazos");
        }else{
            for (Cancion cancion : playList) {
                System.out.println("Reproduciendo " + cancion);
            }
        }
    }
    private static void pDuracion(){
        int durTotal = 0;
        for (Cancion cancion : playList) {
            durTotal += cancion.getDuracion();
        }
        System.out.println("Duracion de la playlist " + durTotal);
    }
    private static void borrarCancion(){
        if (playList.isEmpty()) {
            System.out.println("No hay canciones");
        }else{
            String titulo = solicitarCadena("Introduce el titulo de la cancion");
            String artista = solicitarCadena("Introduce el artista");

            int posBorr = -1;
            for (int i = 0; i < playList.size() && posBorr == -1; i++) {
                if (playList.get(i).getTitulo().equals(titulo) && playList.get(i).getArtista().equals(artista)) {
                    System.out.println("Cancion eliminada");
                }else{
                    System.out.println("No he encontrado la cancion");
                }
            }
        }
    }
}
// Pines del sensor HC-SR04
const int trigPin = 6;
const int echoPin = 5;

// Pines de los LEDs
const int ledRojo = 10;   // Cerca
const int ledAzul = 9;    // Medio
const int ledVerde = 8;   // Lejos

// Umbrales de distancia (en cm)
const int distanciaCerca = 15;
const int distanciaMedia = 30;

void setup() {
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(ledRojo, OUTPUT);
  pinMode(ledAzul, OUTPUT);
  pinMode(ledVerde, OUTPUT);
  Serial.begin(9600);
}

float medirDistancia() {
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  
  long duracion = pulseIn(echoPin, HIGH);
  return duracion * 0.034 / 2;
}

void loop() {
  float distancia = medirDistancia();
  
  // Apagar todos
  digitalWrite(ledRojo, LOW);
  digitalWrite(ledAzul, LOW);
  digitalWrite(ledVerde, LOW);
  
  // Encender según distancia
  if (distancia < distanciaCerca) {
    digitalWrite(ledRojo, HIGH);
  } else if (distancia < distanciaMedia) {
    digitalWrite(ledAzul, HIGH);
  } else {
    digitalWrite(ledVerde, HIGH);
  }
  
  delay(200);
}
