package com.dam.tests;

import java.util.ArrayList;


public class listest {
    public static void main(String[] args) {
        testLists();
    }
    public static void testLists(){
        ArrayList<Integer> listaEnt = new ArrayList<Integer>();
        for(int i = 1; i < 12; i+=2){
            listaEnt.add(i);
        }
        for (Integer entero : listaEnt) {
            System.out.print(entero + " ");
        }
    }
}
