package atendaservices.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.dao.ProdutoDAO;
import atendaservices.dao.Results;
import atendaservices.dao.util.ConnectionManager;
import atendaservices.model.Produto;
import atendaservices.service.ProdutoCriteria;

import atendaservices.dao.util.DAOUtil;

public class ProdutoDAOImpl implements ProdutoDAO {
	private static final Logger logger = Logger.getAnonymousLogger();
	
	@Override
	public Produto findById(Connection connection, Long idProduto) throws Exception {
		Produto produto = null;
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select p.id, p.id_categoria, p.id_marca, p.nome, p.prezo, p.desconto, p.coste, p.iva, p.stock, p.foto, p.baixa ");
			sql.append("from produto as p ");
			sql.append("where p.id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			int i = 1;
			preparedStatement.setLong(i++, idProduto);
			logger.info("create statement"+sql);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				produto=loadNext(connection, resultSet);
			}
			return produto;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
			//ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public ArrayList<Produto> findAll(Connection connection) throws Exception {
		Produto produto=null;
		ArrayList<Produto> produtos=new ArrayList<Produto>();
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select p.id, p.id_categoria, p.id_marca, p.nome, p.prezo, p.desconto, p.coste, p.iva, p.stock, p.foto, p.baixa ");
			sql.append("from produto as p");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			logger.info("create statement"+sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				produto=loadNext(connection, resultSet);
				produtos.add(produto);
			}
			return produtos;
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
	public Results<Produto> findBy(Connection connection, ProdutoCriteria produtoCriteria, int startIndex, int count)
			throws Exception {
		List<Produto> prodListCompleto=new ArrayList<Produto>();
		List<Produto> prodList=new ArrayList<Produto>();
		
		Produto produto=null;
		Boolean incluirMas=false;
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			sql.append("select p.id, p.id_categoria, p.id_marca, p.nome, p.prezo, p.desconto, p.coste, p.iva, p.stock, p.foto, p.baixa ");
			sql.append("from produto as p where ");
			///  usar ? ne lugar de valores
			if(produtoCriteria.getIdCategoria() != null) {
				sql.append("p.id_categoria = ? ");
				incluirMas=true;
			}
			if(produtoCriteria.getIdMarca() != null) {
				if(incluirMas) {
					sql.append(" && ");
				}else {
					incluirMas=true;
				}
				sql.append("p.id_marca = ? ");
			}
			if(produtoCriteria.getNome() != null) {
				if(incluirMas) {
					sql.append(" && ");
				}else {
					incluirMas=true;
				}
				sql.append("p.nome = ?% ");
			}
			if(produtoCriteria.getPrezoDende() != null) {
				if(incluirMas) {
					sql.append(" && ");
				}else {
					incluirMas=true;
				}
				sql.append("p.prezo >= ? "+produtoCriteria.getPrezoDende()+" ");
			}
			if(produtoCriteria.getPrezoAta() != null) {
				if(incluirMas) {
					sql.append(" && ");
				}
				sql.append("p.prezo <= ? "+produtoCriteria.getPrezoAta()+" ");
			}

			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i=1;
			if(produtoCriteria.getIdCategoria() != null) {
				preparedStatement.setInt(i++, produtoCriteria.getIdCategoria());
			}
			if(produtoCriteria.getIdMarca() != null) {
				preparedStatement.setLong(i++, produtoCriteria.getIdMarca());
			}
			if(produtoCriteria.getNome() != null) {
				preparedStatement.setString(i++, produtoCriteria.getNome());
			}
			if(produtoCriteria.getPrezoDende() != null) {
				preparedStatement.setDouble(i++, produtoCriteria.getPrezoDende());
			}
			if(produtoCriteria.getPrezoAta() != null) {
				preparedStatement.setDouble(i++, produtoCriteria.getPrezoAta());
			}
			
			if(produtoCriteria.getIdCategoria()==null&&produtoCriteria.getIdMarca()==null&&produtoCriteria.getNome()==null&&
					produtoCriteria.getPrezoDende()==null&&produtoCriteria.getPrezoAta()==null) {
				sql.append("1");
			}
			
			logger.info(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				produto=loadNext(connection, resultSet);
				prodListCompleto.add(produto);
			}
			int countElementos=startIndex+count;
			if(startIndex+count>=prodListCompleto.size()) {
				countElementos=prodListCompleto.size();
			}
			for(i=startIndex;i<countElementos;i++) {
				prodList.add(prodListCompleto.get(i));
			}
			//total de resultados
			int total=DAOUtil.countRow(prodListCompleto);
			
			
			return new Results<Produto>(prodList, startIndex, total);
			
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
	public int create(Connection connection, Produto produto) throws Exception {
		//que es el int que devuelve
		StringBuilder sql = new StringBuilder();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Long lastId=0L;
		
		try {
			sql.append("insert into produto (id_categoria, id_marca, nome, prezo, desconto, coste, iva, stock, foto, baixa)"+
					"values (?,?,?,?,?,?,?,?,?,?)");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setInt(1, produto.getIdCategoria());
			preparedStatement.setLong(2, produto.getIdMarca());
			preparedStatement.setString(3, produto.getNome());
			preparedStatement.setDouble(4, produto.getPrezo());
			preparedStatement.setInt(5, produto.getDesconto());
			preparedStatement.setDouble(6, produto.getCoste());
			preparedStatement.setInt(7, produto.getIva());
			preparedStatement.setLong(8, produto.getStock());
			preparedStatement.setString(9, produto.getFoto());
			preparedStatement.setBoolean(10, produto.isBaixa());
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			resultSet= connection.prepareStatement("SELECT LAST_INSERT_ID() as lastId").executeQuery();
			if(resultSet.next()) {
				lastId=resultSet.getLong("lastId");
			}
			return (int)(long)lastId;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closeResultSet(resultSet);
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return 0;
	}

	@Override
	public Produto update(Connection connection, Produto produto) throws Exception {
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		Boolean incluirMas=false;
		
		try {
			sql.append("update produto set ");
			if(produto.getIdCategoria()!=null) {
				sql.append("id_categoria=?");
				incluirMas=true;
			}
			if(produto.getIdMarca()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("id_marca=?");
			}
			if(produto.getNome()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("nome=?");
			}
			if(produto.getPrezo()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("prezo=?");
			}
			if(produto.getDesconto()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("desconto=?");
			}
			if(produto.getCoste()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("coste=?");
			}
			if(produto.getIva()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("iva=?");
			}
			if(produto.getStock()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("stock=?");
			}
			if(produto.getFoto()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}else {
					incluirMas=true;
				}
				sql.append("foto=?");
			}
			if(produto.isBaixa()!=null) {
				if(incluirMas) {
					sql.append(", ");
				}
				sql.append("baixa=?");
			}
			
			sql.append(" where id = ?");
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			int i=1;
			if(produto.getIdCategoria()!=null) {
				preparedStatement.setLong(i++, produto.getIdCategoria());
			}
			if(produto.getIdMarca()!=null) {
				preparedStatement.setLong(i++, produto.getIdMarca());
			}
			if(produto.getNome()!=null) {
				preparedStatement.setString(i++, produto.getNome());
			}
			if(produto.getPrezo()!=null) {
				preparedStatement.setDouble(i++, produto.getPrezo());
			}
			if(produto.getDesconto()!=null) {
				preparedStatement.setInt(i++, produto.getDesconto());
			}
			if(produto.getCoste()!=null) {
				preparedStatement.setDouble(i++, produto.getCoste());
			}
			if(produto.getIva()!=null) {
				preparedStatement.setInt(i++, produto.getIva());
			}
			if(produto.getStock()!=null) {
				preparedStatement.setLong(i++, produto.getStock());
			}
			if(produto.getFoto()!=null) {
				preparedStatement.setString(i++, produto.getFoto());
			}
			if(produto.isBaixa()!=null) {
				preparedStatement.setBoolean(i++, produto.isBaixa());
			}
			preparedStatement.setLong(i++, produto.getId());
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			return findById(connection, produto.getId());
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return null;
	}

	@Override
	public boolean softDelete(Connection connection, Long idProduto) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		PreparedStatement preparedStatement = null;
		try {
			
			sql.append("update produto set baixa=true where id = ?");
			
			preparedStatement = connection.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			preparedStatement.setLong(1, idProduto);
			logger.info("create statement: "+sql);
			preparedStatement.execute();
			return true;
		}catch(Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
		}finally {
			ConnectionManager.closePreparedStatement(preparedStatement);
//			ConnectionManager.closeConnection(connection);
		}
		return false;
	}
	
	private Produto loadNext(Connection connection, ResultSet resultSet) {
		int i = 1;
		Produto produto=new Produto();
		
		try {
			produto.setId(resultSet.getLong(i++));
			produto.setIdCategoria(resultSet.getInt(i++));
			produto.setIdMarca(resultSet.getLong(i++));
			produto.setNome(resultSet.getString(i++));
			produto.setPrezo(resultSet.getDouble(i++));
			produto.setDesconto(resultSet.getInt(i++));
			produto.setCoste(resultSet.getDouble(i++));
			produto.setIva(resultSet.getInt(i++));
			produto.setStock(resultSet.getLong(i++));
			produto.setFoto(resultSet.getString(i++));
			produto.setBaixa(resultSet.getBoolean(i++));
			return produto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
