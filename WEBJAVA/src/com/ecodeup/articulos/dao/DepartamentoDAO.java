package com.ecodeup.articulos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ecodeup.articulos.model.Conexion;
import com.ecodeup.articulos.model.Departamento;

/*
 * @autor: Elivar Largo
 * @web: www.ecodeup.com
 */

public class DepartamentoDAO {
	private Conexion con;
	private Connection connection;

	public DepartamentoDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) throws SQLException {
		System.out.println(jdbcURL);
		con = new Conexion(jdbcURL, jdbcUsername, jdbcPassword);
	}

	// insertar artículo
	public boolean insertar(Departamento departamento) throws SQLException {
		String sql = "INSERT INTO Departamentos (id, codigo, nombre, descripcion) VALUES (?, ?, ?, ?)";
		System.out.println(departamento.getDescripcion());
		System.out.println(departamento.getNombre());
		System.out.println(departamento.getCodigo());

		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, null);
		statement.setString(2, departamento.getCodigo());
		statement.setString(3, departamento.getNombre());
		statement.setString(4, departamento.getDescripcion());
	
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return rowInserted;
	}

	// listar todos los productos
	public List<Departamento> listardepartamento() throws SQLException {

		List<Departamento> listadepartamentos = new ArrayList<Departamento>();
		String sql = "SELECT * FROM Departamentos";
		con.conectar();
		connection = con.getJdbcConnection();
		Statement statement = connection.createStatement();
		ResultSet resulSet = statement.executeQuery(sql);

		while (resulSet.next()) {
			int id = resulSet.getInt("id");
			String codigo = resulSet.getString("codigo");
			String nombre = resulSet.getString("nombre");
			String descripcion = resulSet.getString("descripcion");
		
			Departamento departamento = new Departamento(id, codigo, nombre, descripcion);
			listadepartamentos.add(departamento);
		}
		con.desconectar();
		return listadepartamentos;
	}

	// obtener por id
	public Departamento obtenerPorId(int id) throws SQLException {
		Departamento departamento = null;

		String sql = "SELECT * FROM Departamentos WHERE id= ? ";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);

		ResultSet res = statement.executeQuery();
		if (res.next()) {
			departamento = new Departamento(res.getInt("id"), res.getString("codigo"), res.getString("nombre"),
					res.getString("descripcion"));
		}
		res.close();
		con.desconectar();

		return departamento;
	}

	// actualizar
	public boolean actualizar(Departamento departamento) throws SQLException {
		boolean rowActualizar = false;
		String sql = "UPDATE Departamentos SET codigo=?,nombre=?,descripcion=? WHERE id=?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, departamento.getCodigo());
		statement.setString(2, departamento.getNombre());
		statement.setString(3, departamento.getDescripcion());
		statement.setInt(4, departamento.getId());

		rowActualizar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return rowActualizar;
	}
	
	//eliminar
	public boolean eliminar(Departamento departamento) throws SQLException {
		boolean rowEliminar = false;
		String sql = "DELETE FROM Departamentos WHERE ID=?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, departamento.getId());

		rowEliminar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();

		return rowEliminar;
	}

}