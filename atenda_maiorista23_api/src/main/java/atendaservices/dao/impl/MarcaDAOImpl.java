package atendaservices.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.MarcaDAO;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.model.Marca;

public class MarcaDAOImpl implements MarcaDAO{
	
	private static final Logger logger = Logger.getAnonymousLogger();

	@Override
	public ArrayList<Marca> getAllMarcas() throws Exception {
		ArrayList<Marca> marcas= new ArrayList<Marca>();
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("select m.id, m.nome from marca as m");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Marca marca=loadNext(connection, resultSet);
				marcas.add(marca);
			}
			return marcas;
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
	public void actualiza(Marca m) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("update marca set nome = ? where id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setString(i++, m.getNome());
			preparedStatement.setLong(i++, m.getId());
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
	public int inserta(Marca m) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("insert into marca (nome)"+
					" values(?)");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setString(i++, m.getNome());
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
	public void borra(Marca m) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("delete from marca where id = ?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setLong(i++, m.getId());
			preparedStatement.execute();
			logger.info("borrando marca");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
	}

	@Override
	public Marca getMarcaPorId(Long idMarca) throws Exception {
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("select m.id, m.nome from marca as m where id =?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setLong(i++, idMarca);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				Marca marca=loadNext(connection, resultSet);
				return marca;
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

	public Marca loadNext(Connection connection, ResultSet resultSet) {
		Marca marca=new Marca();
		int i=1;
		try {
			marca.setId(resultSet.getLong(i++));
			marca.setNome(resultSet.getString(i++));
			
			return marca;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
