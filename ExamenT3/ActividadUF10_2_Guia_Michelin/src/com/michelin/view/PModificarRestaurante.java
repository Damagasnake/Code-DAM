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

public class PModificarRestaurante extends JPanel {
	private static final int ANCHO = VPRestaurantes.ANCHO 
			- VPRestaurantes.insetsL - VPRestaurantes.insetsR;
	private static final int ALTO = VPRestaurantes.ALTO 
			- VPRestaurantes.insetsT - VPRestaurantes.insetsB 
			- VPRestaurantes.menuH;
	
	private JTextField txtCiudad;
	private JTextField txtNombre;
	private JSpinner spnDist;
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
	private int id;
	private JButton btnGuardarDatos;
	private JButton btnCancelar;
	private JButton btnBuscar;
	
	public PModificarRestaurante() {
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
		
		JLabel lblRegistrar = new JLabel(Texto.LBL_TIT_MODIF);
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
		
		btnGuardarDatos = new JButton(Texto.BTN_GUARDAR);
		btnGuardarDatos.setBounds(175, 311, 153, 29);
		add(btnGuardarDatos);
		
		btnCancelar = new JButton(Texto.BTN_CANCELAR);
		btnCancelar.setBounds(360, 311, 153, 29);
		add(btnCancelar);
		
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
		txtDireccion.setBounds(135, 157, 288, 26);
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
		lblCocina.setBounds(460, 160, 69, 20);
		add(lblCocina);
		
		cmbCocina = new JComboBox<String>();
		
		dcbmCoc = new DefaultComboBoxModel<String>();
		for (String tipo: Restaurante.TIPOS_COCINA) {
			dcbmCoc.addElement(tipo);
		}
		cmbCocina.setModel(dcbmCoc);
		
		cmbCocina.setBounds(535, 157, 147, 26);
		add(cmbCocina);
		
		btnBuscar = new JButton(Texto.BTN_BUSCAR);
		btnBuscar.setBounds(567, 66, 115, 29);
		add(btnBuscar);
		
		habilitarModif(false);
		
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnGuardarDatos() {
		return btnGuardarDatos;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void setControlador(RestaurantesListener controlador) {
		btnGuardarDatos.addActionListener(controlador);
		btnCancelar.addActionListener(controlador);
		btnBuscar.addActionListener(controlador);
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
	
	public void habilitarModif(boolean b) {
		// al inicio, en el panel deben estar habilitados el txtNombre y el botón buscar
		// y el resto de componentes deshabilitados
		// cuando se busca un restaurante y se cargan sus datos, se dehabilitan el txtNombre y el botón buscar
		// y se habilitan el resto de componentes
		txtNombre.setEnabled(!b);
		btnBuscar.setEnabled(!b);
		cmbRegion.setEnabled(b);
		txtCiudad.setEnabled(b);
		spnDist.setEnabled(b);
		txtDireccion.setEnabled(b);
		txtPrecioMin.setEnabled(b);
		txtPrecioMax.setEnabled(b);
		cmbCocina.setEnabled(b);
		txtTelefono.setEnabled(b);
		txtWeb.setEnabled(b);
		btnGuardarDatos.setEnabled(b);
		btnCancelar.setEnabled(b);
		
		
	}

	public void cargarRestaurante(Restaurante restaurante) {
		// Cargar los datos del restaurante en los componentes 
		// cargando la variable id, nos permitiría realizar la modificación por el id en vez de por el nombre
		id = restaurante.getId();
		txtNombre.setText(restaurante.getNombre());
		cmbRegion.setSelectedItem(restaurante.getRegion());
		txtCiudad.setText(restaurante.getCiudad());
		spnDist.setValue(restaurante.getDistincion());
		txtDireccion.setText(restaurante.getDireccion());
		txtPrecioMin.setText(String.valueOf(restaurante.getPrecioMin()));
		if (restaurante.getPrecioMax() != 0)
			txtPrecioMax.setText(String.valueOf(restaurante.getPrecioMax()));
		
		cmbCocina.setSelectedItem(restaurante.getCocina());
		txtTelefono.setText(restaurante.getTelefono());
		txtWeb.setText(restaurante.getWeb());
		
	}
	
	public Restaurante obtenerDatos() {
		Restaurante restaurante = null;
		
		// recuperar y validar lso datos
		// la ciudad no puede estar vacía
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
		
		if (ciu.isEmpty()) {
			JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_NOM, 
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
					restaurante = new Restaurante(id, nom, reg, ciu, dist, dir, precMin, precMax, coc, tel, web);
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, Texto.MSJ_ERROR_PRECIOS_2, 
						Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
		return restaurante;
	}
}
