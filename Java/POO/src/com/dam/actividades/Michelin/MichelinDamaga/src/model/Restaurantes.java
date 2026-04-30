package model;

public class Restaurantes {
    private int id;
    private String nombre;
    private String region;
    private String ciudad;
    private int distincion;
    private String direccion;
    private double maxPrice;
    private double minPrice;
    private String cocina;
    private String tlf;
    private String web;

    public Restaurantes() {
    }

    public Restaurantes(int id, String nombre, String region, String ciudad, int distincion, double maxPrice, double minPrice) {
        this(id, nombre, region, ciudad, distincion, null, maxPrice, minPrice, null, null, null);
    }

    public Restaurantes(int id, String nombre, String region, String ciudad, int distincion, String direccion,
                        double maxPrice, double minPrice, String cocina, String tlf, String web) {
        this.id = id;
        this.nombre = nombre;
        this.region = region;
        this.ciudad = ciudad;
        this.distincion = distincion;
        this.direccion = direccion;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.cocina = cocina;
        this.tlf = tlf;
        this.web = web;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getDistincion() {
        return distincion;
    }

    public void setDistincion(int distincion) {
        this.distincion = distincion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getCocina() {
        return cocina;
    }

    public void setCocina(String cocina) {
        this.cocina = cocina;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "Restaurantes{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", region='" + region + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", distincion=" + distincion +
                ", direccion='" + direccion + '\'' +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", cocina='" + cocina + '\'' +
                ", tlf='" + tlf + '\'' +
                ", web='" + web + '\'' +
                '}';
    }
}
