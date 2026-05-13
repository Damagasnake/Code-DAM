package view;

import control.ConsultaResController;
import javax.swing.JPanel;

import javax.swing.*;

// VIEW — Interfaz (contrato) que debe cumplir cualquier ventana principal.
// Principio clave: el controlador programa CONTRA esta interfaz,
// no contra VPrincipalEncuestas directamente → si cambiamos la implementación,
// el controlador no necesita cambiar.
public interface IVPrincipalRestaurantes {

    // Configura parámetros básicos: título, tamaño, centrado, menú.
    public void configurarVentana();

    // Crea y añade los componentes Swing al contenedor (JScrollPane central).
    public void crearComponentes();

    // Crea la barra de menú con sus ítems.
    public void crearMenu();

    // Inyecta el controlador para que los componentes puedan registrarlo como listener.
    // Se llama DESPUÉS de crear el controlador en Inicio.java.
    public void setControlador(ConsultaResController consultaResController);

    // Hace visible la ventana. Siempre al final, cuando todo está listo.
    public void hacerVisible();

    // Intercambia el panel mostrado en el JScrollPane central.
    // Así una sola ventana muestra distintas "pantallas" sin abrir ventanas nuevas.
    public void cargarPanel(JPanel panel);
}