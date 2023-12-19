package atendaservices.dao;

import java.io.File;
import java.util.ArrayList;

import atendaservices.model.Produto;

public interface xmlDAO {

	 public void exportaProdutos(ArrayList<Produto> produtos, File exportedFile); // exporta a lista de productos a o ficheiro en formato XML
	 public void importaProdutos (File xmlFileProdutos); //importa os productos do ficheiro na lista de produtos
}
