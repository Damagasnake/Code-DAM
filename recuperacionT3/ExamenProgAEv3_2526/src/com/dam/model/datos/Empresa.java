package com.dam.model.datos;

public class Empresa {

	private String cif;
	private String razonSocial;
	private String domicilio;
	private String representante;
	private String correoRL;
	private String convenio;
	private int numEmpleados;
	private String telefono;
	private String web;

	public Empresa(String cif, String razonSocial, String domicilio, String representante, String correoRL,
			String convenio, int numEmpleados, String telefono, String web) {
		this.cif = cif;
		this.razonSocial = razonSocial;
		this.domicilio = domicilio;
		this.representante = representante;
		this.correoRL = correoRL;
		this.convenio = convenio;
		this.numEmpleados = numEmpleados;
		this.telefono = telefono;
		this.web = web;
	}

	public String getCif() {
		return cif;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public String getRepresentante() {
		return representante;
	}

	public String getCorreoRL() {
		return correoRL;
	}

	public String getConvenio() {
		return convenio;
	}

	public int getNumEmpleados() {
		return numEmpleados;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getWeb() {
		return web;
	}

	public String traducirConvenio() {
		if (convenio.equals("SI")) {
			return "Firmado";
		}
		return "Pendiente";
	}

	public static boolean validarCif(String cif) {
		if (cif == null || cif.length() != 9 || !Character.isLetter(cif.charAt(0))) {
			return false;
		}
		for (int i = 1; i <= 7; i++) {
			if (!Character.isDigit(cif.charAt(i))) {
				return false;
			}
		}
		return Character.isLetterOrDigit(cif.charAt(8));
	}

	public static boolean validarCorreo(String email) {
		if (email == null || email.trim().isEmpty() || email.contains(" ")) {
			return false;
		}
		int arroba = email.indexOf("@");
		if (arroba == -1 || arroba != email.lastIndexOf("@")) {
			return false;
		}
		String dominio = email.substring(arroba + 1);
		return dominio.length() >= 3 && dominio.contains(".");
	}

	public static boolean validarTelefono(String telefono) {
		if (telefono == null || telefono.length() != 9) {
			return false;
		}
		char inicio = telefono.charAt(0);
		return inicio == '6' || inicio == '7' || inicio == '8' || inicio == '9';
	}

	public static boolean validarWeb(String url) {
		if (url == null || url.trim().isEmpty() || url.contains(" ")) {
			return false;
		}
		if (!url.startsWith("www.")) {
			return false;
		}
		String dominio = url.substring(4);
		return !dominio.isEmpty() && dominio.contains(".");
	}

}
