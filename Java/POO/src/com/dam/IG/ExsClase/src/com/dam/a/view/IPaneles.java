package com.dam.a.view;

import com.dam.a.control.ControladorEncuestas;

// VIEW — Interfaz (contrato) que debe cumplir cualquier panel de contenido.
// Todos los paneles (PRealizarEncuestas, PVisualizarEncuestas) la implementan.
public interface IPaneles {

    // Crea y configura todos los componentes Swing del panel (labels, botones, etc.).
    // Se llama desde el constructor del panel.
    public void crearComponentes();

    // Inyecta el controlador y lo registra como ActionListener
    // en los botones que tiene este panel.
    // Se llama desde Inicio.java después de crear el controlador.
    public void setControlador(ControladorEncuestas control);
}