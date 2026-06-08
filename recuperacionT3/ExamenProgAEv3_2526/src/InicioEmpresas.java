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
			PRegistrarEmpresa pre = new PRegistrarEmpresa();
			PConsultaEmpresas pce = new PConsultaEmpresas();
			PModificarEmpresa pme = new PModificarEmpresa();

			ControladorEmpresas ce = new ControladorEmpresas(vp);
			ce.setPre(pre);
			ce.setPce(pce);
			ce.setPme(pme);

			vp.setControlador(ce);
			pre.setControlador(ce);
			pce.setControlador(ce);
			pme.setControlador(ce);

			vp.hacerVisible();
		});
	}

}
