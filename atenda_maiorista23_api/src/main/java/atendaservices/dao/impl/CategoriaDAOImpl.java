package atendaservices.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.CategoriaDAO;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {
	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ArrayList<Categoria> getAllCategorias() throws Exception {
		ArrayList<Categoria> categorias= new ArrayList<Categoria>();
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("select c.id, c.nome from categoria as c");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Categoria categoria=loadNext(connection, resultSet);
				categorias.add(categoria);
			}
			return categorias;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void actualiza(Categoria c) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("update categoria set nome = ? where id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setString(i++, c.getNome());
			preparedStatement.setLong(i++, c.getId());
			logger.info("create statement: "+sql);
			preparedStatement.execute();
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
	}

	@Override
	public int inserta(Categoria c) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("insert into categoria (nome)"+
					" values(?)");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setString(i++, c.getNome());
			preparedStatement.execute();
			
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
		return 0;
	}

	@Override
	public void borra(Categoria c) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("delete from categoria where id = ?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setLong(i++, c.getId());
			preparedStatement.execute();
			logger.info("borrando categoria");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
	}

	@Override
	public Categoria getCategoriaPorId(Long idCategoria) throws Exception {
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("select c.id, c.nome from categoria as c where id =?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setLong(i++, idCategoria);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Categoria categoria=loadNext(connection, resultSet);
				return categoria;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
		return null;
	}
	
	public Categoria loadNext(Connection connection, ResultSet resultSet) {
		Categoria categoria=new Categoria();
		int i=1;
		try {
			categoria.setId(resultSet.getLong(i++));
			categoria.setNome(resultSet.getString(i++));
			
			return categoria;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
