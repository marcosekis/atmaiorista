package atendaservices.model;

public class Usuario {

	private Long id;
	private String username;
	private String password;
	private String nome;
	private String rol;
	private String avatar;
	private Boolean baixa;
	
	
	public Usuario() {
		
	}

	public Usuario(Long id, String username, String password, String nome, String rol, String avatar, Boolean baixa ) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nome = nome;
		this.rol = rol;
		this.avatar = avatar;
		this.baixa = baixa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long l) {
		this.id = l;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean isBaixa() {
		return baixa;
	}

	public void setBaixa(Boolean baixa) {
		this.baixa = baixa;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", nome=" + nome + ", rol="
				+ rol + ", avatar=" + avatar + ", baixa=" + baixa + "]";
	}
	
	
}
