package control;

import model.Restaurantes;
import view.ConsultaRes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ConsultaResController implements ActionListener {
    private final ConsultaRes vista;
    private final RestauranteDAO restauranteDAO;

    public ConsultaResController(ConsultaRes vista, RestauranteDAO restauranteDAO) {
        this.vista = vista;
        this.restauranteDAO = restauranteDAO;

        this.vista.addConsultarListener(this);
        this.vista.addEliminarListener(this);

        cargarRegiones();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();

        if (ConsultaRes.AC_BTN_Cons.equals(accion)) {
            consultarRestaurantes();
        } else if (ConsultaRes.AC_BTN_Del.equals(accion)) {
            eliminarRestauranteSeleccionado();
        }
    }

    private void cargarRegiones() {
        try {
            List<String> regiones = restauranteDAO.obtenerRegiones();
            vista.setRegiones(regiones);
        } catch (SQLException ex) {
            vista.mostrarMensaje("No se han podido cargar las regiones: " + ex.getMessage());
        }
    }

    private void consultarRestaurantes() {
        try {
            List<Restaurantes> restaurantes = restauranteDAO.buscarRestaurantes(
                    vista.getRegionSeleccionada(),
                    vista.getDistincionSeleccionada()
            );

            if (restaurantes.isEmpty()) {
                vista.limpiarTabla();
                vista.mostrarMensaje("No se han encontrado restaurantes.");
                return;
            }

            vista.mostrarRestaurantes(restaurantes);
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al consultar los restaurantes: " + ex.getMessage());
        }
    }

    private void eliminarRestauranteSeleccionado() {
        int idSeleccionado = vista.getIdSeleccionado();
        if (idSeleccionado < 0) {
            vista.mostrarMensaje("Debes seleccionar un restaurante para eliminarlo.");
            return;
        }

        int respuesta = vista.confirmarEliminacion("¿Seguro que deseas eliminar el restaurante seleccionado?");
        if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean eliminado = restauranteDAO.eliminarPorId(idSeleccionado);
            if (eliminado) {
                vista.mostrarMensaje("Restaurante eliminado correctamente.");
                consultarRestaurantes();
            } else {
                vista.mostrarMensaje("No se ha podido eliminar el restaurante.");
            }
        } catch (SQLException ex) {
            vista.mostrarMensaje("Error al eliminar el restaurante: " + ex.getMessage());
        }
    }
}

