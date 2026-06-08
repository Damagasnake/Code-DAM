package com.dam.view;

import javax.swing.JPanel;

import com.dam.control.ControladorEmpresas;

public interface IVFrame {
	
	public void configurarVentana();
	
	public void crearComponentes();
	
	public void setControlador(ControladorEmpresas ce);
	
	public void hacerVisible();
	
	public void cargarPanel(JPanel panel);

}
