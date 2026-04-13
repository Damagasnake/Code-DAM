package com.dam.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dam.control.CtrlESeries;
import com.dam.model.Encuesta;

public class OPCION1 extends JPanel implements IVPanels{
	
	private static final int WIDTH = Ppal.WIDTH - Ppal.insetsL - Ppal.insetsR;
	private static final int HEIGHT = Ppal.HEIGHT - Ppal.insetsT - Ppal.insetsB - Ppal.menuH;
	
	public OPCION1() {
		setSize(WIDTH,HEIGHT);
		
		createComp();
	}
	
	public void createComp() {
		setLayout(null);

	}
	
	public void setCtrl(CtrlESeries c) {
		
	}
	
	public Encuesta getEncuesta() {
		return null;
	}
	
	public void clearE() {
		/*
		 * Component[] arrayComponentes = getComponents();
		
		for (Component component : arrayComponentes) {
			if (component instanceof JCheckBox) {
				((JCheckBox) component).setSelected(false);
			}
		}
		 * */
	}
	
	//public void mostrarMensaje(String mensaje) {
		//JOptionPane.showMessageDialog(this, mensaje, "Resultado de la operación", JOptionPane.INFORMATION_MESSAGE);
		
	//}
	
}
