package com.michelin.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.michelin.control.RestaurantesListener;
import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;

public class PConsultarRestaurantes extends JPanel {
	private static final int ANCHO = VPRestaurantes.ANCHO 
			- VPRestaurantes.insetsL - VPRestaurantes.insetsR;
	private static final int ALTO = VPRestaurantes.ALTO 
			- VPRestaurantes.insetsT - VPRestaurantes.insetsB 
			- VPRestaurantes.menuH;

	private JTable tblRestaurantes;
	private JComboBox<String> cmbRegion;
	private JComboBox<String> cmbDist;
	private JButton btnConsultar;
	private JScrollPane scrpRestaurantes;
	private DefaultComboBoxModel<String> dcbmDist;
	private DefaultComboBoxModel<String> dcbmReg;
	private JButton btnEliminar;
	private JLabel lblListado;
	private DefaultTableModel dtmRestaurantes;
	
	public PConsultarRestaurantes() {
		setSize(ANCHO, ALTO);
		initComponents();
	}

	private void initComponents() {
		setLayout(null);
		
		JLabel lblConsulta = new JLabel(Texto.LBL_TIT_CONSULTA);
		lblConsulta.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConsulta.setBounds(15, 16, 300, 20);
		add(lblConsulta);
		
		JLabel lblFiltro = new JLabel(Texto.LBL_FILTRO);
		lblFiltro.setBounds(35, 52, 149, 20);
		add(lblFiltro);
		
		JLabel lblRegion = new JLabel(Texto.LBL_REGION);
		lblRegion.setBounds(61, 85, 64, 20);
		add(lblRegion);
		
		JLabel lblCocina = new JLabel(Texto.LBL_DISTINCION);
		lblCocina.setBounds(400, 85, 69, 20);
		add(lblCocina);
		
		cmbRegion = new JComboBox<String>();
		dcbmReg = new DefaultComboBoxModel<String>();
		cmbRegion.setModel(dcbmReg);
		cmbRegion.setBounds(140, 82, 212, 26);
		add(cmbRegion);
		
		cmbDist = new JComboBox<String>();
		dcbmDist = new DefaultComboBoxModel<String>();
		dcbmDist.addElement(Texto.TODAS);
		for (String dist : Restaurante.DISTINCIONES) {
			dcbmDist.addElement(dist);
		}
		
		cmbDist.setModel(dcbmDist);
		cmbDist.setBounds(484, 82, 140, 26);
		add(cmbDist);
		
		btnConsultar = new JButton(Texto.BTN_CONSULTAR);
		btnConsultar.setBounds(525, 125, 133, 29);
		add(btnConsultar);
		
		lblListado = new JLabel(Texto.LBL_LISTADO);
		lblListado.setVisible(false);
		lblListado.setBounds(35, 134, 218, 20);
		add(lblListado);
		
		scrpRestaurantes = new JScrollPane();
		scrpRestaurantes.setVisible(false);
		scrpRestaurantes.setBounds(61, 169, 597, 175);
		add(scrpRestaurantes);
		
		tblRestaurantes = new JTable();
		tblRestaurantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblRestaurantes.setBounds(0, 0, 1, 1);
		scrpRestaurantes.setViewportView(tblRestaurantes);
		
		configurarTabla();
		
		btnEliminar = new JButton(Texto.BTN_ELIMINAR);
		btnEliminar.setBounds(525, 360, 133, 29);
		btnEliminar.setVisible(false);
		btnEliminar.setEnabled(false);
		add(btnEliminar);
		
	}

	private void configurarTabla() {
		dtmRestaurantes = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tblRestaurantes.setModel(dtmRestaurantes);
		
		dtmRestaurantes.addColumn(Texto.CLM_NOMBRE);
		dtmRestaurantes.addColumn(Texto.CLM_CIUDAD);
		dtmRestaurantes.addColumn(Texto.CLM_DISTINCION);
		dtmRestaurantes.addColumn(Texto.CLM_COCINA);
		dtmRestaurantes.addColumn(Texto.CLM_PRECIO);
		
		tblRestaurantes.getColumn(Texto.CLM_NOMBRE).setPreferredWidth(100);
		tblRestaurantes.getColumn(Texto.CLM_CIUDAD).setPreferredWidth(100);
		tblRestaurantes.getColumn(Texto.CLM_DISTINCION).setPreferredWidth(50);
		tblRestaurantes.getColumn(Texto.CLM_COCINA).setPreferredWidth(100);
		tblRestaurantes.getColumn(Texto.CLM_PRECIO).setPreferredWidth(75);
		
	}
	
	public void cargarTabla(ArrayList<Restaurante> listaRestaurante) {
		tblRestaurantes.clearSelection();
		dtmRestaurantes.getDataVector().clear();
		
		Object[] fila = new Object[5];
		
		for (Restaurante rest : listaRestaurante) {
			fila[0] = rest.getNombre();
			fila[1] = rest.getCiudad();
			fila[2] = "";
			for (int i = 0; i < rest.getDistincion(); i++) {
				fila[2] += "*";
			}
			
			fila[3] = rest.getCocina();
			fila[4] = rest.getPrecioMin();
			if (rest.getPrecioMax() > 0) {
				fila[4] += " - " + rest.getPrecioMax();
			}
			
			dtmRestaurantes.addRow(fila);
			
		}
		
	}

	public void setVisibleTabla(boolean b) {
		// hacer visibles o no los componentes asociados a que haya datos que mostrar en la tabla
		scrpRestaurantes.setVisible(b);
		lblListado.setVisible(b);
		btnEliminar.setVisible(b);
		btnEliminar.setEnabled(b);
	}

	
	public JButton getBtnConsultar() {
		return btnConsultar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public JTable getTblRestaurantes() {
		return tblRestaurantes;
	}

	public DefaultTableModel getDtmRestaurantes() {
		return dtmRestaurantes;
	}

	public JComboBox<String> getCmbDist() {
		return cmbDist;
	}

	public JComboBox<String> getCmbRegion() {
		return cmbRegion;
	}

	public void setControlador(RestaurantesListener controlador) {
		btnConsultar.addActionListener(controlador);
		btnEliminar.addActionListener(controlador);
	}

	public void cargarCombo(ArrayList<String> regiones) {
		dcbmReg.removeAllElements();
		dcbmReg.addElement(Texto.TODAS);
		dcbmReg.addAll(regiones);
		/*for (String reg : regiones) {
			dcbmReg.addElement(reg);
			
		}*/
		
	}

	public void limpiarConsulta() {
		setVisibleTabla(false);
		cmbDist.setSelectedIndex(0);
		cmbRegion.setSelectedIndex(0);
		
	}

	public String getNombreRegSel() {
		int filaSel = tblRestaurantes.getSelectedRow();
		
		if (filaSel != -1) {
			return (String) dtmRestaurantes.getValueAt(filaSel, 0);
		} else {
			return null;
		}
	}
	
	
	
}
