package com.dam.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.dam.model.datos.Empresa;
import com.dam.model.datos.Textos;
import com.dam.model.db.EmpresasDAO;
import com.dam.view.PConsultaEmpresas;
import com.dam.view.PModificarEmpresa;
import com.dam.view.PRegistrarEmpresa;
import com.dam.view.VPEmpresas;

public class ControladorEmpresas implements ActionListener {

	private VPEmpresas vp;
	private PRegistrarEmpresa pre;
	private PConsultaEmpresas pce;
	private PModificarEmpresa pme;

	private EmpresasDAO datosEmpresas;

	public ControladorEmpresas(VPEmpresas vp) {
		this.vp = vp;
		datosEmpresas = new EmpresasDAO();
	}

	public void setPre(PRegistrarEmpresa pre) {
		this.pre = pre;
	}

	public void setPce(PConsultaEmpresas pce) {
		this.pce = pce;
	}

	public void setPme(PModificarEmpresa pme) {
		this.pme = pme;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Object origen = ev.getSource();

		if (origen instanceof JMenuItem) {
			if (origen.equals(vp.getMntmConsulta())) {
				pce.limpiarConsulta();
				vp.cargarPanel(pce);
			} else if (origen.equals(vp.getMntmRegistrar())) {
				pre.limpiarDatos();
				vp.cargarPanel(pre);
			}
		} else if (origen.equals(pre.getBtnGuardar())) {
			registrar();
		} else if (origen.equals(pre.getBtnCancelar())) {
			pre.limpiarDatos();
		} else if (origen.equals(pce.getBtnBuscar())) {
			buscar();
		} else if (origen.equals(pce.getBtnEliminar())) {
			eliminar();
		} else if (origen.equals(pce.getBtnModificar())) {
			abrirModificar();
		} else if (origen.equals(pme.getBtnGuardar())) {
			modificar();
		} else if (origen.equals(pme.getBtnCancelar())) {
			buscar();
			vp.cargarPanel(pce);
		}
	}

	private void modificar() {
		Empresa empresa = pme.obtenerDatos();
		if (empresa != null) {
			int res = datosEmpresas.updateEmpresa(empresa);
			if (res > 0) {
				pme.mostrarMensaje(Textos.MSJ_MODIF_OK, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
				buscar();
				vp.cargarPanel(pce);
			} else {
				pme.mostrarMensaje(Textos.MSJ_MODIF_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void abrirModificar() {
		int fila = pce.getTblEmpresas().getSelectedRow();
		if (fila == -1) {
			pce.mostrarMensaje(Textos.MSJ_ERROR_SEL_M, Textos.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
			return;
		}

		String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);
		Empresa empresa = datosEmpresas.obtenerEmpresaPorCif(cif);
		if (empresa != null) {
			pme.cargarDatos(empresa);
			vp.cargarPanel(pme);
		}
	}

	private void eliminar() {
		int fila = pce.getTblEmpresas().getSelectedRow();
		if (fila == -1) {
			pce.mostrarMensaje(Textos.MSJ_ERROR_SEL_E, Textos.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
			return;
		}

		int confirmacion = pce.mostrarConfirmacion(
				Textos.MSJ_CONFIR_ELIMINAR, Textos.TIT_CONFIRM, JOptionPane.YES_NO_OPTION);
		if (confirmacion == JOptionPane.YES_OPTION) {
			String cif = (String) pce.getDtmEmpresas().getValueAt(fila, 0);
			int res = datosEmpresas.eliminarEmpresa(cif);
			if (res > 0) {
				pce.mostrarMensaje(Textos.MSJ_RESULT_ELIMINAR, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
				buscar();
			} else {
				pce.mostrarMensaje(Textos.MSJ_MODIF_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void buscar() {
		String cif = pce.getCifFiltro();
		String razon = pce.getRazonFiltro();
		ArrayList<Empresa> lista = datosEmpresas.buscarEmpresas(cif, razon);

		if (lista.isEmpty()) {
			pce.mostrarMensaje(Textos.MSJ_NO_DATOS, Textos.TIT_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
			pce.setVisibleTabla(false);
		} else {
			pce.cargarTabla(lista);
			pce.setVisibleTabla(true);
		}
	}

	private void registrar() {
		Empresa empresa = pre.obtenerDatos();
		if (empresa != null) {
			int res = datosEmpresas.insertEmpresa(empresa);
			if (res > 0) {
				pre.mostrarMensaje(Textos.MSJ_REGISTRO_OK, Textos.TIT_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
				pre.limpiarDatos();
			} else {
				pre.mostrarMensaje(Textos.MSJ_REGISTRO_KO, Textos.TIT_RESULTADO, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
