package com.dam.view;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.dam.control.CtrlESeries;
import com.dam.model.Encuesta;

public class OPCION2 extends JPanel implements IVPanels{
	
	private JList<Encuesta> lstEncuestas;
	private DefaultListModel<Encuesta> lstModel;
	private JButton btnVer;
	private JLabel lblMsg;
	
	private static final int WIDTH = Ppal.WIDTH - Ppal.insetsL - Ppal.insetsR;
	private static final int HEIGHT = Ppal.HEIGHT - Ppal.insetsT - Ppal.insetsB - Ppal.menuH;
	
	public OPCION2() {
		setSize(WIDTH,HEIGHT);
		
		createComp();
	}

	@Override
	public void createComp() {
		setLayout(null);
		
		JLabel lblLista = new JLabel("Listado de Encuestas");
		lblLista.setBounds(25, 20, 160, 20);
		lblLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblLista);
		
		JScrollPane scrpLista = new JScrollPane();
		scrpLista.setBounds(45, 55, 480, 200);
		add(scrpLista);
		
		lstEncuestas = new JList<Encuesta>();
		scrpLista.setViewportView(lstEncuestas);
		lstModel = new DefaultListModel<Encuesta>();
		lstEncuestas.setModel(lstModel);
		
	}
	
	public void setCtrl(CtrlESeries c) {
	}
	
	public void showE(ArrayList<Encuesta> es) {
		lstModel.clear();
		lstModel.addAll(es);
	}
	
}
