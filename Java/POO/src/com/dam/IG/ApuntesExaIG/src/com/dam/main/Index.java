package com.dam.main;

import java.awt.EventQueue;

import com.dam.control.CtrlESeries;
import com.dam.view.OPCION2;
import com.dam.view.OPCION1;
import com.dam.view.Ppal;

public class Index {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				Ppal vp = new Ppal();
				OPCION1 op1 = new OPCION1();
				OPCION2 op2 = new OPCION2();
				
				CtrlESeries c = new CtrlESeries(vp,op1,op2);
				
				vp.setCtrl(c);
				op1.setCtrl(c);
				op2.setCtrl(c);
				
				vp.showWindow();
			}
		});
	}

}
