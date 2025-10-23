package com.dam;
import com.dam.pojo.Helipuerto;
import java.util.Scanner;
public class helipuertomain {
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
//---------------------------------H1-----------------------------------
    System.out.println("**Bienvenido a  tu comparador de helipuertos**");

    System.out.println("Introduce el nombre del helipuerto");
    String nombreH1 = sc.nextLine();

    System.out.println("Introduce el radio de tu helipuerto");
    double radioH1 = Double.parseDouble(sc.nextLine());
    Helipuerto helipuerto1 = new Helipuerto(nombreH1, radioH1);

//-------------------------------H2-------------------------------------

    System.out.println("Introduce el nombre del segundo helipuerto");
    String nombreH2 = sc.nextLine();

    System.out.println("Introduce el radio de tu segundo helipuerto");
    double radioH2 = Double.parseDouble(sc.nextLine());
    Helipuerto helipuerto2 = new Helipuerto(nombreH2, radioH2);
    sc.close();
    System.out.println("El nombre de tu primer helipuerto es");
}
}
