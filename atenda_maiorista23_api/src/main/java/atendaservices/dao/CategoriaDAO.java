package atendaservices.dao;

import java.util.ArrayList;

import atendaservices.model.Categoria;

public interface CategoriaDAO {
	public ArrayList<Categoria> getAllCategorias() throws Exception; // devolve a lista das categorias

	public void actualiza(Categoria c) throws Exception; // acgtualiza categoria

	public int inserta(Categoria c) throws Exception; /// inserta categoria

	public void borra(Categoria c) throws Exception; // borra categoria

	public Categoria getCategoriaPorId(Long idCategoria) throws Exception; // obten categoria por id
}
