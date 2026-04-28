import java.sql.*;
import java.util.ArrayList;

public class TablaPruebaPersistencia {
    static final String NOM_TABLA = "Damaga";
    static final String NOM_COLUMNA_ID = "ID";
    static final String NOM_COLUMNA_DESCRIPCION = "DESCRIPTION";
    private AccesoDBProp accesoDBProp;

    public TablaPruebaPersistencia(){
        accesoDBProp = new AccesoDBProp();
    }

    public ArrayList<regTablaDamaga> runSelect() {
        ArrayList<regTablaDamaga> listaDamaga = new ArrayList<>();
        String sentenciaSelect = "SELECT " + NOM_COLUMNA_ID + ", " + NOM_COLUMNA_DESCRIPCION + " FROM " + NOM_TABLA;
        Connection conexion = null;
        Statement query = null;
        ResultSet rs = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.createStatement();
            rs = query.executeQuery(sentenciaSelect);
            int id;
            String descripcion;

            while (rs.next()) {
                id = rs.getInt(NOM_COLUMNA_ID);
                descripcion = rs.getString(NOM_COLUMNA_DESCRIPCION);
                regTablaDamaga reg = new regTablaDamaga(id, descripcion);
                listaDamaga.add(reg);
                System.out.println("ID: " + id + " Descripcion: " + descripcion);
            }

            System.out.println("Se ejecuto correctamente la sentencia");
        } catch (Exception e) {
            System.out.println("Error al ejecutar select: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar resultSet: " + e.getMessage());
            }

            try {
                if (query != null) {
                    query.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar statement: " + e.getMessage());
            }

            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return listaDamaga;
    }
    public ArrayList<regTablaDamaga> runSelectId(int idBuscado) {
        ArrayList<regTablaDamaga> listaDamaga = new ArrayList<>();
        String sentenciaSelect = "SELECT " + NOM_COLUMNA_ID + ", " + NOM_COLUMNA_DESCRIPCION + " FROM " + NOM_TABLA + " WHERE " + NOM_COLUMNA_ID + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;
        ResultSet rs = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaSelect);
            query.setInt(1, idBuscado);
            rs = query.executeQuery();

            int id;
            String descripcion;

            while (rs.next()) {
                id = rs.getInt(NOM_COLUMNA_ID);
                descripcion = rs.getString(NOM_COLUMNA_DESCRIPCION);
                regTablaDamaga reg = new regTablaDamaga(id, descripcion);
                listaDamaga.add(reg);
                System.out.println("ID: " + id + " Descripcion: " + descripcion);
            }

            System.out.println("Se ejecuto correctamente la sentencia");
        } catch (Exception e) {
            System.out.println("Error al ejecutar select: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar resultSet: " + e.getMessage());
            }

            try {
                if (query != null) {
                    query.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar statement: " + e.getMessage());
            }

            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }

        return listaDamaga;
    }
    public void runInsert(regTablaDamaga reg) {
        String sentenciaInsert = "INSERT INTO " + NOM_TABLA + " (" + NOM_COLUMNA_DESCRIPCION + ") VALUES (?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = accesoDBProp.getConexion();
            preparedStatement = connection.prepareStatement(sentenciaInsert);
            preparedStatement.setString(1, reg.getDescripcion());

            preparedStatement.executeUpdate();
            System.out.println("Se ejecuto correctamente la sentencia de insercion");
        } catch (Exception e) {
            System.out.println("Error al ejecutar insert: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }
}
