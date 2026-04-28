import java.sql.*;

public class UsuariosDAO {
    static final String NOM_TABLA = "USUARIOS";
    static final String NOM_COLUMNA_USR = "USUARIO";
    static final String NOM_COLUMNA_PWD = "PASSWORD";
    private AccesoDBProp accesoDBProp;

    public UsuariosDAO() {
        this.accesoDBProp = new AccesoDBProp();
    }

    /**
     * Valida las credenciales de un usuario (login)
     * @param usuario nombre de usuario
     * @param password contraseña
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String usuario, String password) {
        String sentenciaSelect = "SELECT " + NOM_COLUMNA_PWD + " FROM " + NOM_TABLA + 
                                 " WHERE " + NOM_COLUMNA_USR + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;
        ResultSet rs = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaSelect);
            query.setString(1, usuario);
            rs = query.executeQuery();

            if (rs.next()) {
                String passwordBD = rs.getString(NOM_COLUMNA_PWD);
                return passwordBD.equals(password);
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al validar credenciales: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(rs, query, conexion);
        }
    }

    /**
     * Obtiene un usuario por su nombre de usuario
     * @param usuario nombre de usuario
     * @return objeto Usuario si existe, null en caso contrario
     */
    public Usuario obtenerUsuario(String usuario) {
        String sentenciaSelect = "SELECT " + NOM_COLUMNA_USR + ", " + NOM_COLUMNA_PWD + 
                                 " FROM " + NOM_TABLA + " WHERE " + NOM_COLUMNA_USR + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;
        ResultSet rs = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaSelect);
            query.setString(1, usuario);
            rs = query.executeQuery();

            if (rs.next()) {
                String usr = rs.getString(NOM_COLUMNA_USR);
                String pwd = rs.getString(NOM_COLUMNA_PWD);
                return new Usuario(usr, pwd);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error al obtener usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            cerrarRecursos(rs, query, conexion);
        }
    }

    /**
     * Inserta un nuevo usuario en la base de datos
     * @param usuario objeto Usuario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertarUsuario(Usuario usuario) {
        String sentenciaInsert = "INSERT INTO " + NOM_TABLA + " (" + NOM_COLUMNA_USR + ", " + 
                                 NOM_COLUMNA_PWD + ") VALUES (?, ?)";
        Connection conexion = null;
        PreparedStatement query = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaInsert);
            query.setString(1, usuario.getUsuario());
            query.setString(2, usuario.getPassword());
            
            int filasAfectadas = query.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, query, conexion);
        }
    }

    /**
     * Actualiza la contraseña de un usuario
     * @param usuario nombre de usuario
     * @param nuevoPassword nueva contraseña
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarPassword(String usuario, String nuevoPassword) {
        String sentenciaUpdate = "UPDATE " + NOM_TABLA + " SET " + NOM_COLUMNA_PWD + 
                                 " = ? WHERE " + NOM_COLUMNA_USR + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaUpdate);
            query.setString(1, nuevoPassword);
            query.setString(2, usuario);
            
            int filasAfectadas = query.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar password: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, query, conexion);
        }
    }

    /**
     * Elimina un usuario de la base de datos
     * @param usuario nombre de usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarUsuario(String usuario) {
        String sentenciaDelete = "DELETE FROM " + NOM_TABLA + " WHERE " + NOM_COLUMNA_USR + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaDelete);
            query.setString(1, usuario);
            
            int filasAfectadas = query.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(null, query, conexion);
        }
    }

    /**
     * Verifica si un usuario existe en la base de datos
     * @param usuario nombre de usuario
     * @return true si existe, false en caso contrario
     */
    public boolean usuarioExiste(String usuario) {
        String sentenciaSelect = "SELECT 1 FROM " + NOM_TABLA + " WHERE " + NOM_COLUMNA_USR + " = ?";
        Connection conexion = null;
        PreparedStatement query = null;
        ResultSet rs = null;

        try {
            conexion = accesoDBProp.getConexion();
            query = conexion.prepareStatement(sentenciaSelect);
            query.setString(1, usuario);
            rs = query.executeQuery();
            
            return rs.next();
        } catch (Exception e) {
            System.out.println("Error al verificar existencia de usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            cerrarRecursos(rs, query, conexion);
        }
    }

    /**
     * Método auxiliar para cerrar recursos de base de datos
     */
    private void cerrarRecursos(ResultSet rs, PreparedStatement query, Connection conexion) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar ResultSet: " + e.getMessage());
            }
        }

        if (query != null) {
            try {
                query.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }

        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar Connection: " + e.getMessage());
            }
        }
    }
}
