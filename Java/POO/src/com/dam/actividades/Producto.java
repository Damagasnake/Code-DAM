package com.dam.actividades;

public class Producto {
    String name;
    int cant;

    public Producto(String name, int cant) {
        this.name = name;
        this.cant = cant;
    }

    @Override
    public String toString() {
        return "Productos lista " + name + ", cantidad a comprar " + cant + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (cant != other.cant)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + cant;
        return result;
    }
}
