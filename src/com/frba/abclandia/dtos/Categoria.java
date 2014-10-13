package com.frba.abclandia.dtos;

public class Categoria {
	private Integer categoriaID;
	private String categoriaNombre;
	private String categoriaDescripcion;
	
	public Categoria(Integer categoriaID, String categoriaNombre, String categoriaDescripcion){
		this.setCategoriaID(categoriaID);
		this.setCategoriaNombre(categoriaNombre);
		this.setCategoriaDescripcion(categoriaDescripcion);
	}

	public String getCategoriaDescripcion() {
		return categoriaDescripcion;
	}

	public void setCategoriaDescripcion(String categoriaDescripcion) {
		this.categoriaDescripcion = categoriaDescripcion;
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public Integer getCategoriaID() {
		return categoriaID;
	}

	public void setCategoriaID(Integer categoriaID) {
		this.categoriaID = categoriaID;
	}
}
