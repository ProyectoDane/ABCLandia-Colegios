package com.frba.abclandia.dtos;

public class Alumno {
	
	private String nombre;
	private String apellido;
	private int maestro = 0;
	private int id;
	
	public Alumno(int id, String apellido, String nombre, int maestro){
		this.setId(id);
		this.setApellido(apellido);
		this.setNombre(nombre);
		this.setMaestro(maestro);
	}

	public Alumno(int id, String apellido, String nombre){
		this.setId(id);
		this.setApellido(apellido);
		this.setNombre(nombre);
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getMaestro() {
		return maestro;
	}

	public void setMaestro(int profesor) {
		this.maestro = profesor;
	}

	public int getId() {
		return id;
	}

	public void setId(int legajo) {
		this.id = legajo;
	}

}
