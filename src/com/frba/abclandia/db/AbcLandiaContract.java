package com.frba.abclandia.db;

import android.provider.BaseColumns;

public final class AbcLandiaContract {
	
	public AbcLandiaContract() {}
	
	public static abstract class AlumnoEntry implements BaseColumns {
		public static final String TABLE_NAME = "Alumnos";
		public static final String COLUMN_NAME_ALUMNO_ID = "alumno_id";
		public static final String COLUMN_NAME_COLEGIO_ID = "colegio_id";
		public static final String COLUMN_NAME_CONFIGURACION_ID = "configuracion_id";
		public static final String COLUMN_NAME_NOMBRE = "nombre";
		public static final String COLUMN_NAME_APELLIDO = "apellido";
		public static final String COLUMN_NAME_EDAD = "edad";
		public static final String COLUMN_NAME_SEXO = "sexo";
	}
	
	public static abstract class MaestroEntry implements BaseColumns {
		public static final String TABLE_NAME = "Maestros";
		public static final String COLUMN_NAME_MAESTRO_ID ="maestro_id";
		public static final String COLUMN_NAME_COLEGIO_ID = "colegio_id";
		public static final String COLUMN_NAME_MAESTRO_EMAIL = "maestro_email";
		public static final String COLUMN_NAME_MAESTRO_CLAVE= "maestro_clave";
		public static final String COLUMN_NAME_MAESTRO_NOMBRE = "maestro_nombre";
		public static final String COLUMN_NAME_MAESTRO_APELLIDO = "maestro_apellido";
		public static final String COLUMN_NAME_MAESTRO_TIPO = "maestro_tipo";
	}
	
	public static abstract class ColegioEntry implements BaseColumns{
		public static final String TABLE_NAME = "Colegios";
		public static final String COLUMN_NAME_COLEGIO_ID = "colegio_id";
		public static final String COLUMN_NAME_COLEGIO_NOMBRE = "nombre";
		public static final String COLUMN_NAME_COLEGIO_TELEFONO = "telefono";	
	}
	
	public static abstract class RendimientosEntry implements BaseColumns{
		public static final String TABLE_NAME = "Rendimientos";
		public static final String COLUMN_NAME_RENDIMIENTOS_FECHA = "fecha";
		public static final String COLUMN_NAME_ALUMNO_ID = "alumno_id";
		public static final String COLUMN_NAME_MAESTRO_ID = "maestro_id";
		public static final String COLUMN_NAME_LETRA_ID = "letra_id";
		public static final String COLUMN_NAME_EJERCICIO = "ejercicio";
		public static final String COLUMN_NAME_NIVEL = "nivel";
		public static final String COLUMN_NAME_SECUENCIA = "secuencia";
		public static final String COLUMN_NAME_TIEMPO = "tiempo";
		public static final String COLUMN_NAME_CANTIDAD_ACIERTOS = "cantidad_aciertos";
		public static final String COLUMN_NAME_CANTIDAD_FALLAS = "cantidad_fallas";
		
	}
	
	public static abstract class ConfiguracionesEntry implements BaseColumns {
		public static final String TABLE_NAME = "Configuraciones";
		public static final String COLUMN_NAME_CONFIGURACION_ID = "configuracion_id";
		public static final String COLUMN_NAME_CATEGORIA_ID = "categoria_id";
		public static final String COLUMN_NAME_CONFIGURACION_MAYUSCULA = "configuracion_mayuscula";
		public static final String COLUMN_NAME_CONFIGURACION_TIEMPO = "configuracion_tiempo";
	}

	public static abstract class AlumnosMaestrosEntry implements BaseColumns{
		public static final String TABLE_NAME = "Alumnos_Maestros";
		public static final String COLUMN_NAME_ALUMNO_ID = "alumno_id";
		public static final String COLUMN_NAME_MAESTRO_ID = "maestro_id";
	}
	
	public static abstract class CategoriasEntry implements  BaseColumns {
		public static final String TABLE_NAME = "Categorias";
		public static final String COLUMN_NAME_CATEGORIA_ID = "categoria_id";
		public static final String COLUMN_NAME_CATEGORIA_NOMBRE = "categoria_nombre";
		public static final String COLUMN_NAME_CATEGORIA_DESCRIPCION = "categoria_descripcion";
	}
	
	public static abstract class PalabrasEntry implements BaseColumns {
		public static final String TABLE_NAME = "Palabras";
		public static final String COLUMN_NAME_CATEGORIA_ID = "categoria_id";
		public static final String COLUMN_NAME_PALABRA_LETRA = "palabra_letra";
		public static final String COLUMN_NAME_PALABRA_PALABRA = "palabra_palabra";
		public static final String COLUMN_NAME_SONIDO_ID = "sonido_id";
		public static final String COLUMN_NAME_IMAGEN_ID = "imagen_id";
	}
	
	public static abstract class ImagenesEntry implements BaseColumns {
		public static final String TABLE_NAME = "Imagenes";
		public static final String COLUMN_NAME_IMAGEN_ID = "imagen_id";
		public static final String COLUMN_NAME_IMAGEN_BLOB = "imagen_blob";
	}
	
	public static abstract class SonidosEntry implements BaseColumns {
		public static final String TABLE_NAME = "Sonidos";
		public static final String COLUMN_NAME_SONIDO_ID = "sonido_id";
		public static final String COLUMN_NAME_SONIDO_BLOB = "sonido_blob";
	}
	
	
}
