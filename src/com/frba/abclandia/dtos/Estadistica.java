package com.frba.abclandia.dtos;

public class Estadistica {
	private String timestamp;
	private String fecha;
	private Integer alumno_id;
	private Integer maestro_id;
	private Integer categoria_id;
	private String letra;
	private Integer ejercicio;
	private Integer nivel;
	private String secuencia;
	private String tiempo;
	private Integer cantidad_aciertos;
	private Integer cantidad_fallas;
	
	public Estadistica(){
		
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Integer getAlumno_id() {
		return alumno_id;
	}

	public void setAlumno_id(Integer alumno_id) {
		this.alumno_id = alumno_id;
	}

	public Integer getMaestro_id() {
		return maestro_id;
	}

	public void setMaestro_id(Integer maestro_id) {
		this.maestro_id = maestro_id;
	}

	public Integer getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public Integer getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(Integer ejercicio) {
		this.ejercicio = ejercicio;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public Integer getCantidad_aciertos() {
		return cantidad_aciertos;
	}

	public void setCantidad_aciertos(Integer cantidad_aciertos) {
		this.cantidad_aciertos = cantidad_aciertos;
	}

	public Integer getCantidad_fallas() {
		return cantidad_fallas;
	}

	public void setCantidad_fallas(Integer cantidad_fallas) {
		this.cantidad_fallas = cantidad_fallas;
	}
	

}
