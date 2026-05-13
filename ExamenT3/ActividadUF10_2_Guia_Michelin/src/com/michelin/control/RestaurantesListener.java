package com.michelin.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.w3c.dom.Text;

import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;
import com.michelin.model.db.RestaurantesDAO;
import com.michelin.view.PConsultarRestaurantes;
import com.michelin.view.PModificarRestaurante;
import com.michelin.view.PRegistrarRestaurante;
import com.michelin.view.VPRestaurantes;

public class RestaurantesListener implements ActionListener {
	
	private VPRestaurantes vp;
	private PConsultarRestaurantes pcr;
	private PRegistrarRestaurante prr;
	private PModificarRestaurante pmr;
	
	private RestaurantesDAO datosRestaurantes;

	public RestaurantesListener(VPRestaurantes vp) {
		this.vp = vp;
		datosRestaurantes = new RestaurantesDAO();
	}

	public void setPcr(PConsultarRestaurantes pcr) {
		this.pcr = pcr;
	}
	
	public void setPrr(PRegistrarRestaurante prr) {
		this.prr = prr;
	}

	public void setPmr(PModificarRestaurante pmr) {
		this.pmr = pmr;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		
		/* TODO:
		 
		 * Si se pulsa la opción de menú de registro
		 *  --> se cargará el panel de registro
		 * Si se pulsa la opción de menú de modificación
		 *  --> se cargará el panel de modificación y se inicializará, es decir, habilitar y deshabilitar lo que proceda
		 * Si se pulsa la opción de salir 
		 *  --> muestra un mensaje de confirmación 
		 *  	si la respuesta es afirmativa --> se cierra la aplicación
		 *  
		 
		 * Si se pulsa el botón limpiar del panel de registro --> se limpian los componentes del panel
		 * Si se pulsa el botón guardar del panel de registro --> invocar a registrarRestaurante
		 * Si se pulsa el botón eliminar del panel de consulta --> invocar a eliminarRestaurante
		 * Si se pulsa el botón buscar del panel de modificación --> invocar a buscarRestaurante 
		 * Si se pulsa el botón guardar del panel de modificación --> invocar a modificarRestaurantes
		 * Si se pulsa el botón cancelar del panel de modificación --> se limpian los componentes editables 
		 * 																y se reinicia el panel de modificación 
		 */
		
		if (ev.getSource() instanceof JMenuItem) {
			if (ev.getSource().equals(vp.getMntmConsulta())) {
				/*
				 * Si se pulsa la opción de menú consulta 
				 * 	--> se cargará el combo de regiones con las distintas regiones de que haya en la tabla
				 * 		se limpiará el panel por si se hubiera realizado una consulta anterior
				 * 		se cargará el panel de consulta
				 */
				pcr.cargarCombo(datosRestaurantes.selectRegiones());
				pcr.limpiarConsulta();
				vp.cargarPanel(pcr);
				
			} else if (ev.getSource().equals(vp.getMntmAlta())) {
				vp.cargarPanel(prr);
				
			} else if (ev.getSource().equals(vp.getMntmSalir())) {
				vp.confirmarSalida();
				
			} else {
				vp.cargarPanel(pmr);
				
			}
			
		} else if (ev.getSource() instanceof JButton) {
			
			// if (ev.getActionCommand().equals(Texto.BTN_CONSULTAR)) {
			if (ev.getSource().equals(pcr.getBtnConsultar())) {
				//* Si se pulsa el botón consultar del panel de consulta --> invocar a consultarRestaurantes
				consultarRestaurantes();
				
			} else if (ev.getSource().equals(pcr.getBtnEliminar())) {
				eliminarRestaurante();
				
			} else if (ev.getSource().equals(prr.getBtnGuardarDatos())) {
				registrarRestaurante();
				
			} else if (ev.getSource().equals(prr.getBtnLimpiarDatos())) {
				prr.limpiarDatos();
				
			} else if (ev.getSource().equals(pmr.getBtnBuscar())) {
				buscarRestaurante();
				
			} else if (ev.getSource().equals(pmr.getBtnGuardarDatos())) {
				modificarRestaurantes();
				
			} else if (ev.getSource().equals(pmr.getBtnCancelar())) {
				pmr.limpiarDatos();
				pmr.habilitarModif(false);
				
			}
			
		}

	}

	private void modificarRestaurantes() {
		/*
		 * Obtener los datos validados del panel de modificación
		 * Si los datos son válidos --> modificar el restaurante y dar feedback al usuario
		 */
		Restaurante restaurante = pmr.obtenerDatos();
		
		if (restaurante != null) {
			int res = datosRestaurantes.updateRestaurante(restaurante);
			
			if (res == 1) {
				JOptionPane.showMessageDialog(pmr, Texto.MSJ_MODIFICACION, 
						Texto.TIT_MSJ_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
				
			}
			
		}
		
	}

	private void buscarRestaurante() {
		/*
		 * Recuperar el nombre del restaurante
		 * Si se ha introducido algo --> realizar la consulta para obtener todos los datos del restaurante por nombre
		 * 								Si se han encontrado datos --> se cargan en el panel de modificación 
		 * 																y se habilitan los componentes para la modificación
		 * 								Si no se encuentran datos --> mostrar un mensaje informativo
		 * Si no se ha introducido un nombre de restaurante --> mostrar un error
		 */
		String nombre = pmr.getTxtNombre().getText().trim();
		
		if (!nombre.isEmpty()) {
			Restaurante restaurante = datosRestaurantes.selectRestauranteNombre(nombre);
			
			if (restaurante != null) {
				pmr.cargarRestaurante(restaurante);
				pmr.habilitarModif(true);
			} else {
				JOptionPane.showMessageDialog(pmr, 
						Texto.MSJ_CONSULTA, 
						Texto.TIT_MSJ_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(pmr, 
					Texto.MSJ_NOMBRE_OBLIG, 
					Texto.TIT_ERROR_DATOS, JOptionPane.ERROR_MESSAGE);
			
		}
		
	}

	private void eliminarRestaurante() {
		/*
		 * Recuperar el registro seleccionado en la tabla
		 * Si se ha seleccionado un registro --> mostrar un mensaje de confirmación
		 * 										Si la respuesta es afirmativa 
		 * 											--> recuperar el nombre del restaurante seleccionado 
		 * 													con el método del model getValueAt(fila, columna)
		 * 												borrar el restaurante de la bbdd por el nombre
		 * 												dar feedback al usuario
		 * Si no se ha seleccionado ningún registro --> mostrar un mensaje de error
		 * 
		 */
		String nom = null;
		
		nom = pcr.getNombreRegSel();
		
		// int filaSel = pcr.getTblRestaurantes().getSelectedRow();
		
		if (nom != null) {
		// if (filaSel != -1) {
			// nom = (String) pcr.getDtmRestaurantes().getValueAt(filaSel, 0);
			int respuesta = JOptionPane.showConfirmDialog(pcr, Texto.MSJ_CONFIR_ELIMINAR, Texto.TIT_CONFIRM, 
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if (respuesta == JOptionPane.YES_OPTION) {
				int resultado = datosRestaurantes.deleteRestaurante(nom);
				if (resultado == 1) {
					JOptionPane.showMessageDialog(pcr, Texto.MSJ_RESULT_ELIMINAR, Texto.TIT_MSJ_RESULTADO, 
							JOptionPane.INFORMATION_MESSAGE);
					consultarRestaurantes();
				}
			}
			
		} else {
			JOptionPane.showMessageDialog(pcr, Texto.MSJ_ERROR_SEL, Texto.TIT_ERROR_SEL, JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void registrarRestaurante() {
		/*
		 * Obtener los datos validados del panel de registro
		 * Si los datos son válidos --> registrar el restaurante 
		 *								dar feedback al usuario teniendo en cuenta que si no se ha realizado el registro
		 *									solo puede ser porque el nombre ya existe en la bbdd
		 *								limpiar campos
		 */
		Restaurante rest = prr.obtenerDatos();
		
		if (rest != null) {
			int res = datosRestaurantes.insertRestaurante(rest);
			
			if (res == 1) {
				JOptionPane.showMessageDialog(prr, Texto.MSJ_REGISTRO, 
						Texto.TIT_MSJ_RESULTADO, JOptionPane.INFORMATION_MESSAGE);
				prr.limpiarDatos();
			} else {
				JOptionPane.showMessageDialog(prr, Texto.MSJ_NOMBRE_DUP, 
						Texto.TIT_MSJ_RESULTADO, JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
	}

	private void consultarRestaurantes() {
		/*
		 * recuperar la región seleccionada
		 * recuperar la distinción seleccionada
		 * dependiendo de la región y la distinción recuperar de la bbdd la lista de restaurantes que cumplan las condiciones
		 * Si la lista contiene restaurantes --> cargar los restaurantes en la tabla
		 * 										 hacer visibles los componentes asociados a la visualización de la consulta
		 * Si la lista no contiene restaurnates --> mostrar un mensaje informativo 
		 * 											ocultar los componentes asociados a la visualización de la consulta
		 */
		String reg = (String) pcr.getCmbRegion().getSelectedItem();
		String dist = (String) pcr.getCmbDist().getSelectedItem();
		
		ArrayList<Restaurante> listRestaurantes = new ArrayList<Restaurante>();
		
		/*
		if (!reg.equals(Texto.TODAS) && !dist.equals(Texto.TODAS)) {
			listRestaurantes = 
					datosRestaurantes.selectRestaurantesFiltro(reg, Character.getNumericValue(dist.charAt(0)));
		} else if (!reg.equals(Texto.TODAS)) {
			listRestaurantes = datosRestaurantes.selectRestaurantesReg(reg);
		} else if (!dist.equals(Texto.TODAS)) {
			listRestaurantes = datosRestaurantes.selectRestaurantesDist(Character.getNumericValue(dist.charAt(0)));
		} else {
			listRestaurantes = datosRestaurantes.selectRestaurantes();
		}
		*/
		
		listRestaurantes = datosRestaurantes.selectRestaurantesFiltro(reg, dist);
		
		if (!listRestaurantes.isEmpty()) {
			pcr.cargarTabla(listRestaurantes);
			pcr.setVisibleTabla(true);
		} else {
			JOptionPane.showMessageDialog(pcr, Texto.MSJ_NO_DATOS, 
					Texto.TIT_MSJ_CONSULTA, JOptionPane.INFORMATION_MESSAGE);
			pcr.setVisibleTabla(false);
		}
		
	}
	

}
