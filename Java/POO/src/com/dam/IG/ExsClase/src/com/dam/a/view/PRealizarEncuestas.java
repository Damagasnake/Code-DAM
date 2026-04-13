package com.dam.a.view;

import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.dam.a.control.ControladorEncuestas;
import com.dam.a.model.Encuesta;

// VIEW — Panel del formulario de encuesta. Extiende JPanel (ES un contenedor de componentes).
// Implementa IPaneles → el controlador puede tratarlo como interfaz.
public class PRealizarEncuestas extends JPanel implements IPaneles {

    // El panel ocupa el espacio útil de la ventana:
    // ancho total − márgenes laterales
    // alto total  − márgenes superior/inferior − altura del menú
    private static final int ANCHO = VPrincipalEncuestas.ANCHO
            - VPrincipalEncuestas.insetsL - VPrincipalEncuestas.insetsR;
    private static final int ALTO  = VPrincipalEncuestas.ALTO
            - VPrincipalEncuestas.insetsT - VPrincipalEncuestas.insetsB
            - VPrincipalEncuestas.menuH;

    // Constante de acción del botón. El controlador la compara con e.getActionCommand().
    // Usar constante en lugar de literal evita errores tipográficos.
    public static final String AC_BTN_ADD = "Añadir Encuesta";

    // ── COMPONENTES ───────────────────────────────────────────────────────────
    // ButtonGroup: hace que los JRadioButton sean mutuamente excluyentes.
    // Sin este grupo podrían estar todos marcados a la vez.
    private final ButtonGroup btngEdad = new ButtonGroup();

    private JRadioButton rdbtn517;    // "Entre 5 y 17"
    private JRadioButton rdbtn1830;   // "Entre 18 y 30"
    private JRadioButton rdbtn3140;   // "Entre 31 y 40"
    private JRadioButton rdbtn4165;   // "Entre 41 y 65"
    private JRadioButton rdbtn65;     // "Más de 65"

    // JComboBox: lista desplegable. Solo se puede elegir UN elemento.
    private JComboBox<String> cmbFrecuencia;

    // JCheckBox: casillas de verificación. Pueden marcarse VARIAS a la vez.
    private JCheckBox chckJdT;     // Juego de Tronos
    private JCheckBox chckV;       // Vikingos
    private JCheckBox chckBB;      // Breaking Bad
    private JCheckBox chckST;      // Stranger Things
    private JCheckBox chckECdlC;   // El Cuento de la Criada
    private JCheckBox chckDB;      // Dragon Ball
    private JCheckBox chck7V;      // 7 vidas

    private JButton btnAddProducto;

    public PRealizarEncuestas() {
        setSize(ANCHO, ALTO);
        crearComponentes();
    }

    // ── crearComponentes ──────────────────────────────────────────────────────
    // Construye y posiciona TODOS los componentes del formulario.
    // Layout null: posicionamiento absoluto con setBounds(x, y, ancho, alto).
    @Override
    public void crearComponentes() {
        setLayout(null);   // sin layout manager → coordenadas absolutas en píxeles

        // -- Título
        JLabel lblTitulo = new JLabel("Realizar encuesta");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitulo.setBounds(25, 20, 150, 25);   // (x, y, w, h)
        add(lblTitulo);

        // -- Sección edad: label + 5 radio buttons
        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(45, 55, 70, 14);
        add(lblEdad);

        // Crear cada radio button con el texto de la constante,
        // añadirlo al ButtonGroup Y al panel.
        rdbtn517 = new JRadioButton(Encuesta.RANGOS_EDAD[0]);
        btngEdad.add(rdbtn517);          // lo mete en el grupo exclusivo
        rdbtn517.setBounds(125, 53, 100, 20);
        rdbtn517.setSelected(true);      // seleccionado por defecto
        add(rdbtn517);

        rdbtn1830 = new JRadioButton(Encuesta.RANGOS_EDAD[1]);
        btngEdad.add(rdbtn1830);
        rdbtn1830.setBounds(235, 53, 100, 20);
        add(rdbtn1830);

        rdbtn3140 = new JRadioButton(Encuesta.RANGOS_EDAD[2]);
        btngEdad.add(rdbtn3140);
        rdbtn3140.setBounds(345, 53, 100, 20);
        add(rdbtn3140);

        rdbtn4165 = new JRadioButton(Encuesta.RANGOS_EDAD[3]);
        btngEdad.add(rdbtn4165);
        rdbtn4165.setBounds(125, 80, 100, 20);
        add(rdbtn4165);

        rdbtn65 = new JRadioButton(Encuesta.RANGOS_EDAD[4]);
        btngEdad.add(rdbtn65);
        rdbtn65.setBounds(235, 80, 100, 20);
        add(rdbtn65);

        // -- Sección frecuencia: label + combobox
        JLabel lblFrecuencia = new JLabel("Frecuencia:");
        lblFrecuencia.setBounds(45, 115, 80, 14);
        add(lblFrecuencia);

        cmbFrecuencia = new JComboBox<String>();
        // DefaultComboBoxModel: modelo de datos del JComboBox.
        // Se construye directamente desde el array de constantes.
        DefaultComboBoxModel<String> cmbModel = new DefaultComboBoxModel<String>(Encuesta.FRECUENCIAS);
        cmbFrecuencia.setModel(cmbModel);
        cmbFrecuencia.setBounds(150, 113, 175, 20);
        add(cmbFrecuencia);

        // -- Sección series: label + 8 checkboxes (2 columnas)
        JLabel lblSeries = new JLabel("Series:");
        lblSeries.setBounds(45, 150, 80, 14);
        add(lblSeries);

        chckJdT = new JCheckBox(Encuesta.SERIES[0]);
        chckJdT.setBounds(125, 148, 150, 22);
        add(chckJdT);

        chckV = new JCheckBox(Encuesta.SERIES[1]);
        chckV.setBounds(305, 148, 150, 22);
        add(chckV);

        chckBB = new JCheckBox(Encuesta.SERIES[2]);
        chckBB.setBounds(125, 175, 150, 22);
        add(chckBB);

        chckST = new JCheckBox(Encuesta.SERIES[3]);
        chckST.setBounds(305, 175, 150, 22);
        add(chckST);

        chckECdlC = new JCheckBox(Encuesta.SERIES[4]);
        chckECdlC.setBounds(125, 202, 175, 22);
        add(chckECdlC);

        // AVISO: este checkbox se crea como variable local (no es campo de la clase)
        // porque limpiarEncuesta() lo limpia mediante getComponents() + instanceof,
        // sin necesitar una referencia directa.
        JCheckBox chckJdlC = new JCheckBox(Encuesta.SERIES[5]);
        chckJdlC.setBounds(305, 202, 150, 22);
        add(chckJdlC);

        chckDB = new JCheckBox(Encuesta.SERIES[6]);
        chckDB.setBounds(125, 229, 150, 22);
        add(chckDB);

        chck7V = new JCheckBox(Encuesta.SERIES[7]);
        chck7V.setBounds(305, 229, 150, 22);
        add(chck7V);

        // -- Botón de envío
        btnAddProducto = new JButton(AC_BTN_ADD);   // el texto = ActionCommand por defecto
        btnAddProducto.setBounds(225, 275, 150, 22);
        add(btnAddProducto);
    }

    // Registra el controlador como listener del botón.
    // Cuando el usuario haga clic, Swing llamará a controlador.actionPerformed(e).
    @Override
    public void setControlador(ControladorEncuestas control) {
        btnAddProducto.addActionListener(control);
    }

    // ── LEER EL FORMULARIO ────────────────────────────────────────────────────
    // Lee el estado actual de todos los componentes y devuelve un objeto Encuesta.
    // El controlador llama a este método cuando el usuario pulsa "Añadir Encuesta".
    public Encuesta obtenerDatosEncuesta() {
        // 1. Rango de edad: iterar getComponents() buscando el JRadioButton seleccionado.
        //    Usamos "continuar" para parar en cuanto encontremos el seleccionado.
        String rangoEdad = "";
        boolean continuar = true;
        Component[] arrayComponentes = getComponents();   // todos los hijos del panel
        for (int i = 0; i < arrayComponentes.length && continuar; i++) {
            if (arrayComponentes[i] instanceof JRadioButton) {
                if (((JRadioButton) arrayComponentes[i]).isSelected()) {
                    rangoEdad = ((JRadioButton) arrayComponentes[i]).getText();
                    continuar = false;   // ya encontramos el marcado, paramos
                }
            }
        }

        // 2. Frecuencia: getSelectedItem() devuelve Object → cast a String.
        String frecuencia = (String) cmbFrecuencia.getSelectedItem();

        // 3. Series marcadas: iterar todos los componentes buscando JCheckBox seleccionados.
        ArrayList<String> listaSeriesVistas = new ArrayList<String>();
        for (Component component : arrayComponentes) {
            if (component instanceof JCheckBox) {
                if (((JCheckBox) component).isSelected()) {
                    listaSeriesVistas.add(((JCheckBox) component).getText());
                }
            }
        }

        // 4. Construir y devolver el objeto Encuesta con los datos leídos.
        return new Encuesta(rangoEdad, frecuencia, listaSeriesVistas);
    }

    // ── LIMPIAR EL FORMULARIO ─────────────────────────────────────────────────
    // Resetea el formulario a su estado inicial. El controlador lo llama
    // después de guardar la encuesta para dejar el panel listo para la siguiente.
    public void limpiarEncuesta() {
        rdbtn517.setSelected(true);          // volver al primer radio button
        cmbFrecuencia.setSelectedIndex(0);   // volver al primer elemento del combo

        // Desmarcar todos los checkboxes iterando los componentes del panel.
        Component[] arrayComponentes = getComponents();
        for (Component component : arrayComponentes) {
            if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(false);
            }
        }
    }

    // Muestra un diálogo informativo. El controlador lo llama para dar feedback al usuario.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado de la operación",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
