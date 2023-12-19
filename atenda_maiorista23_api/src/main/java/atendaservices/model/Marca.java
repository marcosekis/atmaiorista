package atendaservices.model;

public class Marca {

	private Long id;
	private String nome;
	
	
	public Marca() {
	}


	public Marca(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public String toString() {
		return "Marca [id=" + id + ", nome=" + nome + "]";
	}
	
	
}
