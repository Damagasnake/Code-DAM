package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;



import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Textos;

public class VPEmpresas extends JFrame implements IVFrame {
	
	public static final int ALTO = 600;
	public static final int ANCHO = 500;

	
	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;
	
	private JScrollPane scrpContenedor;
	private JMenuItem mntmSalir;
	private JMenuItem mntmConsulta; // Panel Consulta
	private JMenuItem mntmRegistrar; // Panel Registrar

	public VPEmpresas() {
		super("** E M P R E S A S **");
		
		configurarVentana();
		crearMenu();
	}
	// TODO: Constructor
	
	@Override
	public void configurarVentana() {
		setSize(ANCHO, ALTO);

		// setLayout ANTES de add(), si no el nuevo BorderLayout no conoce CENTER
		getContentPane().setLayout(new BorderLayout(0, 0));
		scrpContenedor = new JScrollPane();
		getContentPane().add(scrpContenedor, BorderLayout.CENTER);

		centrarVentana();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		insetsR = this.getInsets().right;
		insetsL = this.getInsets().left;
		insetsT = this.getInsets().top;
		insetsB = this.getInsets().bottom;
	}
	
	private void centrarVentana() {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

	}
	
	public void crearMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuH = menuBar.getPreferredSize().height;
		
		JMenu mnMantenimiento = new JMenu(Textos.MN_MANTENIMIENTO);
		menuBar.add(mnMantenimiento);
		
		mntmConsulta = new JMenuItem(Textos.MNTM_CONSULTAR);
		mnMantenimiento.add(mntmConsulta);
		
		mntmRegistrar = new JMenuItem(Textos.MNTM_REGISTRAR);
		mnMantenimiento.add(mntmRegistrar);
	}

	@Override
	public void crearComponentes() {
		scrpContenedor = new JScrollPane();
		getContentPane().add(scrpContenedor, BorderLayout.CENTER);

	}

	@Override
	public void setControlador(ControladorEmpresas ce) {
		// TODO: Asignar el controlador como escuchador a los componentes que lo requieran
		mntmConsulta.addActionListener(ce);
		mntmRegistrar.addActionListener(ce);
	}

	@Override
	public void hacerVisible() {
		setVisible(true);

	}

	@Override
	public void cargarPanel(JPanel panel) {
		scrpContenedor.setViewportView(panel);
		scrpContenedor.revalidate();
		scrpContenedor.repaint();
	}

	public JMenuItem getMntmSalir() {
		return mntmSalir;
	}

	public JMenuItem getMntmConsulta() {
		return mntmConsulta;
	}

	public JMenuItem getMntmRegistrar() {
		return mntmRegistrar;
	}
	

	// TODO: identificar los componentes que deban ser escuchados para poder diferenciar en el controlador

}
