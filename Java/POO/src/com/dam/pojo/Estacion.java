package com.dam.pojo;

public class Estacion {
    private String nombre;
    private String provincia;
    private String estado;
    private String remontes;
    private String pistas;
    private String km;

    public Estacion(String nombre, String provincia, String estado, String pistas, String remontes, String km){
        this.nombre = nombre;
        this.provincia = provincia;
        this.estado = estado;
        this.pistas = pistas;
        this.km = km;
        this.remontes = remontes;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre);
        sb.append("(");
        sb.append(provincia);
        sb.append(")");
        sb.append("\n");
        sb.append("Estado:");
        sb.append(estado);
        sb.append("\n");
        sb.append("Remontes:");
        sb.append(remontes);
        sb.append(",");
        sb.append("Pistas:");
        sb.append(pistas);
        sb.append(",");
        sb.append("KM esquiables:");
        sb.append(km);
        return sb.toString();
    }
    
    public double kmesq(){
        String [] kmmod = km.split("/");
        double k1 = Double.parseDouble(kmmod[0]);
        double k2 = Double.parseDouble(kmmod[1]);
        double res = (k1 * 100) / k2;
        return res;
    }
    public double kmab(){
        String [] kmmod = km.split("/");
        double k1 = Double.parseDouble(kmmod[0]);
        return k1;
    }
    public String getNombre(){
        return nombre;
    }
    public String getProvincia(){
        return provincia;
    }
    public String getEstado(){
        return estado;
    }
    public String getRemontes(){
        return remontes;
    }
    public String getPistas(){
        return pistas;
    }
    public String getKm(){
        return km;
    }
}