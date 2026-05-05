package control;

import model.Restaurantes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {
    private final AccesoDBprop accesoDBprop;

    public RestauranteDAO() {
        this(new AccesoDBprop());
    }

    public RestauranteDAO(AccesoDBprop accesoDBprop) {
        this.accesoDBprop = accesoDBprop;
    }

    public List<String> obtenerRegiones() throws SQLException {
        String sql = "SELECT DISTINCT REGION FROM RESTAURANTES WHERE REGION IS NOT NULL AND TRIM(REGION) <> '' ORDER BY REGION";
        ArrayList<String> regiones = new ArrayList<>();

        try (Connection connection = accesoDBprop.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                regiones.add(resultSet.getString("REGION"));
            }
        }

        return regiones;
    }

    public List<Restaurantes> buscarRestaurantes(String region, Integer distincion) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ID, NOMBRE, REGION, CIUDAD, DISTINCION, DIRECCION, PRECIO_MIN, PRECIO_MAX, COCINA, TELEFONO, WEB " + "FROM RESTAURANTES WHERE 1 = 1");
        List<Object> parametros = new ArrayList<>();

        if (region != null && !region.isBlank()) {
            sql.append(" AND REGION = ?");
            parametros.add(region);

        }

        if (distincion != null) {
            sql.append(" AND DISTINCION = ?");
            parametros.add(distincion);
        }

        sql.append(" ORDER BY NOMBRE");

        List<Restaurantes> restaurantes = new ArrayList<>();

        try (Connection connection = accesoDBprop.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                preparedStatement.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    restaurantes.add(mapearRestaurante(resultSet));
                }
            }
        }

        return restaurantes;
    }

    public boolean eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM RESTAURANTES WHERE ID = ?";

        try (Connection connection = accesoDBprop.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private Restaurantes mapearRestaurante(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("ID");
        String nombre = resultSet.getString("NOMBRE");
        String region = resultSet.getString("REGION");
        String ciudad = resultSet.getString("CIUDAD");
        int distincion = resultSet.getInt("DISTINCION");

        double precioMin = resultSet.getDouble("PRECIO_MIN");
        if (resultSet.wasNull()) {
            precioMin = 0;
        }

        double precioMax = resultSet.getDouble("PRECIO_MAX");
        if (resultSet.wasNull()) {
            precioMax = 0;
        }

        String direccion = resultSet.getString("DIRECCION");
        String cocina = resultSet.getString("COCINA");
        String telefono = resultSet.getString("TELEFONO");
        String web = resultSet.getString("WEB");

        return new Restaurantes(id, nombre, region, ciudad, distincion, direccion, precioMax, precioMin, cocina, telefono, web);
    }
}

