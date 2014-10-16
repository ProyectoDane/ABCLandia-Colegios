package com.frba.abclandia.dtos;

public class Palabra {
	private Integer id;
	private Integer categoria;
	private String letra;
	private String palabra;
	private String imagen;
	private String sonido;
	
	public Palabra(){
		
	}

	public Palabra(int palabra_id, int categoria_id, String palabra_letra, String palabra_palabra,
			String imagen_id, String sonido_id) {
		// TODO Auto-generated constructor stub
		this.setId(categoria_id);
		this.setCategoria(categoria_id);
		this.setLetra(palabra_letra);
		this.setPalabra(palabra_palabra);
		this.setImagen(imagen_id);
		this.setSonido(sonido_id);
	
	}

	public String getSonido() {
		return sonido;
	}

	public void setSonido(String sonido) {
		this.sonido = sonido;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imgaen) {
		this.imagen = imgaen;
	}

	public String getPalabra() {
		return palabra;
	}

	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
