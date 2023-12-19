package atendaservices.model;

public class LineaPedido {

	private Long id;
	private Produto produto;
	private Long desconto;
	private Long unidades;
	private Double prezo;
	private Double coste;
	
	public LineaPedido() {
	}

	public LineaPedido(Long id, Produto produto, Long desconto, Long unidades, Double prezo, Double coste) {
		super();
		this.id = id;
		this.produto = produto;
		this.desconto = desconto;
		this.unidades = unidades;
		this.prezo = prezo;
		this.coste = coste;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getDesconto() {
		return desconto;
	}

	public void setDesconto(Long desconto) {
		this.desconto = desconto;
	}

	public Long getUnidades() {
		return unidades;
	}

	public void setUnidades(Long unidades) {
		this.unidades = unidades;
	}

	public Double getPrezo() {
		return prezo;
	}

	public void setPrezo(Double prezo) {
		this.prezo = prezo;
	}

	public Double getCoste() {
		return coste;
	}

	public void setCoste(Double coste) {
		this.coste = coste;
	}

	@Override
	public String toString() {
		return "LineaPedido [id=" + id + ", produto=" + produto + ", desconto=" + desconto + ", unidades=" + unidades
				+ ", prezo=" + prezo + ", coste=" + coste + "]";
	}
	
	
}
