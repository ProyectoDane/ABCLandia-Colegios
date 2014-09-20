package com.frba.abclandia.dtos;

public class Maestro {
	private  String nombre;
	private String apellido;
	private int legajo;
	
	public Maestro(int legajo,String apellido, String nombre){
		this.nombre = nombre;
		this.apellido =  apellido;
		this.legajo = legajo;
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

	public int getLegajo() {
		return legajo;
	}

	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}
	
}


