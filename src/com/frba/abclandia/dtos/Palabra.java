package com.frba.abclandia.dtos;

public class Palabra {
	private String id;
	private String categoria;
	private String letra;
	private String palabra;
	private String imagen;
	private String sonido;
	
	public Palabra(){
		
	}

	public Palabra(String palabra_id, String categoria_id, String palabra_letra, String palabra_palabra,
			String imagen_id, String sonido_id) {
		this.setId(palabra_id);
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

	public void setImagen(String imagen) {
		this.imagen = imagen;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getId() {
		return id;
	}

	public void setId(String categoria_id) {
		this.id = categoria_id;
	}
}
