package atendaservices.dao;

import java.sql.Connection;
import java.util.ArrayList;

import atendaservices.model.Produto;
import atendaservices.service.ProdutoCriteria;

public interface ProdutoDAO {
	public Produto findById(Connection connection, Long idProduto) throws Exception; // obten produto por id
	public ArrayList<Produto> findAll(Connection connection) throws Exception; // devolve a lista de produtos
	public Results<Produto> findBy(Connection connection, ProdutoCriteria produto, int startIndex, int count) throws Exception;
	public int create(Connection connection, Produto produto) throws Exception;//inserta produto
	public Produto update(Connection connection, Produto produto) throws Exception; // actualiza produto
	public boolean softDelete (Connection connection, Long idProduto) throws Exception; // borra soft produto
}
