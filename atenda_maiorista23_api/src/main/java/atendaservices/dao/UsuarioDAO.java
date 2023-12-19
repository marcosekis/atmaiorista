package atendaservices.dao;

import java.sql.Connection;
import java.util.ArrayList;

import atendaservices.exceptions.DataException;
import atendaservices.model.Usuario;

public interface UsuarioDAO {

	public Usuario findById(Connection connection, Long idUsuario) throws DataException;// obten usuario polo id
	public Usuario findByEmail(Connection connection, String email) throws DataException;// obten usuario polo email
	public ArrayList<Usuario> findAll(Connection connection) throws DataException;// devolve a lista de usuarios
	public Usuario create(Connection connection, Usuario usuario) throws DataException;// crea usuari
	public Usuario update(Connection connection, Usuario usuario) throws DataException; // actualiza usuoario
	public void softDelete(Connection connection, Long idUsuario) throws DataException; // borra soft usuario
}
