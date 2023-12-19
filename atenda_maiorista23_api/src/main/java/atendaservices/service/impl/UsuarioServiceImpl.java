
package atendaservices.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.UsuarioDAO;
import atendaservices.dao.impl.UsuarioDAOImpl;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.exceptions.DataException;
import atendaservices.model.Usuario;
import atendaservices.service.UsuarioService;
import atendaservices.service.exceptions.UserNotFoundException;

//import org.mindrot.jbcrypt.BCrypt;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioServiceImpl implements UsuarioService{
	
	private static final Logger logger = Logger.getAnonymousLogger();
	private UsuarioDAO usuarioDAO=new UsuarioDAOImpl();
	
	@Override
	public Usuario login(String username, String password) throws UserNotFoundException, DataException {
		Connection connection =ConnectionManager.getConnection();
//		String passCodif="";
		Usuario usuario=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuario=usuarioDAO.findByEmail(connection, username);
//			passCodif=BCrypt.hashpw(password, BCrypt.gensalt(12));
			if(BCrypt.checkpw(password, usuario.getPassword())) {
				commit=true;
				logger.info("Funcionamiento correcto");
				return usuario;
			}else {
				logger.info("Error: Las contraseñas no coinciden");
			}
			
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			logger.info("Funcionamiento INCORRECTO (posiblemente no exista un usuario con dicho username-email");
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return null;
	}

	@Override
	public Usuario findByEmail(String email) throws DataException, UserNotFoundException {
		Connection connection =ConnectionManager.getConnection();
		Usuario usuario=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuario=usuarioDAO.findByEmail(connection, email);
			commit=true;
			logger.info("Funcionamiento correcto");
			return usuario;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return usuario;
	}

	@Override
	public Usuario findById(Long idUsuario) throws DataException, UserNotFoundException {
		Connection connection =ConnectionManager.getConnection();
		Usuario usuario=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuario=usuarioDAO.findById(connection, idUsuario);
			commit=true;
			logger.info("Funcionamiento correcto");
			return usuario;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return usuario;
	}

	@Override
	public ArrayList<Usuario> findAll() throws DataException {
		Connection connection =ConnectionManager.getConnection();
		ArrayList<Usuario> usuarios=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuarios=usuarioDAO.findAll(connection);
			commit=true;
			logger.info("Funcionamiento correcto");
			return usuarios;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return usuarios;
	}

	@Override
	public Usuario register(Usuario usuario) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		Usuario usuarioNovo=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuarioNovo=usuarioDAO.create(connection, usuario);
			commit=true;
			logger.info("Funcionamiento correcto");
			return usuarioNovo;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return usuarioNovo;
	}

	@Override
	public Usuario update(Usuario usuario) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		Usuario usuarioNovo=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuarioNovo=usuarioDAO.update(connection, usuario);
			commit=true;
			logger.info("Funcionamiento correcto");
			return usuarioNovo;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return usuarioNovo;
	}

	@Override
	public void softDelete(Long idUsuario) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			usuarioDAO.softDelete(connection, idUsuario);
			commit=true;
			logger.info("Funcionamiento correcto");
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
	}

}
