package atendaservices.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.OpinionDAO;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.model.Opinion;
import atendaservices.model.Produto;

public class OpinionDAOImpl implements OpinionDAO {

	private static final Logger logger = Logger.getAnonymousLogger();
	
	@Override
	public ArrayList<Opinion> getOpinions(Produto produto) throws Exception {
		ArrayList<Opinion> opinions= new ArrayList<Opinion>();
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		try {
			sql.append("select o.id, o.idUsuario, o.idProduto, o.valoracion, o.texto, o.data_hora from opinion as o where idProduto = ?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i=1;
			preparedStatement.setLong(i++, produto.getId());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Opinion opinion=loadNext(connection, resultSet);
				opinions.add(opinion);
			}
			return opinions;
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
	public int inserta(Opinion comentario) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Connection connection=ConnectionManager.getConnection();
		
		try {
			sql.append("insert into opinion (idUsuario, idProduto, valoracion, texto, data_hora)"+
					"values (?,?,?,?,?)");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setLong(1, comentario.getUsuario());
			preparedStatement.setLong(2, comentario.getIdProduto());
			preparedStatement.setString(3, comentario.getValoracion().toString());
			preparedStatement.setString(4, comentario.getTexto());
			preparedStatement.setTimestamp(5, Timestamp.valueOf(comentario.getData()));
			
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			return 1;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection(connection);
		}
		return 0;
	}

	private Opinion loadNext(Connection connection, ResultSet resultSet) {
		int i = 1;
		Opinion opinion=new Opinion();
		
		try {
			opinion.setId(resultSet.getLong(i++));
			opinion.setUsuario(resultSet.getLong(i++));
			opinion.setIdProduto(resultSet.getLong(i++));
			opinion.setValoracion(Integer.parseInt(resultSet.getString(i++)));
			opinion.setTexto(resultSet.getString(i++));
			opinion.setData(resultSet.getDate(i++).toLocalDate().atStartOfDay());
			
			return opinion;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
