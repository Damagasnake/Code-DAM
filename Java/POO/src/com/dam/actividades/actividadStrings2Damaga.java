package com.dam.actividades;
import java.util.Scanner;

public class actividadStrings2Damaga {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nveces = 0;
        int i = 0;
        System.out.println("Introduce una String");
        String palabraUser = sc.nextLine();
        System.out.println("Introduce char a encontrar");
        String charaencontrar = sc.nextLine();
        while (i < palabraUser.length()) {
            if (palabraUser.charAt(i) == charaencontrar.charAt(0)) {
                nveces++;
                i++;       
            }else{
                i++;
            }
        }
        System.out.println(nveces);
        sc.close();
    }
}
