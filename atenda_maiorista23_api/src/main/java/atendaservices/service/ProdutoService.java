package atendaservices.service;

import java.util.ArrayList;

import atendaservices.dao.Results;
import atendaservices.exceptions.DataException;
import atendaservices.model.Produto;

public interface ProdutoService {
	public Produto findById(Long idProduto) throws DataException; // obten produto por id
	public ArrayList<Produto> findAll() throws DataException; // devolve a lista de produtos
	public Results<Produto> findBy(ProdutoCriteria produto, int startIndex, int count) throws DataException;
	public int create(Produto produto) throws DataException;//inserta produto
	public Produto update(Produto produto) throws DataException; // actualiza produto
	public boolean softDelete (Long idProduto) throws DataException; // borra soft produto
	public boolean asignarProdutoCategoria(Long idProduto, Integer idCategoria) throws DataException;//asigna categoria a un produto
	public boolean asignarProdutoMarca(Long idProduto, Long idMarca) throws DataException;//asigna marca a un produto
}
