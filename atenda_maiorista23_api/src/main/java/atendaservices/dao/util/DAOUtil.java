package atendaservices.dao.util;

import java.util.List;

import atendaservices.model.Produto;

public class DAOUtil {

	public static int countRow(List<Produto> listaProdutos) {
		
		return listaProdutos.size();
	}
}
