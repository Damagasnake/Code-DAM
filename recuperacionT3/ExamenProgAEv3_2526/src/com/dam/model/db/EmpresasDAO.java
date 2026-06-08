package com.dam.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.dam.model.datos.Empresa;

public class EmpresasDAO {

	private final AccesoDB acceso = new AccesoDB();

	public int insertEmpresa(Empresa e) {
		int res = 0;

		String query = "INSERT INTO " + EmpresaContracts.NOM_TABLA + " ("
				+ EmpresaContracts.COL_ID + ", "
				+ EmpresaContracts.COL_RAZON + ", "
				+ EmpresaContracts.COL_DOMICILIO + ", "
				+ EmpresaContracts.COL_REPRESENTANTE + ", "
				+ EmpresaContracts.COL_REPRESENTANTE_MAIL + ", "
				+ EmpresaContracts.COL_CONVENIO + ", "
				+ EmpresaContracts.COL_NUM_EMP + ", "
				+ EmpresaContracts.COL_TLF + ", "
				+ EmpresaContracts.COL_WEB
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = acceso.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, e.getCif());
			stmt.setString(2, e.getRazonSocial());
			stmt.setString(3, e.getDomicilio());
			stmt.setString(4, e.getRepresentante());
			stmt.setString(5, e.getCorreoRL());
			stmt.setString(6, e.getConvenio());
			stmt.setInt(7, e.getNumEmpleados());
			stmt.setString(8, e.getTelefono());
			stmt.setString(9, e.getWeb());

			res = stmt.executeUpdate();

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			cerrar(stmt, con);
		}

		return res;
	}

	public ArrayList<Empresa> buscarEmpresas(String cif, String razon) {
		ArrayList<Empresa> lista = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT * FROM " + EmpresaContracts.NOM_TABLA);
		ArrayList<String> condiciones = new ArrayList<>();

		if (cif != null && !cif.isEmpty()) {
			condiciones.add(EmpresaContracts.COL_ID + " = ?");
		}
		if (razon != null && !razon.isEmpty()) {
			condiciones.add(EmpresaContracts.COL_RAZON + " LIKE ?");
		}

		if (!condiciones.isEmpty()) {
			query.append(" WHERE ").append(String.join(" OR ", condiciones));
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = acceso.getConexion();
			stmt = con.prepareStatement(query.toString());
			int param = 1;
			if (cif != null && !cif.isEmpty()) {
				stmt.setString(param++, cif);
			}
			if (razon != null && !razon.isEmpty()) {
				stmt.setString(param, razon + "%");
			}
			rs = stmt.executeQuery();
			while (rs.next()) {
				lista.add(mapearEmpresa(rs));
			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			cerrar(rs, stmt, con);
		}

		return lista;
	}

	public Empresa obtenerEmpresaPorCif(String cif) {
		Empresa empresa = null;
		String query = "SELECT * FROM " + EmpresaContracts.NOM_TABLA
				+ " WHERE " + EmpresaContracts.COL_ID + " = ?";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = acceso.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, cif);
			rs = stmt.executeQuery();
			if (rs.next()) {
				empresa = mapearEmpresa(rs);
			}
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			cerrar(rs, stmt, con);
		}

		return empresa;
	}

	public int updateEmpresa(Empresa e) {
		int res = 0;
		String query = "UPDATE " + EmpresaContracts.NOM_TABLA + " SET "
				+ EmpresaContracts.COL_RAZON + " = ?, "
				+ EmpresaContracts.COL_DOMICILIO + " = ?, "
				+ EmpresaContracts.COL_REPRESENTANTE + " = ?, "
				+ EmpresaContracts.COL_REPRESENTANTE_MAIL + " = ?, "
				+ EmpresaContracts.COL_CONVENIO + " = ?, "
				+ EmpresaContracts.COL_NUM_EMP + " = ?, "
				+ EmpresaContracts.COL_TLF + " = ?, "
				+ EmpresaContracts.COL_WEB + " = ? "
				+ "WHERE " + EmpresaContracts.COL_ID + " = ?";

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = acceso.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, e.getRazonSocial());
			stmt.setString(2, e.getDomicilio());
			stmt.setString(3, e.getRepresentante());
			stmt.setString(4, e.getCorreoRL());
			stmt.setString(5, e.getConvenio());
			stmt.setInt(6, e.getNumEmpleados());
			stmt.setString(7, e.getTelefono());
			stmt.setString(8, e.getWeb());
			stmt.setString(9, e.getCif());
			res = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			cerrar(stmt, con);
		}

		return res;
	}

	public int eliminarEmpresa(String cif) {
		int res = 0;
		String query = "DELETE FROM " + EmpresaContracts.NOM_TABLA
				+ " WHERE " + EmpresaContracts.COL_ID + " = ?";

		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = acceso.getConexion();
			stmt = con.prepareStatement(query);
			stmt.setString(1, cif);
			res = stmt.executeUpdate();
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		} finally {
			cerrar(stmt, con);
		}

		return res;
	}

	private Empresa mapearEmpresa(ResultSet rs) throws SQLException {
		return new Empresa(
				rs.getString(EmpresaContracts.COL_ID),
				rs.getString(EmpresaContracts.COL_RAZON),
				rs.getString(EmpresaContracts.COL_DOMICILIO),
				rs.getString(EmpresaContracts.COL_REPRESENTANTE),
				rs.getString(EmpresaContracts.COL_REPRESENTANTE_MAIL),
				rs.getString(EmpresaContracts.COL_CONVENIO),
				rs.getInt(EmpresaContracts.COL_NUM_EMP),
				rs.getString(EmpresaContracts.COL_TLF),
				rs.getString(EmpresaContracts.COL_WEB));
	}

	private void cerrar(AutoCloseable... recursos) {
		for (AutoCloseable recurso : recursos) {
			if (recurso != null) {
				try {
					recurso.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
