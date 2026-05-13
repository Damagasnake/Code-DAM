package com.michelin.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.michelin.model.data.Restaurante;
import com.michelin.model.data.Texto;

public class RestaurantesDAO {
	
	private AccesoDB acceso;
	
	public RestaurantesDAO() {
		acceso = new AccesoDB();
	}
	
	public ArrayList<String> selectRegiones() {
		ArrayList<String> listaRegiones = new ArrayList<String>();
		
		String query = "SELECT DISTINCT " + RestaurantesContract.COL_REGION 
				+ " FROM " + RestaurantesContract.NOM_TABLA;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.createStatement();
			rslt = stmt.executeQuery(query);
			
			while (rslt.next()) {
				
				listaRegiones.add(rslt.getString(1));
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRegiones;
	}

	public ArrayList<Restaurante> selectRestaurantes() {
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		// CONSULTAR TODOS LOS RESTAURANTES
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.createStatement();
			rslt = stmt.executeQuery(query);
			
			Restaurante rest = null;
			while (rslt.next()) {
				rest = new Restaurante(rslt.getInt(1), 
						rslt.getString(RestaurantesContract.COL_NOMBRE), 
						rslt.getString(RestaurantesContract.COL_REGION), 
						rslt.getString(RestaurantesContract.COL_CIUDAD), 
						rslt.getInt(RestaurantesContract.COL_DISTINCION), 
						rslt.getString(RestaurantesContract.COL_DIRECCION), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MIN), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MAX), 
						rslt.getString(RestaurantesContract.COL_COCINA), 
						rslt.getString(RestaurantesContract.COL_TELEF), 
						rslt.getString(RestaurantesContract.COL_WEB));
				listaRestaurantes.add(rest);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRestaurantes;
	}
	
	public ArrayList<Restaurante> selectRestaurantesReg(String reg) {
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		// CONSULTAR RESTAURANTES DE UNA REGIÓN
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA
				+ " WHERE " + RestaurantesContract.COL_REGION + " = ?";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, reg);
			
			rslt = stmt.executeQuery();
			
			Restaurante rest = null;
			while (rslt.next()) {
				rest = new Restaurante(rslt.getInt(1), 
						rslt.getString(RestaurantesContract.COL_NOMBRE), 
						rslt.getString(RestaurantesContract.COL_REGION), 
						rslt.getString(RestaurantesContract.COL_CIUDAD), 
						rslt.getInt(RestaurantesContract.COL_DISTINCION), 
						rslt.getString(RestaurantesContract.COL_DIRECCION), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MIN), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MAX), 
						rslt.getString(RestaurantesContract.COL_COCINA), 
						rslt.getString(RestaurantesContract.COL_TELEF), 
						rslt.getString(RestaurantesContract.COL_WEB));
				listaRestaurantes.add(rest);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRestaurantes;
	}
	
	public ArrayList<Restaurante> selectRestaurantesDist(int dist) {
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		// CONSULTAR RESTAURANTES DE UNA DISTINCIÓN
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA
				+ " WHERE " + RestaurantesContract.COL_DISTINCION + " = ?";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, dist);
			
			rslt = stmt.executeQuery();
			
			Restaurante rest = null;
			while (rslt.next()) {
				rest = new Restaurante(rslt.getInt(1), 
						rslt.getString(RestaurantesContract.COL_NOMBRE), 
						rslt.getString(RestaurantesContract.COL_REGION), 
						rslt.getString(RestaurantesContract.COL_CIUDAD), 
						rslt.getInt(RestaurantesContract.COL_DISTINCION), 
						rslt.getString(RestaurantesContract.COL_DIRECCION), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MIN), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MAX), 
						rslt.getString(RestaurantesContract.COL_COCINA), 
						rslt.getString(RestaurantesContract.COL_TELEF), 
						rslt.getString(RestaurantesContract.COL_WEB));
				listaRestaurantes.add(rest);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRestaurantes;
	}
	
	public ArrayList<Restaurante> selectRestaurantesFiltro(String reg, int dist) {
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		// CONSULTAR RESTAURANTES DE UNA REGIÓN Y UNA DISTINCIÓN
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA
				+ " WHERE " + RestaurantesContract.COL_REGION + " = ? AND "
				+ RestaurantesContract.COL_DISTINCION + " = ?";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, reg);
			stmt.setInt(2, dist);
			
			rslt = stmt.executeQuery();
			
			Restaurante rest = null;
			while (rslt.next()) {
				rest = new Restaurante(rslt.getInt(1), 
						rslt.getString(RestaurantesContract.COL_NOMBRE), 
						rslt.getString(RestaurantesContract.COL_REGION), 
						rslt.getString(RestaurantesContract.COL_CIUDAD), 
						rslt.getInt(RestaurantesContract.COL_DISTINCION), 
						rslt.getString(RestaurantesContract.COL_DIRECCION), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MIN), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MAX), 
						rslt.getString(RestaurantesContract.COL_COCINA), 
						rslt.getString(RestaurantesContract.COL_TELEF), 
						rslt.getString(RestaurantesContract.COL_WEB));
				listaRestaurantes.add(rest);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRestaurantes;
	}
	
	// todas las consultas en un solo método
	public ArrayList<Restaurante> selectRestaurantesFiltro(String reg, String dist) {
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		// CONSULTA DEPENDIENDO DE LOS VALORES DE reg y dist
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA;
		
		if (!reg.equals(Texto.TODAS) && !dist.equals(Texto.TODAS)) {
			query += " WHERE " + RestaurantesContract.COL_REGION + " = ? AND "
				+ RestaurantesContract.COL_DISTINCION + " = ?";
		} else if (!reg.equals(Texto.TODAS)) {
			query += " WHERE " + RestaurantesContract.COL_REGION + " = ?";
		} else if (!dist.equals(Texto.TODAS)) {
			query += " WHERE " + RestaurantesContract.COL_DISTINCION + " = ?";
		}
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			
			if (!reg.equals(Texto.TODAS) && !dist.equals(Texto.TODAS)) {
				stmt.setString(1, reg);
				stmt.setInt(2, Character.getNumericValue(dist.charAt(0)));
			} else if (!reg.equals(Texto.TODAS)) {
				stmt.setString(1, reg);
			} else if (!dist.equals(Texto.TODAS)) {
				stmt.setInt(1, Character.getNumericValue(dist.charAt(0)));
			}		
			
			rslt = stmt.executeQuery();
			
			Restaurante rest = null;
			while (rslt.next()) {
				rest = new Restaurante(rslt.getInt(1), 
						rslt.getString(RestaurantesContract.COL_NOMBRE), 
						rslt.getString(RestaurantesContract.COL_REGION), 
						rslt.getString(RestaurantesContract.COL_CIUDAD), 
						rslt.getInt(RestaurantesContract.COL_DISTINCION), 
						rslt.getString(RestaurantesContract.COL_DIRECCION), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MIN), 
						rslt.getDouble(RestaurantesContract.COL_PREC_MAX), 
						rslt.getString(RestaurantesContract.COL_COCINA), 
						rslt.getString(RestaurantesContract.COL_TELEF), 
						rslt.getString(RestaurantesContract.COL_WEB));
				listaRestaurantes.add(rest);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return listaRestaurantes;
	}

	public int insertRestaurante(Restaurante restaurante) {
		int res = 0;
		// INSERTAR RESTAURANTE
		String query = "INSERT INTO " + RestaurantesContract.NOM_TABLA + " ("
				+ RestaurantesContract.COL_NOMBRE + ", " + RestaurantesContract.COL_REGION + ", " 
						+ RestaurantesContract.COL_CIUDAD + ", " + RestaurantesContract.COL_DISTINCION 
				+ ", " + RestaurantesContract.COL_DIRECCION + ", " + RestaurantesContract.COL_PREC_MIN 
				+ ", " + RestaurantesContract.COL_PREC_MAX + ", " + RestaurantesContract.COL_COCINA 
				+ ", " + RestaurantesContract.COL_TELEF + ", " + RestaurantesContract.COL_WEB
				+") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, restaurante.getNombre());
			stmt.setString(2, restaurante.getRegion());
			stmt.setString(3, restaurante.getCiudad());
			stmt.setInt(4, restaurante.getDistincion());
			stmt.setString(5, restaurante.getDireccion());
			stmt.setDouble(6, restaurante.getPrecioMin());
			stmt.setDouble(7, restaurante.getPrecioMax());
			stmt.setString(8, restaurante.getCocina());
			stmt.setString(9, restaurante.getTelefono());
			stmt.setString(10, restaurante.getWeb());
			
			res = stmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	public int deleteRestaurante(String nombre) {
		int res = 0;
		String query = "DELETE FROM " + RestaurantesContract.NOM_TABLA 
				+ " WHERE " + RestaurantesContract.COL_NOMBRE + " = ?";
		
		// BORRAR RESTAURANTE POR NOMBRE
		Connection con = null;
		PreparedStatement stmt = null;
		
		try {
			con = acceso.getConexion();
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, nombre);
			
			res = stmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

	public Restaurante selectRestauranteNombre(String nombre) {
		Restaurante restaurante = null;
		
		String query = "SELECT * FROM " + RestaurantesContract.NOM_TABLA 
				+ " WHERE " + RestaurantesContract.COL_NOMBRE + " LIKE ? "; 
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rslt = null;
		
		try {
			con = acceso.getConexion();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + nombre + "%");
			
			rslt = pstmt.executeQuery();
			
			if (rslt.next()) {
				
				restaurante = new Restaurante(rslt.getInt(1), rslt.getString(2), rslt.getString(3), 
						rslt.getString(4), rslt.getInt(5), rslt.getString(6), rslt.getDouble(7), rslt.getDouble(8),
						rslt.getString(9), rslt.getString(10), rslt.getString(11));
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rslt != null) rslt.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return restaurante;
	}

	public int updateRestaurante(Restaurante restaurante) {
		int res = 0;
		
		// MODIFICAR RESTAURANTE POR NOMBRE
		String query = "UPDATE " + RestaurantesContract.NOM_TABLA + " SET "
				+ RestaurantesContract.COL_REGION + " = ?, "
				+ RestaurantesContract.COL_CIUDAD + " = ?, " 
				+ RestaurantesContract.COL_DISTINCION + " = ?, " 
				+ RestaurantesContract.COL_DIRECCION + " = ?, " 
				+ RestaurantesContract.COL_PREC_MIN + " = ?, "
				+ RestaurantesContract.COL_PREC_MAX + " = ?, " 
				+ RestaurantesContract.COL_COCINA + " = ?, " 
				+ RestaurantesContract.COL_TELEF + " = ?, " 
				+ RestaurantesContract.COL_WEB + " = ? "
				+ "WHERE " + RestaurantesContract.COL_ID + " = ?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = acceso.getConexion();
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, restaurante.getRegion());
			pstmt.setString(2, restaurante.getCiudad());
			pstmt.setInt(3, restaurante.getDistincion());
			pstmt.setString(4, restaurante.getDireccion());
			pstmt.setDouble(5, restaurante.getPrecioMin());
			pstmt.setDouble(6, restaurante.getPrecioMax());
			pstmt.setString(7, restaurante.getCocina());
			pstmt.setString(8, restaurante.getTelefono());
			pstmt.setString(9, restaurante.getWeb());
			pstmt.setInt(10, restaurante.getId());
			
			res = pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return res;
	}

}
