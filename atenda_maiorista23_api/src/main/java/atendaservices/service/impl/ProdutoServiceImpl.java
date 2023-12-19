package atendaservices.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.ProdutoDAO;
import atendaservices.dao.Results;
import atendaservices.dao.impl.ProdutoDAOImpl;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.exceptions.DataException;
import atendaservices.model.Produto;
import atendaservices.service.ProdutoCriteria;
import atendaservices.service.ProdutoService;

public class ProdutoServiceImpl implements ProdutoService{

	private static final Logger logger = Logger.getAnonymousLogger();
	private ProdutoDAO produtoDAO=new ProdutoDAOImpl();
	
	@Override
	public Produto findById(Long idProduto) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		Produto p=new Produto();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			p=produtoDAO.findById(connection, idProduto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return p;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return p;
		
		
		
	}

	@Override
	public ArrayList<Produto> findAll() throws DataException {
		Connection connection =ConnectionManager.getConnection();
		ArrayList<Produto> produtos=null;
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			produtos=produtoDAO.findAll(connection);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return produtos;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return produtos;
	}

	@Override
	public Results<Produto> findBy(ProdutoCriteria produto, int startIndex, int count) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			Results<Produto> produtos=produtoDAO.findBy(connection, produto, startIndex, count);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return produtos;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return null;
	}

	@Override
	public int create(Produto produto) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			int idCreado=produtoDAO.create(connection, produto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return idCreado;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return 0;
	}

	@Override
	public Produto update(Produto produto) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			Produto produtoUpdated=produtoDAO.update(connection, produto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return produtoUpdated;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return null;
	}

	@Override
	public boolean softDelete(Long idProduto) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			boolean borrado= produtoDAO.softDelete(connection, idProduto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return borrado;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return false;
	}

	@Override
	public boolean asignarProdutoCategoria(Long idProduto, Integer idCategoria) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			Produto produto=produtoDAO.findById(connection, idProduto);
			produto.setIdCategoria(idCategoria);
			produtoDAO.update(connection, produto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return true;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return false;
		
	}

	@Override
	public boolean asignarProdutoMarca(Long idProduto, Long idMarca) throws DataException {
		Connection connection =ConnectionManager.getConnection();
		boolean commit=false;
		try {
			connection.setAutoCommit(false);
			Produto produto=produtoDAO.findById(connection, idProduto);
			produto.setIdMarca(idMarca);
			produtoDAO.update(connection, produto);
			commit=true;
			logger.info("Funcionamiento correcto. commit= "+commit);
			return true;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeConnection(connection, commit);
		}
		return false;
	}

}
