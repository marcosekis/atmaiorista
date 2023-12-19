package atendaservices.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import atendaservices.model.Pedido;
import atendaservices.model.Usuario;

public interface PedidoDAO {

	 public Pedido getPedidoPorId(int id) throws Exception; // devolve pedido de campo id= id
	 // devolve os pedidos abertos ou pechados entre as d�as datas con ou sin devoluci�ns e recibidos ou non
	 public ArrayList<Pedido> getPedidosPeriodo(LocalDate dende, LocalDate ata, boolean conDevolucions, boolean pechado, boolean recibido) throws Exception;
	 // devolve os pedidos abertos ou pechados entre as d�as datas dun usuario e con ou sin devoluci�ns e recibidos ou non
	 public ArrayList<Pedido> getPedidosPeriodoDe(LocalDate dendeLocalDate, LocalDate ataLocalDate, boolean eDevolucions, Usuario usuario, boolean pechado, boolean recibido) throws Exception; 
	 public ArrayList<Pedido> getDevolucionsDe (Pedido pedido)throws Exception; // devolve a lista de pedidos devoluci�ns do pedido parametro
	 public int inserta(Pedido pedido)throws Exception; // // inserta pedido como aberto, insertando tam�n lineas pedido que cont�n
	 public void actualiza(Pedido pedido )throws Exception; // actualiza todo menos lineas pedido
	 // novo en v. 230102
	 public void borra (Pedido pedido) throws Exception;// borra pedido
}
