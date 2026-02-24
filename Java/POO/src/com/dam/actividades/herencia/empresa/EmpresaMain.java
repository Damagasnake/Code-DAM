package com.dam.actividades.herencia.empresa;

import java.util.Scanner;

public class EmpresaMain {
    static Empresa empresa;
    static Scanner sc;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String nombre = SolicitarCadenaNoVacia("Introduce el nombre de la empresa");
        empresa.getNombre();
    }
    private static String SolicitarCadenaNoVacia(String mensaje){
        String cadena = "";
        do {
            
        } while (condition);
    }
    private static void InfoEmp(){
        if (empresa.getListaEm().isEmpty()) {
            System.out.println("No hay empleados en " + empresa.getNombre());
        }else{
            System.out.println(empresa);
            System.out.println("Salario total " + empresa.calcSal());
        }
    }
}
