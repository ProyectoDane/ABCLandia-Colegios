package com.frba.abclandia.dtos;

public class Categoria {
	private int categoriaID;
	private String categoriaNombre;
	private String categoriaDescripcion;
	private int categoriaTipoLetra;
	private  int categoriaIntervalo;
	private int categoriaAlumno;
	
	public Categoria(Integer categoriaID, String categoriaNombre, String categoriaDescripcion){
		this.setCategoriaID(categoriaID);
		this.setCategoriaNombre(categoriaNombre);
		this.setCategoriaDescripcion(categoriaDescripcion);
	}

	public Categoria(int unaCategoria, int tipoLetra,
			int intervaloEntreTarjetas, int alumno_id) {
		this.setCategoriaID(unaCategoria);
		this.setCategoriaTipoLetra(tipoLetra);
		this.setCategoriaIntervalo(intervaloEntreTarjetas);
		this.setCategoriaAlumno(alumno_id);
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

	public int getCategoriaTipoLetra() {
		return categoriaTipoLetra;
	}

	public void setCategoriaTipoLetra(int categoriaTipoLetra) {
		this.categoriaTipoLetra = categoriaTipoLetra;
	}

	public int getCategoriaIntervalo() {
		return categoriaIntervalo;
	}

	public void setCategoriaIntervalo(int categoriaIntervalo) {
		this.categoriaIntervalo = categoriaIntervalo;
	}

	public int getCategoriaAlumno() {
		return categoriaAlumno;
	}

	public void setCategoriaAlumno(int categoriaAlumno) {
		this.categoriaAlumno = categoriaAlumno;
	}
}
