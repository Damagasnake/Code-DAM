package com.michelin.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Texto;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class VPRestaurantes extends JFrame {
	
	public static final int ALTO = 500;
	public static final int ANCHO = 750;
	
	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;
	
	private JMenuItem mntmConsulta;
	private JMenuItem mntmSalir;
	private JScrollPane scrpContenedor;
	private JMenuItem mntmAlta;
	private JMenuItem mntmModif;
	
	public VPRestaurantes() {
		super("* * G U I A  M I C H E L I N * *");
		
		crearMenu();
		
		configurarVentana();
	}

	private void crearMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuH = menuBar.getPreferredSize().height;
		
		JMenu mnMantenimiento = new JMenu(Texto.MN_MTO);
		menuBar.add(mnMantenimiento);
		
		mntmConsulta = new JMenuItem(Texto.MNTM_CONSULTA);
		mnMantenimiento.add(mntmConsulta);
		
		mntmAlta = new JMenuItem(Texto.MNTM_REGISTRO);
		mnMantenimiento.add(mntmAlta);
		
		mntmModif = new JMenuItem(Texto.MNTM_MODIFICACION);
		mnMantenimiento.add(mntmModif);
		
		mntmSalir = new JMenuItem(Texto.MNTM_SALIR);
		menuBar.add(mntmSalir);
		
	}
	
	private void configurarVentana() {
		setSize(ANCHO, ALTO);
		insetsR = this.getInsets().right;
		insetsL = this.getInsets().left;
		insetsT = this.getInsets().top;
		insetsB = this.getInsets().bottom;
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		scrpContenedor = new JScrollPane();
		getContentPane().add(scrpContenedor, BorderLayout.CENTER);
		
		centrarVentana();
		
		
	}

	private void centrarVentana() {	
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();               
		              
		setLocation((pantalla.width - ANCHO) / 2,  (pantalla.height - ALTO) / 2);
	}
	
	public void hacerVisible() {
		setVisible(true);
		
	}

	public void setControlador(RestaurantesListener controlador) {
		mntmConsulta.addActionListener(controlador);
		mntmAlta.addActionListener(controlador);
		mntmModif.addActionListener(controlador);
		mntmSalir.addActionListener(controlador);
		
	}

	public JMenuItem getMntmConsulta() {
		return mntmConsulta;
	}

	public JMenuItem getMntmAlta() {
		return mntmAlta;
	}

	public JMenuItem getMntmModif() {
		return mntmModif;
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public void confirmarSalida() {
		int opcion = JOptionPane.showConfirmDialog(this, 
				Texto.MSJ_CONFIR_SALIR,
				Texto.TIT_CONFIRM,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		
		if (opcion == JOptionPane.YES_OPTION) {
			System.exit(0);
		} 
		
	}

	public void cargarPanel(JPanel panel) {
		scrpContenedor.setViewportView(panel);
		
	}

}
