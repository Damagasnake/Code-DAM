package com.dam.Libro;
public class Book {
    private String Titulo;
    private String Autor;
    private int pages;
    private boolean tapadura;

    public String getTitulo(){
        return Titulo;
    }
    public void setTitulo(String value){
        Titulo = value;
    }
    public String getAutor(){
        return Autor;
    }
    public void setAutor(String value){
        Autor = value;
    }
    public int getPages(){
        return pages;
    }
    public void setPages(int value){
        pages = value;
    }
    public boolean isTapadura(){
        return tapadura;
    }
    public void setTapadura(boolean value){
        tapadura = value;
    }
}
