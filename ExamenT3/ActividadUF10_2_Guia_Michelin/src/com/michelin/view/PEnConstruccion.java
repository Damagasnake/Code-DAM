package com.michelin.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class PEnConstruccion extends JPanel{
	
	private static final int ANCHO = VPRestaurantes.ANCHO 
			- VPRestaurantes.insetsL - VPRestaurantes.insetsR;
	private static final int ALTO = VPRestaurantes.ALTO 
			- VPRestaurantes.insetsT - VPRestaurantes.insetsB 
			- VPRestaurantes.menuH;

	public PEnConstruccion() {
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		setSize(ANCHO, ALTO);
		
		//Funcionalidad Consulta de Restaurantes en construcción
		JLabel lblEnConstruccion = new JLabel("En construcción");
		lblEnConstruccion.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEnConstruccion.setBounds(65, 173, 610, 20);
		add(lblEnConstruccion);
	}

}
