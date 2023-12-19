package atendaservices.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import atendaservices.model.Opinion;
import atendaservices.model.Produto;

public interface OpinionDAO {

	public ArrayList<Opinion>  getOpinions(Produto produto) throws Exception ; // devolve as opinions sobre un produto 																							// como pares valoracion-numero de valoracions
	public int inserta(Opinion comentario) throws Exception; // insertar unha opinión na base de datos
	 
//	 public int getValoracionMedia(Produto produto) throws Exception; // obten a valoración media enteira dun produto 
//	 public LinkedHashMap<Integer, Integer> getValoracions (Produto produto) throws Exception; // devolve as valoracións sobre un produto
	 
}
