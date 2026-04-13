package com.dam.a.view;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.dam.a.control.ControladorEncuestas;
import com.dam.a.model.Encuesta;

// VIEW — Panel de visualización. Muestra el listado de encuestas y el análisis.
// Extiende JPanel, implementa IPaneles.
public class PVisualizarEncuestas extends JPanel implements IPaneles {

    // Mismo cálculo de tamaño que en PRealizarEncuestas.
    private static final int ANCHO = VPrincipalEncuestas.ANCHO
            - VPrincipalEncuestas.insetsL - VPrincipalEncuestas.insetsR;
    private static final int ALTO  = VPrincipalEncuestas.ALTO
            - VPrincipalEncuestas.insetsT - VPrincipalEncuestas.insetsB
            - VPrincipalEncuestas.menuH;

    // Constante de acción del botón "Ver Resultados".
    public static final String AC_BTN_RESULTADOS = "Ver Resultados";

    // JList: componente que muestra una lista de elementos con scroll.
    // Usa Object como tipo genérico para poder contener Encuesta directamente.
    private JList<Object> lstEncuestas;

    // DefaultListModel: el modelo de datos del JList.
    // Swing sigue MVC internamente: JList = vista, DefaultListModel = modelo.
    // Para añadir/quitar elementos se modifica el modelo, no el JList.
    private DefaultListModel<Object> lstModel;

    private JButton btnVer;

    public PVisualizarEncuestas() {
        setSize(ANCHO, ALTO);
        crearComponentes();
    }

    @Override
    public void crearComponentes() {
        setLayout(null);

        // -- Título
        JLabel lblLista = new JLabel("Listado de Encuestas");
        lblLista.setBounds(25, 20, 160, 20);
        lblLista.setFont(new Font("Tahoma", Font.BOLD, 14));
        add(lblLista);

        // -- JScrollPane que contiene el JList (scroll cuando hay muchas encuestas)
        JScrollPane scrpLista = new JScrollPane();
        scrpLista.setBounds(45, 55, 480, 200);
        // Mostrar barra horizontal solo si el contenido es más ancho que el panel.
        scrpLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrpLista);

        // -- JList dentro del JScrollPane
        lstEncuestas = new JList<Object>();
        scrpLista.setViewportView(lstEncuestas);   // el JList ES el contenido del scroll

        // -- Crear el modelo y asociarlo al JList
        lstModel = new DefaultListModel<Object>();
        lstEncuestas.setModel(lstModel);
        // A partir de ahora: lstModel.addElement(obj) → aparece en la lista en pantalla

        // -- Botón de análisis
        btnVer = new JButton(AC_BTN_RESULTADOS);
        btnVer.setBounds(225, 275, 150, 22);
        add(btnVer);
    }

    // Oculta o muestra el botón "Ver Resultados".
    // Si no hay encuestas el controlador lo oculta para evitar clics vacíos.
    public void hacerVisibleBtnVer(boolean b) {
        btnVer.setVisible(b);
    }

    // Registra el controlador como listener del botón "Ver Resultados".
    @Override
    public void setControlador(ControladorEncuestas control) {
        btnVer.addActionListener(control);
    }

    // Muestra el texto del análisis en un diálogo. Llamado por el controlador.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Llena el JList con las encuestas actuales del modelo.
    // El JList llama automáticamente a toString() de cada Encuesta para renderizarla.
    public void cargarEncuestas(ArrayList<Encuesta> listaEncuestas) {
        lstModel.clear();   // limpiar lo que hubiera antes
        for (Encuesta e : listaEncuestas) {
            lstModel.addElement(e);   // e.toString() se usará al pintar la celda
        }
    }
}
