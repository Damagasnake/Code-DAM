package com.michelin.main;

import java.awt.EventQueue;

import com.michelin.control.RestaurantesListener;
import com.michelin.view.PConsultarRestaurantes;
import com.michelin.view.PModificarRestaurante;
import com.michelin.view.PRegistrarRestaurante;
import com.michelin.view.VPRestaurantes;

public class RestaurantesMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				VPRestaurantes vp = new VPRestaurantes();
				/*PEnConstruccion pec = new PEnConstruccion();
				vp.cargarPanel(pec);*/
				
				PConsultarRestaurantes pcr = new PConsultarRestaurantes();
				PRegistrarRestaurante prr = new PRegistrarRestaurante();
				PModificarRestaurante pmr = new PModificarRestaurante();
				
				RestaurantesListener controlador = new RestaurantesListener(vp);
				controlador.setPcr(pcr);
				controlador.setPrr(prr);
				controlador.setPmr(pmr);
				
				vp.setControlador(controlador);
				pcr.setControlador(controlador);
				prr.setControlador(controlador);
				pmr.setControlador(controlador);
				
				vp.hacerVisible();
				
			}
		});

	}

}
