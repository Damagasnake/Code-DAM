import java.awt.EventQueue;

import com.dam.a.control.ControladorEncuestas;
import com.dam.a.model.DatosEncuestas;
import com.dam.a.view.PRealizarEncuestas;
import com.dam.a.view.PVisualizarEncuestas;
import com.dam.a.view.VPrincipalEncuestas;

// PUNTO DE ENTRADA — Crea y conecta todas las piezas del patrón MVC.
//
// ¿Por qué EventQueue.invokeLater?
// Swing NO es thread-safe: todos los cambios en componentes gráficos deben
// ejecutarse en el Event Dispatch Thread (EDT). invokeLater encola el Runnable
// para que se ejecute en ese hilo especial, de forma segura.
public class Inicio {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                // ── 1. Crear las VISTAS ────────────────────────────────────────
                // Se crean primero porque el controlador las necesita como argumentos.
                // VPrincipalEncuestas lee los insets de la ventana en su constructor,
                // por eso los paneles DEBEN crearse después de la ventana.
                VPrincipalEncuestas vp  = new VPrincipalEncuestas();
                PRealizarEncuestas  pre = new PRealizarEncuestas();
                PVisualizarEncuestas pve = new PVisualizarEncuestas();

                // ── 2. Crear el MODELO ────────────────────────────────────────
                DatosEncuestas datos = new DatosEncuestas();

                // ── 3. Crear el CONTROLADOR ───────────────────────────────────
                // Inyección de dependencias: recibe vistas y modelo.
                // No los crea él mismo → más fácil de modificar y testear.
                ControladorEncuestas control = new ControladorEncuestas(vp, pre, pve, datos);

                // ── 4. Inyectar el controlador en las vistas ──────────────────
                // Ahora las vistas pueden registrar el controlador como listener
                // en sus componentes (addActionListener(control)).
                // Solo es posible aquí porque hasta el paso 3 el controlador no existía.
                vp.setControlador(control);
                pre.setControlador(control);
                pve.setControlador(control);

                // ── 5. Mostrar la ventana ─────────────────────────────────────
                // Al final: la ventana aparece ya completamente configurada,
                // con el controlador registrado y todo listo para interactuar.
                vp.hacerVisible();
            }
        });
    }
}
