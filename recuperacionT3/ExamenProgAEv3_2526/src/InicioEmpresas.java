import javax.swing.SwingUtilities;

import com.dam.control.ControladorEmpresas;
import com.dam.view.PConsultaEmpresas;
import com.dam.view.PModificarEmpresa;
import com.dam.view.PRegistrarEmpresa;
import com.dam.view.VPEmpresas;

public class InicioEmpresas {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VPEmpresas vp = new VPEmpresas();
			PRegistrarEmpresa pre = new PRegistrarEmpresa(); // Panel Registrar
			PConsultaEmpresas pce = new PConsultaEmpresas(); // Panel Consulta
			PModificarEmpresa pme = new PModificarEmpresa(); // Panel Modificar

			ControladorEmpresas ce = new ControladorEmpresas(vp); // Controlador
			ce.setPre(pre); // Panel Registrar
			ce.setPce(pce); // Panel Consulta
			ce.setPme(pme); // Panel Modificar

			vp.setControlador(ce); // Ventana Principal
			pre.setControlador(ce); // Panel Registrar
			pce.setControlador(ce); // Panel Consulta
			pme.setControlador(ce); // Panel Modificar

			vp.hacerVisible(); // Mostrar la ventana
		});
	}

}
