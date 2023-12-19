package atendaservices.dao;

import atendaservices.model.LineaPedido;
import atendaservices.model.Pedido;

public interface LineaPedidoDAO {

	 public int inserta (LineaPedido lineaPedido, Pedido pedido) throws Exception; // inserta LineaPedido e devolve id
	 public void actualiza (LineaPedido lineaPedido) throws Exception; // actualiza LineaPedido (unidades só)
	 public int getUnidadesDevoltasDe (LineaPedido lineaPedido) throws Exception;   // devolve as devoluci�ns xa feitas dunha li�a pedido
	 /// novo en v. 230102
	 public void borra(LineaPedido linea) throws Exception;// borra linea pedido.
}
