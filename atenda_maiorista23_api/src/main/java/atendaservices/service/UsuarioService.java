package atendaservices.service;

import java.util.ArrayList;

import atendaservices.exceptions.DataException;
import atendaservices.model.Usuario;
import atendaservices.service.exceptions.UserNotFoundException;

public interface UsuarioService {
	public Usuario login(String username, String password) throws UserNotFoundException, DataException;// autentica usuario
	public Usuario findByEmail(String email) throws DataException, UserNotFoundException;// obten usuario polo email
	public Usuario findById(Long idUsuario) throws DataException, UserNotFoundException;// obten usuario polo id
	public ArrayList<Usuario> findAll() throws DataException;// devolve a lista de usuarios
	public Usuario register(Usuario usuario) throws DataException;// crea usuario comprobando que email non repetido e encriptando password
	public Usuario update(Usuario usuario) throws DataException; // actualiza usuario
	public void softDelete(Long idUsuario) throws DataException; // borra soft usuario

}
