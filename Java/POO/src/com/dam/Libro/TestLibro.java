package com.dam.Libro;
public class TestLibro {
    public static void main(String[] args) {
        Book libro = new Book();
        libro.setAutor("Brandon Sanderson");
        libro.setPages(1100);
        libro.setTitulo("El camino de los reyes");
        System.out.println("Mi libro favorito es: " + libro.getTitulo() + "El autor es: " + libro.getAutor() + " tiene " + libro.getPages() + " paginas" );
    }
}
