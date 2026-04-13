package com.dam.a.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import com.dam.a.control.ControladorEncuestas;

// VIEW — Ventana principal. Extiende JFrame (ES una ventana de escritorio).
// Implementa IVPrincipalEncuestas → el controlador puede tratarla como interfaz.
public class VPrincipalEncuestas extends JFrame implements IVPrincipalEncuestas {

    // Tamaño fijo de la ventana (layout null requiere tamaño fijo).
    public static final int ANCHO = 600;
    public static final int ALTO  = 400;

    // Márgenes de la ventana y altura del menú, públicos y estáticos para que
    // los paneles puedan calcular su propio tamaño exacto restándolos.
    public static int insetsR;
    public static int insetsL;
    public static int insetsT;
    public static int insetsB;
    public static int menuH;

    // JScrollPane que actúa como contenedor intercambiable de paneles.
    private JScrollPane scrpContenedor;

    // Ítems de menú: los guarda como campos porque setControlador() necesita
    // llamar a addActionListener sobre ellos después de la construcción.
    private JMenuItem mntmRealizar;
    private JMenuItem mntmVisualizar;

    public VPrincipalEncuestas() {
        configurarVentana();   // título, tamaño, posición, menú
        crearComponentes();    // JScrollPane central
    }

    @Override
    public void configurarVentana() {
        setTitle("E N C U E S T A S");

        // Al cerrar la ventana, cerrar también la JVM.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(ANCHO, ALTO);

        // Leer los insets (márgenes del SO) DESPUÉS de setSize para tener valores reales.
        insetsR = this.getInsets().right;
        insetsL = this.getInsets().left;
        insetsT = this.getInsets().top;
        insetsB = this.getInsets().bottom;

        centrarVentana();
        crearMenu();
    }

    // Calcula la posición para que la ventana quede centrada en la pantalla.
    private void centrarVentana() {
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension ventana  = new Dimension(ANCHO, ALTO);
        // setLocation(x, y): coordenada superior-izquierda de la ventana
        setLocation(
            (pantalla.width  - ventana.width)  / 2,
            (pantalla.height - ventana.height) / 2
        );
    }

    @Override
    public void crearComponentes() {
        scrpContenedor = new JScrollPane();
        // BorderLayout.CENTER → ocupa todo el espacio disponible de la ventana.
        getContentPane().add(scrpContenedor, BorderLayout.CENTER);
    }

    @Override
    public void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Guardar la altura del menú para que los paneles descuenten ese espacio.
        menuH = menuBar.getPreferredSize().height;

        JMenu mnEncuestas = new JMenu("Encuestas A");
        menuBar.add(mnEncuestas);

        mntmRealizar = new JMenuItem("Realizar Encuesta");
        mnEncuestas.add(mntmRealizar);

        mntmVisualizar = new JMenuItem("Visualizar Encuestas");
        mnEncuestas.add(mntmVisualizar);
    }

    // Registra el controlador como ActionListener en los ítems del menú.
    // El controlador sabrá qué ítem disparó el evento comparando con getMntmRealizar()
    // y getMntmVisualizar() mediante e.getSource().
    @Override
    public void setControlador(ControladorEncuestas ce) {
        mntmRealizar.addActionListener(ce);
        mntmVisualizar.addActionListener(ce);
    }

    @Override
    public void hacerVisible() {
        setVisible(true);
    }

    // Reemplaza el contenido del JScrollPane con el panel recibido.
    // Esto es lo que simula la navegación entre "pantallas" sin abrir nuevas ventanas.
    @Override
    public void cargarPanel(JPanel panel) {
        scrpContenedor.setViewportView(panel);
    }

    // Getters para que el controlador pueda comparar con e.getSource().
    public JMenuItem getMntmRealizar()   { return mntmRealizar; }
    public JMenuItem getMntmVisualizar() { return mntmVisualizar; }
}
