package com.dam.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.dam.control.ControladorEmpresas;
import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;
import com.dam.model.db.EmpresaContracts;

public class PConsultaEmpresas extends JPanel implements IPaneles {

	private static final int ANCHO = VPEmpresas.ANCHO - VPEmpresas.insetsL - VPEmpresas.insetsR;
	private static final int ALTO = VPEmpresas.ALTO - VPEmpresas.insetsT - VPEmpresas.insetsB - VPEmpresas.menuH;

	private JTextField txtCIF;
	private JTextField txtRazon;
	private JButton btnBuscar;
	private JLabel lblListado;
	private JScrollPane scrpEmpresas;
	private JTable tblEmpresas;
	private JButton btnEliminar;
	private JButton btnModificar;
	private DefaultTableModel dtmEmpresas;

	public PConsultaEmpresas() {
		crearComponentes();
		setPreferredSize(new Dimension(ANCHO, ALTO));
	}

	@Override
	public void crearComponentes() {
		setLayout(null);
		JLabel lblConsulta = new JLabel(Textos.LBL_TIT_CONSULTA);
		lblConsulta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConsulta.setBounds(20, 15, 300, 20);
		add(lblConsulta);

		JLabel lblCIF = new JLabel(Textos.LBL_CIF_F);
		lblCIF.setBounds(30, 55, 60, 20);
		add(lblCIF);

		txtCIF = new JTextField();
		txtCIF.setBounds(120, 53, 150, 24);
		add(txtCIF);
		txtCIF.setColumns(10);

		JLabel lblRazon = new JLabel(Textos.LBL_RAZON_F);
		lblRazon.setBounds(30, 95, 100, 20);
		add(lblRazon);

		txtRazon = new JTextField();
		txtRazon.setBounds(150, 93, 270, 24);
		add(txtRazon);
		txtRazon.setColumns(10);

		btnBuscar = new JButton(Textos.BTN_BUSCAR);
		btnBuscar.setBounds(400, 135, 150, 24);
		add(btnBuscar);

		lblListado = new JLabel(Textos.LBL_LISTADO);
		lblListado.setVisible(false);
		lblListado.setBounds(30, 137, 218, 20);
		add(lblListado);

		scrpEmpresas = new JScrollPane();
		scrpEmpresas.setVisible(false);
		scrpEmpresas.setBounds(30, 175, 520, 175);
		add(scrpEmpresas);

		tblEmpresas = new JTable();
		tblEmpresas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblEmpresas.setBounds(0, 0, 1, 1);
		scrpEmpresas.setViewportView(tblEmpresas);

		configurarTabla();

		btnEliminar = new JButton(Textos.BTN_ELIMINAR);
		btnEliminar.setBounds(400, 365, 150, 24);
		btnEliminar.setVisible(false);
		btnEliminar.setEnabled(false);
		add(btnEliminar);

		btnModificar = new JButton(Textos.BTN_MODIFICAR);
		btnModificar.setBounds(30, 365, 150, 24);
		btnModificar.setVisible(false);
		btnModificar.setEnabled(false);
		add(btnModificar);
	}

	private void configurarTabla() {
		dtmEmpresas = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblEmpresas.setModel(dtmEmpresas);

		dtmEmpresas.addColumn(EmpresaContracts.COL_ID);
		dtmEmpresas.addColumn(EmpresaContracts.COL_RAZON);
		dtmEmpresas.addColumn(EmpresaContracts.COL_DOMICILIO);
		dtmEmpresas.addColumn(EmpresaContracts.COL_REPRESENTANTE);
		dtmEmpresas.addColumn(EmpresaContracts.COL_REPRESENTANTE_MAIL);
		dtmEmpresas.addColumn(EmpresaContracts.COL_CONVENIO);
		dtmEmpresas.addColumn(EmpresaContracts.COL_NUM_EMP);
		dtmEmpresas.addColumn(EmpresaContracts.COL_TLF);
		dtmEmpresas.addColumn(EmpresaContracts.COL_WEB);
	}

	public void cargarTabla(ArrayList<Empresa> listaEmpresas) {
		tblEmpresas.clearSelection();
		dtmEmpresas.setRowCount(0);

		for (Empresa e : listaEmpresas) {
			dtmEmpresas.addRow(new Object[] {
					e.getCif(),
					e.getRazonSocial(),
					e.getDomicilio(),
					e.getRepresentante(),
					e.getCorreoRL(),
					e.traducirConvenio(),
					e.getNumEmpleados(),
					e.getTelefono(),
					e.getWeb()
			});
		}
	}

	public void setVisibleTabla(boolean visible) {
		scrpEmpresas.setVisible(visible);
		lblListado.setVisible(visible);
		btnEliminar.setVisible(visible);
		btnEliminar.setEnabled(visible);
		btnModificar.setVisible(visible);
		btnModificar.setEnabled(visible);
	}

	public void limpiarConsulta() {
		txtCIF.setText("");
		txtRazon.setText("");
		setVisibleTabla(false);
		dtmEmpresas.setRowCount(0);
		tblEmpresas.clearSelection();
	}

	@Override
	public void setControlador(ControladorEmpresas control) {
		btnBuscar.addActionListener(control);
		btnEliminar.addActionListener(control);
		btnModificar.addActionListener(control);
	}

	public String getCifFiltro() {
		return txtCIF.getText().trim();
	}

	public String getRazonFiltro() {
		return txtRazon.getText().trim();
	}

	public JTable getTblEmpresas() {
		return tblEmpresas;
	}

	public DefaultTableModel getDtmEmpresas() {
		return dtmEmpresas;
	}

	public JButton getBtnBuscar() {
		return btnBuscar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public JButton getBtnModificar() {
		return btnModificar;
	}

	public void mostrarMensaje(String mensaje, String titulo, int tipo) {
		JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
	}

	public int mostrarConfirmacion(String mensaje, String titulo, int tipo) {
		return JOptionPane.showConfirmDialog(this, mensaje, titulo, tipo);
	}

}
