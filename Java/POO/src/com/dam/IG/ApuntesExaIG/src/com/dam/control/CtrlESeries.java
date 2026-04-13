package com.dam.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.dam.model.LstEncuestas;
import com.dam.view.OPCION2;
import com.dam.view.OPCION1;
import com.dam.view.Ppal;

public class CtrlESeries implements ActionListener{
	
	private Ppal vp;
	private OPCION1 op1;
	private OPCION2 op2;
	
	private LstEncuestas lstE= new LstEncuestas();
	
	public CtrlESeries(Ppal vp, OPCION1 op1, OPCION2 op2) {
		this.vp=vp;
		this.op1=op1;
		this.op2=op2;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String btn = e.getActionCommand();
		
		op2.showE(lstE.getLstESeries());
		
		if(e.getSource() instanceof JMenuItem) {
			if(e.getSource().equals(vp.getOp1())) {
				vp.runPanel(op1);
			}else if(e.getSource().equals(vp.getOp2())) {
				vp.runPanel(op2);
			}
		}else if(e.getSource() instanceof JButton) {
			BtnsBehavior(btn);
		}
	}

	private void BtnsBehavior(String btn) {
		
	}

}
