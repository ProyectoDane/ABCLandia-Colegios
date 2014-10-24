package com.frba.abclandia.dtos;

import org.json.JSONException;
import org.json.JSONObject;

public class Estadistica {
	private String timestamp = "0";
	private int alumno_id = 0;
	private int maestro_id = 0;
	private int categoria_id = 0;
	private String letra = "0";
	private int ejercicio = 0;
	private int nivel = 0;
	private String secuencia= "none";
	private int tiempo = 0;
	private int cantidad_aciertos;
	private int cantidad_fallas;
	
	public Estadistica(JSONObject estadisticas){
//		jsonEstadisticas.put("alumno_id", mGame.getUnAlumno());
		try {
			setAlumno_id(estadisticas.getInt("alumno_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			setAlumno_id(0);
		}
//		jsonEstadisticas.put("maestro_id", mGame.getUnMaestro());
		try {
			setMaestro_id(estadisticas.getInt("maestro_id"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			setMaestro_id(0);
		}
//		jsonEstadisticas.put("categoria_id", mGame.getUnaCategoria());
		try { 
			setCategoria_id(estadisticas.getInt("categorias"));
		} catch (JSONException e){
			setCategoria_id(0);
		}
//		jsonEstadisticas.put("ejercicio", mGame.getmGameNumber());
		try {
			setEjercicio(estadisticas.getInt("ejercicio"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("nivel", mGame.getNivel());
		try {
			setNivel(estadisticas.getInt("nivel"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("secuencia", mGame.getSecuence());
		try {
			setSecuencia(estadisticas.getString("secuencia"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("tiempo", mElapsedTime);
		try {
			setTiempo(estadisticas.getInt("tiempo"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("cantidad_aciertos", hitsCount - failsCount);
		try {
			setCantidad_aciertos(estadisticas.getInt("cantidad_aciertos"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("cantidad_fallas", failsCount);
		try {
			setCantidad_fallas(estadisticas.getInt("cantidad_fallas"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		jsonEstadisticas.put("timestamp", mStartTime);
		try {
			setTimestamp(estadisticas.getString("timestamp"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	

}
