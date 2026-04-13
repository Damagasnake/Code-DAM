package com.dam.a.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.dam.a.model.DatosEncuestas;
import com.dam.a.model.Encuesta;
import com.dam.a.view.PRealizarEncuestas;
import com.dam.a.view.PVisualizarEncuestas;
import com.dam.a.view.VPrincipalEncuestas;

// CONTROLLER — El cerebro de la aplicación.
// Implementa ActionListener: Swing lo llama automáticamente cuando el usuario
// hace clic en cualquier componente al que este controlador esté registrado.
//
// Es el ÚNICO que conoce a todos: tiene referencias a la ventana, los dos
// paneles y el modelo. Las vistas NO se conocen entre sí.
public class ControladorEncuestas implements ActionListener {

    // Referencias a todas las capas.
    private VPrincipalEncuestas vpe;   // ventana principal (para cambiar paneles)
    private PRealizarEncuestas  pre;   // panel de formulario
    private PVisualizarEncuestas pve;  // panel de listado
    private DatosEncuestas datos;      // modelo (repositorio de encuestas)

    // Constructor: recibe todo por inyección de dependencias (desde Inicio.java).
    // No crea nada por su cuenta → es fácil de sustituir en tests.
    public ControladorEncuestas(VPrincipalEncuestas vpe, PRealizarEncuestas pre,
                                 PVisualizarEncuestas pve, DatosEncuestas datos) {
        this.vpe   = vpe;
        this.pre   = pre;
        this.pve   = pve;
        this.datos = datos;
    }

    // ── actionPerformed ───────────────────────────────────────────────────────
    // Swing llama a este método cada vez que ocurre un evento de acción
    // (clic en botón o ítem de menú) en un componente que registró este listener.
    //
    // e.getSource()       → el objeto que disparó el evento (JButton, JMenuItem...)
    // e.getActionCommand() → el texto/comando del componente (String)
    @Override
    public void actionPerformed(ActionEvent e) {
        Object componente = e.getSource();

        // ── Rama 1: el evento viene de un JMenuItem (menú de la ventana) ─────
        if (componente instanceof JMenuItem) {

            if (componente.equals(vpe.getMntmRealizar())) {
                // Usuario eligió "Realizar Encuesta" → mostrar el formulario
                vpe.cargarPanel(pre);

            } else if (componente.equals(vpe.getMntmVisualizar())) {
                // Usuario eligió "Visualizar Encuestas"
                if (datos.getListaEncuestas().isEmpty()) {
                    // Sin datos: avisar y ocultar el botón de análisis
                    pve.mostrarMensaje("No hay encuestas almacenadas");
                    pve.hacerVisibleBtnVer(false);
                } else {
                    // Con datos: mostrar botón y cargar la lista en el panel
                    pve.hacerVisibleBtnVer(true);
                    pve.cargarEncuestas(datos.getListaEncuestas());
                }
                // En ambos casos se carga el panel de visualización
                vpe.cargarPanel(pve);
            }

        // ── Rama 2: el evento viene de un JButton ─────────────────────────────
        } else if (componente instanceof JButton) {

            // Usamos ActionCommand (String) en lugar de comparar referencias al botón.
            // Ventaja: el controlador no necesita tener un campo "JButton btnAdd",
            // basta con que el botón tenga configurado su ActionCommand.

            if (e.getActionCommand().equals(PRealizarEncuestas.AC_BTN_ADD)) {
                // Usuario pulsó "Añadir Encuesta"

                // 1. Leer el formulario → obtener objeto Encuesta
                Encuesta enc = pre.obtenerDatosEncuesta();

                // 2. Guardar en el modelo
                datos.addEncuesta(enc);

                // 3. Feedback al usuario
                pre.mostrarMensaje("Se ha guardado la encuesta con éxito");

                // 4. Resetear el formulario para la siguiente encuesta
                pre.limpiarEncuesta();

            } else if (e.getActionCommand().equals(PVisualizarEncuestas.AC_BTN_RESULTADOS)) {
                // Usuario pulsó "Ver Resultados"

                // 1. El modelo calcula el análisis y lo devuelve como String
                String analisis = datos.realizarAnalisis();

                // 2. La vista lo muestra en un JOptionPane
                pve.mostrarMensaje(analisis);
            }
        }
    }
}
