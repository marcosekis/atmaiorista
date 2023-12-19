package atendaservices.model;

import java.time.LocalDateTime;

public class Opinion {

	private Long id;
	//SEGURO QUE USUARIO ES TIPO USUARIO Y NO LONG. HAGO COMO QUE ES UN LONG
	private Long Usuario;
	private Long idProduto;
	private Integer valoracion = 1;
	private String texto;
	private LocalDateTime data;
	
	public Opinion() {
	}

	public Opinion(Long id, Long usuario, Long idProduto, Integer valoracion, String texto,
			LocalDateTime data) {
		this.id = id;
		Usuario = usuario;
		this.idProduto = idProduto;
		this.valoracion = valoracion;
		this.texto = texto;
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuario() {
		return Usuario;
	}

	public void setUsuario(Long usuario) {
		Usuario = usuario;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Opinion [id=" + id + ", Usuario=" + Usuario + ", idProduto=" + idProduto + ", valoracion=" + valoracion
				+ ", texto=" + texto + ", data=" + data + "]";
	}
	
	
	
}
