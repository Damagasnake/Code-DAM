package com.michelin.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class PRegistrarRestaurante extends JPanel {
	private static final int ANCHO = VPRestaurantes.ANCHO 
			- VPRestaurantes.insetsL - VPRestaurantes.insetsR;
	private static final int ALTO = VPRestaurantes.ALTO 
			- VPRestaurantes.insetsT - VPRestaurantes.insetsB 
			- VPRestaurantes.menuH;
	
	private JTextField txtCiudad;
	private JTextField txtNombre;
	private JSpinner spnDist;
	private JButton btnGuardarDatos;
	private JButton btnLimpiarDatos;
	private JLabel lblEstrellas;
	private JComboBox<String> cmbRegion;
	private DefaultComboBoxModel<String> dcbmReg;
	private JTextField txtPrecioMin;
	private JTextField txtPrecioMax;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtWeb;
	private JLabel lblCocina;
	private JComboBox<String> cmbCocina;
	private DefaultComboBoxModel<String> dcbmCoc;
	
	public PRegistrarRestaurante() {
		setSize(ANCHO, ALTO);
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		
		JLabel lblNombre = new JLabel(Texto.LBL_NOMBRE);
		lblNombre.setBounds(43, 70, 69, 20);
		add(lblNombre);
		
		JLabel lblCiudad = new JLabel(Texto.LBL_CIUDAD);
		lblCiudad.setBounds(360, 115, 69, 20);
		add(lblCiudad);
		
		JLabel lblRegion = new JLabel(Texto.LBL_REGION);
		lblRegion.setBounds(43, 115, 58, 20);
		add(lblRegion);
		
		JLabel lblRegistrar = new JLabel(Texto.LBL_TIT_REGISTRO);
		lblRegistrar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRegistrar.setBounds(15, 16, 271, 20);
		add(lblRegistrar);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(127, 67, 271, 26);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		txtCiudad = new JTextField();
		txtCiudad.setBounds(437, 112, 231, 26);
		add(txtCiudad);
		txtCiudad.setColumns(10);
		
		JLabel lblDistincion = new JLabel(Texto.LBL_DISTINCION);
		lblDistincion.setBounds(43, 205, 94, 20);
		add(lblDistincion);
		
		spnDist = new JSpinner();
		spnDist.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spnDist.setBounds(139, 202, 42, 26);
		add(spnDist);
		
		cmbRegion = new JComboBox<String>();
		dcbmReg = new DefaultComboBoxModel<String>();
		for (String reg: Restaurante.REGIONES) {
			dcbmReg.addElement(reg);
		}
		cmbRegion.setModel(dcbmReg);
		cmbRegion.setBounds(116, 112, 212, 26);
		add(cmbRegion);
		
		btnGuardarDatos = new JButton(Texto.BTN_REGISTRAR);
		btnGuardarDatos.setBounds(175, 311, 153, 29);
		add(btnGuardarDatos);
		
		btnLimpiarDatos = new JButton(Texto.BTN_LIMPIAR);
		btnLimpiarDatos.setBounds(360, 311, 153, 29);
		add(btnLimpiarDatos);
		
		lblEstrellas = new JLabel(Texto.LBL_ESTRELLAS);
		lblEstrellas.setVisible(false);
		lblEstrellas.setBounds(196, 205, 69, 20);
		add(lblEstrellas);
		
		JLabel lblPrecioMin = new JLabel(Texto.LBL_PRECIO_MIN);
		lblPrecioMin.setBounds(298, 205, 125, 20);
		add(lblPrecioMin);
		
		JLabel lblPrecioMax = new JLabel(Texto.LBL_PRECIO_MAX);
		lblPrecioMax.setBounds(514, 205, 86, 20);
		add(lblPrecioMax);
		
		txtPrecioMin = new JTextField();
		txtPrecioMin.setBounds(430, 202, 69, 26);
		add(txtPrecioMin);
		txtPrecioMin.setColumns(10);
		
		txtPrecioMax = new JTextField();
		txtPrecioMax.setColumns(10);
		txtPrecioMax.setBounds(598, 202, 69, 26);
		add(txtPrecioMax);
		
		JLabel lblDireccion = new JLabel(Texto.LBL_DIRECCION);
		lblDireccion.setBounds(43, 160, 94, 20);
		add(lblDireccion);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(139, 157, 413, 26);
		add(txtDireccion);
		txtDireccion.setColumns(10);
		
		JLabel lblTelfono = new JLabel(Texto.LBL_TELEFONO);
		lblTelfono.setBounds(43, 250, 86, 20);
		add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(133, 247, 138, 26);
		add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblWeb = new JLabel(Texto.LBL_WEB);
		lblWeb.setBounds(308, 250, 58, 20);
		add(lblWeb);
		
		txtWeb = new JTextField();
		txtWeb.setBounds(367, 247, 301, 26);
		add(txtWeb);
		txtWeb.setColumns(10);
		
		lblCocina = new JLabel(Texto.LBL_COCINA);
		lblCocina.setBounds(430, 70, 69, 20);
		add(lblCocina);
		
		cmbCocina = new JComboBox<String>();
		
		dcbmCoc = new DefaultComboBoxModel<String>();
		for (String tipo: Restaurante.TIPOS_COCINA) {
			dcbmCoc.addElement(tipo);
		}
		cmbCocina.setModel(dcbmCoc);
		
		cmbCocina.setBounds(504, 67, 147, 26);
		add(cmbCocina);		
			
	}

	public JButton getBtnGuardarDatos() {
		return btnGuardarDatos;
	}

	public JButton getBtnLimpiarDatos() {
		return btnLimpiarDatos;
	}

	public void limpiarDatos() {
		txtNombre.setText("");
		cmbRegion.setSelectedIndex(0);
		txtCiudad.setText("");
		spnDist.setValue(1);
		txtDireccion.setText("");
		txtPrecioMin.setText("");
		txtPrecioMax.setText("");
		cmbCocina.setSelectedIndex(0);
		txtTelefono.setText("");
		txtWeb.setText("");
		
	}
	
	public void setControlador(RestaurantesListener controlador) {
		btnGuardarDatos.addActionListener(controlador);
		btnLimpiarDatos.addActionListener(controlador);
		
	}

	public Restaurante obtenerDatos() {
		Restaurante restaurante = null;
		
		// recuperar y validar los datos
		// el nombre y la ciudad no pueden estar vacíos
		// Ninguno de los dos son obligatorios, pero si se ha introducido precio mínimo y precio máximo 
		// tienen que ser valores numéricos decimales
		// y no puede ser precio máximo menor que el mínimo
		String nom = txtNombre.getText().trim();
		String reg = (String) cmbRegion.getSelectedItem();
		String ciu = txtCiudad.getText().trim();
		int dist = (int) spnDist.getValue();
		String dir = txtDireccion.getText();
		String sPrecMin = txtPrecioMin.getText();
		String sPrecMax = txtPrecioMax.getText();
		String coc = (String) cmbCocina.getSelectedItem();
		String tel = txtTelefono.getText();
		String web = txtWeb.getText();
		double precMin = 0;
		double precMax = 0;
		
		if (nom.isEmpty()) {
			JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_NOM, 
					Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
		} else if (ciu.isEmpty()) {
			JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_CIU, 
					Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if (!sPrecMin.isEmpty()) {
					precMin = Double.parseDouble(sPrecMin);
				}
				
				if (!sPrecMax.isEmpty()) {
					precMax = Double.parseDouble(sPrecMax);
				}
				
				if (precMax != 0 && precMin > precMax) {
					JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_PRECIOS_1, 
							Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
				} else {
					restaurante = new Restaurante(-1, nom, reg, ciu, dist, dir, precMin, precMax, coc, tel, web);
				}
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_PRECIOS_2, 
						Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			}
		}
		
		return restaurante;
	}
}
