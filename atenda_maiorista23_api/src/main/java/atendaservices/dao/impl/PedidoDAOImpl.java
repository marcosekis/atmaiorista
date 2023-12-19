package atendaservices.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import atendaservices.dao.PedidoDAO;
import atendaservices.model.Pedido;
import atendaservices.model.Usuario;

public class PedidoDAOImpl implements PedidoDAO {

	@Override
	public Pedido getPedidoPorId(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pedido> getPedidosPeriodo(LocalDate dende, LocalDate ata, boolean conDevolucions, boolean pechado,
			boolean recibido) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pedido> getPedidosPeriodoDe(LocalDate dendeLocalDate, LocalDate ataLocalDate, boolean eDevolucions,
			Usuario usuario, boolean pechado, boolean recibido) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Pedido> getDevolucionsDe(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int inserta(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void actualiza(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borra(Pedido pedido) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
