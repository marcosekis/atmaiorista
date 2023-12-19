package atendaservices.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.UsuarioDAO;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.exceptions.DataException;
import atendaservices.model.Usuario;

public class UsuarioDAOImpl implements UsuarioDAO {
	private static final Logger logger = Logger.getAnonymousLogger();
	
	@Override
	public Usuario findById(Connection connection, Long idUsuario) throws DataException {
		Usuario usuario = null;
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select u.id, u.username, u.password, u.nome, u.rol, u.avatar, u.baixa ");
			sql.append("from usuario as u ");
			sql.append("where u.id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			preparedStatement.setLong(i++, idUsuario);
			logger.info("create statement"+sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				usuario=loadNext(connection, resultSet);
			}
			return usuario;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Usuario findByEmail(Connection connection, String email) throws DataException {
		Usuario usuario = null;
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select u.id, u.username, u.password, u.nome, u.rol, u.avatar, u.baixa ");
			sql.append("from usuario as u ");
			sql.append("where u.username = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			preparedStatement.setString(i++, email);
			logger.info("create statement"+sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				usuario=loadNext(connection, resultSet);
			}
			return usuario;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public ArrayList<Usuario> findAll(Connection connection) throws DataException {
		Usuario usuario=null;
		ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select u.id, u.username, u.password, u.nome, u.rol, u.avatar, u.baixa ");
			sql.append("from usuario as u ");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			logger.info("create statement"+sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				usuario=loadNext(connection, resultSet);
				usuarios.add(usuario);
			}
			return usuarios;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Usuario create(Connection connection, Usuario usuario) throws DataException {
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Long lastId=0L;
		
		try {
			sql.append("insert into usuario (username, password, nome, rol, avatar, baixa)"+
					"values (?,?,?,?,?,?)");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setString(1, usuario.getUsername());
			preparedStatement.setString(2, usuario.getPassword());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getRol().toString());
			preparedStatement.setString(5, usuario.getAvatar());
			preparedStatement.setBoolean(6, usuario.isBaixa());
			
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			resultSet= connection.prepareStatement("SELECT LAST_INSERT_ID() as lastId").executeQuery();
			if(resultSet.next()) {
				lastId=resultSet.getLong("lastId");
			}
			return findById(connection, lastId);
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public Usuario update(Connection connection, Usuario usuario) throws DataException {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Boolean incluirMas=false;
		
		try {
			sql.append("update usuario set ");
			if(usuario.getUsername()!=null) {
				sql.append("username=?");
				incluirMas=true;
			}
			if(usuario.getPassword()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("password=?");
			}
			if(usuario.getNome()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("nome=?");
			}
			if(usuario.getRol()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("rol=?");
			}
			if(usuario.getAvatar()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("avatar=?");
			}
			if(usuario.isBaixa()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("baixa=?");
			}
			
			
			sql.append(" where id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i=1;
			if(usuario.getUsername()!=null) {
				preparedStatement.setString(i++, usuario.getUsername());
			}
			if(usuario.getPassword()!=null) {
				preparedStatement.setString(i++, usuario.getPassword());
			}
			if(usuario.getNome()!=null) {
				preparedStatement.setString(i++, usuario.getNome());
			}
			if(usuario.getRol()!=null) {
				preparedStatement.setString(i++, usuario.getRol());
			}
			if(usuario.getAvatar()!=null) {
				preparedStatement.setString(i++, usuario.getAvatar());
			}
			if(usuario.isBaixa()!=null) {
				preparedStatement.setBoolean(i++, usuario.isBaixa());
			}
			
			preparedStatement.setLong(i++, usuario.getId());
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			return findById(connection, usuario.getId());
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public void softDelete(Connection connection, Long idUsuario) throws DataException {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		try {
			
			sql.append("update usuario set baixa=true where id = ?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setLong(1, idUsuario);
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			logger.info("Usuario posto de baixa");
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
	}

	private Usuario loadNext(Connection connection, ResultSet resultSet) {
		int i = 1;
		Usuario usuario=new Usuario();
		
		try {
			usuario.setId(resultSet.getLong(i++));
			usuario.setUsername(resultSet.getString(i++));
			usuario.setPassword(resultSet.getString(i++));
			usuario.setNome(resultSet.getString(i++));
			usuario.setRol(resultSet.getString(i++));
			usuario.setAvatar(resultSet.getString(i++));
			usuario.setBaixa(resultSet.getBoolean(i++));
			
			return usuario;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
