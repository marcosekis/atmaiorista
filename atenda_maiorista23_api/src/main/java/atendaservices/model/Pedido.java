package atendaservices.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

	private Long id;
	private Long id_pedido_devol;
	private Usuario cliente;
	private LocalDateTime data;
	private Boolean pechado;
	private Boolean recibido;
	private List<LineaPedido> LineasPedido=new ArrayList<LineaPedido>();
	
}
