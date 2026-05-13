package com.michelin.model.data;

public class Restaurante {
	
	public static final String[] REGIONES = {"Andalucía", "Aragón", "Asturias", "Islas Baleares", "Cantabria", "Islas Canarias", 
			"Castilla - La Mancha", "Castilla y León", "Cataluña", "Galicia", "Extremadura"	, "Madrid", "Murcia", 
			"Navarra", "País Vasco", "La Rioja", "Comunidad Valenciana"};
	
	public static final String[] DISTINCIONES = {"1 estrella", "2 estrellas", "3 estrellas"};
	
	public static final String[] TIPOS_COCINA = {"Creativa", "Moderna", "Tradicional", "Regional", "Fusión"};
	
	private int id;
	private String nombre;
	private String region;
	private String ciudad;
	private int distincion;
	private String direccion;
	private double precioMin;
	private double precioMax;
	private String cocina;
	private String telefono;
	private String web;
	
	public Restaurante(int id, String nombre, String region, String ciudad, int distincion, String direccion,
			double precioMin, double precioMax, String cocina, String telefono, String web) {
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.ciudad = ciudad;
		this.distincion = distincion;
		this.direccion = direccion;
		this.precioMin = precioMin;
		this.precioMax = precioMax;
		this.cocina = cocina;
		this.telefono = telefono;
		this.web = web;
	}
	
	public Restaurante(String nombre, String region, String ciudad, int distincion, String direccion,
			double precioMin, double precioMax, String cocina, String telefono, String web) {
		this.nombre = nombre;
		this.region = region;
		this.ciudad = ciudad;
		this.distincion = distincion;
		this.direccion = direccion;
		this.precioMin = precioMin;
		this.precioMax = precioMax;
		this.cocina = cocina;
		this.telefono = telefono;
		this.web = web;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public int getDistincion() {
		return distincion;
	}

	public double getPrecioMin() {
		return precioMin;
	}

	public double getPrecioMax() {
		return precioMax;
	}

	public String getCocina() {
		return cocina;
	}

	public int getId() {
		return id;
	}

	public String getRegion() {
		return region;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getWeb() {
		return web;
	}
	
	
	
}
