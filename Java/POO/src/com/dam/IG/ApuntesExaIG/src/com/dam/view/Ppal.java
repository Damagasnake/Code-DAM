package com.dam.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.dam.control.CtrlESeries;

public class Ppal extends JFrame {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;
	
	public static int insetsR;
	public static int insetsL;
	public static int insetsT;
	public static int insetsB;
	public static int menuH;
	
	private JMenuItem op1;
	private JMenuItem op2;
	
	private JScrollPane scrlCont;
	
	public Ppal() {
		configWindow();
		
		createComp();
	}

	private void configWindow() {
		setTitle("Encuestas");
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setSize(WIDTH,HEIGHT);
		
		insetsR = this.getInsets().right;
		insetsL = this.getInsets().left;
		insetsT = this.getInsets().top;
		insetsB = this.getInsets().bottom;
		
		centerWindow();
		
		createMenu();
	}
	
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuH = menuBar.getPreferredSize().height;
		
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		
		op1 = new JMenuItem("OP 1");
		menu.add(op1);
		
		op2 = new JMenuItem("OP 2");
		menu.add(op2);
	}

	private void centerWindow() {
		Dimension pantalla = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = new Dimension(WIDTH, HEIGHT);
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	private void createComp() {
			
		scrlCont = new JScrollPane();
		getContentPane().add(scrlCont, BorderLayout.CENTER);
	}
	
	public void setCtrl(CtrlESeries c) {
		op1.addActionListener(c);
		op2.addActionListener(c);
	}

	public void showWindow() {
		setVisible(true);
	}
	
	public void runPanel(JPanel p) {
		scrlCont.setViewportView(p);
	}
	
	public JMenuItem getOp1() {
		return op1;
	}
	
	public JMenuItem getOp2() {
		return op2;
	}
}
