package com.frba.abclandia.dtos;

public class Alumno {
	
	private String nombre;
	private String apellido;
	private int profesor;
	private int legajo;
	
	public Alumno(int legajo, String apellido, String nombre, int profesor){
		this.setLegajo(legajo);
		this.setApellido(apellido);
		this.setNombre(nombre);
		this.setProfesor(profesor);
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

	public int getProfesor() {
		return profesor;
	}

	public void setProfesor(int profesor) {
		this.profesor = profesor;
	}

	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

}
