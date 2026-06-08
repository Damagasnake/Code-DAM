package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;

public class PRegistrarEmpresa extends JPanel implements IPaneles {

	private static final int ANCHO = VPEmpresas.ANCHO - VPEmpresas.insetsL - VPEmpresas.insetsR;
	private static final int ALTO = VPEmpresas.ALTO - VPEmpresas.insetsT - VPEmpresas.insetsB - VPEmpresas.menuH;

	private JTextField txtCIF;
	private JTextField txtRazon;
	private JTextField txtDomicilio;
	private JTextField txtRepresentante;
	private JTextField txtCorreoR;
	private JTextField txtWeb;
	private JTextField txtTelefono;
	private JButton btnCancelar;
	private JButton btnGuardar;
	private JSpinner spnNumEmpleados;
	private JCheckBox chckFirmado;

	public PRegistrarEmpresa() {
		crearComponentes();
		setPreferredSize(new Dimension(ANCHO, ALTO));
	}

	@Override
	public void crearComponentes() {
		setLayout(null);

		JLabel lblRegistrar = new JLabel(Textos.LBL_TIT_REGISTRO);
		lblRegistrar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRegistrar.setBounds(20, 15, 271, 20);
		add(lblRegistrar);

		JLabel lblCIF = new JLabel(Textos.LBL_CIF);
		lblCIF.setBounds(30, 55, 60, 20);
		add(lblCIF);

		txtCIF = new JTextField();
		txtCIF.setBounds(120, 53, 150, 24);
		add(txtCIF);
		txtCIF.setColumns(10);

		JLabel lblRazon = new JLabel(Textos.LBL_RAZON);
		lblRazon.setBounds(30, 95, 100, 20);
		add(lblRazon);

		txtRazon = new JTextField();
		txtRazon.setBounds(150, 93, 270, 24);
		add(txtRazon);
		txtRazon.setColumns(10);

		JLabel lblDomincilio = new JLabel(Textos.LBL_DOMICILIO);
		lblDomincilio.setBounds(30, 135, 100, 20);
		add(lblDomincilio);

		txtDomicilio = new JTextField();
		txtDomicilio.setBounds(150, 133, 300, 24);
		add(txtDomicilio);
		txtDomicilio.setColumns(10);

		JLabel lblRepresentante = new JLabel(Textos.LBL_REPRESENTANTE);
		lblRepresentante.setBounds(30, 175, 150, 20);
		add(lblRepresentante);

		txtRepresentante = new JTextField();
		txtRepresentante.setBounds(200, 173, 270, 24);
		add(txtRepresentante);
		txtRepresentante.setColumns(10);

		JLabel lblCorreoR = new JLabel(Textos.LBL_CORREO_REPRE);
		lblCorreoR.setBounds(30, 215, 150, 20);
		add(lblCorreoR);

		txtCorreoR = new JTextField();
		txtCorreoR.setBounds(200, 213, 300, 24);
		add(txtCorreoR);
		txtCorreoR.setColumns(10);

		JLabel lblConvenio = new JLabel(Textos.LBL_CONVENIO);
		lblConvenio.setBounds(30, 255, 120, 20);
		add(lblConvenio);

		chckFirmado = new JCheckBox(Textos.CHCK_FIRMADO);
		chckFirmado.setBounds(120, 253, 90, 24);
		add(chckFirmado);

		JLabel lblWeb = new JLabel(Textos.LBL_WEB);
		lblWeb.setBounds(220, 255, 70, 20);
		add(lblWeb);

		txtWeb = new JTextField();
		txtWeb.setBounds(300, 253, 250, 24);
		add(txtWeb);
		txtWeb.setColumns(10);

		JLabel lblNumEmpleados = new JLabel(Textos.LBL_NUM_EMPLE);
		lblNumEmpleados.setBounds(30, 295, 150, 20);
		add(lblNumEmpleados);

		spnNumEmpleados = new JSpinner();
		spnNumEmpleados.setModel(new SpinnerNumberModel(0, 0, 10000, 10));
		spnNumEmpleados.setBounds(200, 293, 70, 24);
		add(spnNumEmpleados);

		JLabel lblTelefono = new JLabel(Textos.LBL_TELEFONO);
		lblTelefono.setBounds(300, 295, 100, 20);
		add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(400, 293, 120, 24);
		add(txtTelefono);
		txtTelefono.setColumns(10);

		btnGuardar = new JButton(Textos.BTN_GUARDAR);
		btnGuardar.setBounds(125, 340, 150, 24);
		add(btnGuardar);

		btnCancelar = new JButton(Textos.BTN_CANCELAR);
		btnCancelar.setBounds(300, 340, 150, 24);
		add(btnCancelar);
	}

	@Override
	public void setControlador(ControladorEmpresas control) {
		btnGuardar.addActionListener(control);
		btnCancelar.addActionListener(control);
	}

	public void mostrarMensaje(String mensaje, String titulo, int tipo) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
	}

	public Empresa obtenerDatos() {
		String cif = txtCIF.getText().trim();
		String razon = txtRazon.getText().trim();
		String domicilio = txtDomicilio.getText().trim();
		String representante = txtRepresentante.getText().trim();
		String correo = txtCorreoR.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String web = txtWeb.getText().trim();
		int numEmpleados = (int) spnNumEmpleados.getValue();
		String convenio = chckFirmado.isSelected() ? "SI" : "NO";

		if (cif.isEmpty() || razon.isEmpty() || domicilio.isEmpty()
				|| representante.isEmpty() || correo.isEmpty()) {
			mostrarMensaje(Textos.MSJ_ERROR_DATOS, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!Empresa.validarCif(cif)) {
			mostrarMensaje(Textos.MSJ_ERROR_CIF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!Empresa.validarCorreo(correo)) {
			mostrarMensaje(Textos.MSJ_ERROR_CORREO, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			return null;
		}
		if (!telefono.isEmpty() && !Empresa.validarTelefono(telefono)) {
			mostrarMensaje(Textos.MSJ_ERROR_TELEF, Textos.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			return null;
		}

		return new Empresa(cif, razon, domicilio, representante, correo, convenio, numEmpleados, telefono, web);
	}

	public void limpiarDatos() {
		txtCIF.setText("");
		txtCorreoR.setText("");
		txtDomicilio.setText("");
		txtRazon.setText("");
		txtRepresentante.setText("");
		txtTelefono.setText("");
		txtWeb.setText("");
		spnNumEmpleados.setValue(0);
		chckFirmado.setSelected(false);
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

}
